package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.stack.ChemicalStack;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.itemhandler.TankItemHandler;
import net.rimrim.rimmod.menu.ChemicalTankMenu;
import net.rimrim.rimmod.transport.TransportHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ChemicalTankBlockEntity extends BlockEntity implements MenuProvider {

    private final ChemicalStackHandler chemHandler = new ChemicalStackHandler(10) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            ChemicalTankBlockEntity.this.setChanged();
        }
    };

    private final TankItemHandler itemHandler = new TankItemHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            fluidToChemical();
            ChemicalTankBlockEntity.this.setChanged();
        }
    };

    private final TransportHandler transHandler = new TransportHandler(chemHandler);

    private static final Component TITLE = Component.translatable("container." + RimMod.MODID + ".chemical_tank");

    public ChemicalTankBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CHEMICAL_TANK.get(), pos, blockState);
    }


    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    public ChemicalStackHandler getChemHandler() {
        return this.chemHandler;
    }

    public TransportHandler getTransHandler() {
        return this.transHandler;
    }

    public TankItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public @NotNull ItemStack getFluidItem() {
        return this.itemHandler.getStackInSlot(this.itemHandler.FLUID_SLOT);
    }

    private void fluidToChemical() {

        RimMod.LOGGER.info("trying level or client side");
        if (level == null || level.isClientSide()) return;

        ItemStack fluidItem = this.getFluidItem();
        IFluidHandlerItem itemFluidHandler = fluidItem.getCapability(Capabilities.FluidHandler.ITEM, null);
        if (itemFluidHandler == null) return;

        RimMod.LOGGER.info("checking if water bucket");
        if (!fluidItem.is(Items.WATER_BUCKET)) return;
        // Can process water


        RimMod.LOGGER.info("checking if same fluid or empty");
        ChemicalStack chemStack = this.chemHandler.getChemicalStack();
        boolean sameFluidOrEmpty = (Objects.equals(chemStack.chemical().name, "water") || chemStack.isEmpty());
        if (!sameFluidOrEmpty) return;

        // Check if can insert

        RimMod.LOGGER.info("checking if can insert");
        float remainingMass = this.chemHandler.getRemainingMassWithChemical(Chemicals.WATER);
        float expectedMass = Chemicals.WATER.density(this.chemHandler.processVars());
        int insertedMass = itemFluidHandler.drain((int) expectedMass, IFluidHandler.FluidAction.SIMULATE).getAmount();
        RimMod.LOGGER.info("CALCULATED RHO: {}", insertedMass);
        if (remainingMass >= insertedMass) {
            itemFluidHandler.drain(insertedMass, IFluidHandler.FluidAction.EXECUTE);
            this.chemHandler.insertChemical(Chemicals.WATER, insertedMass, false);

            this.getItemHandler().setStackInSlotNoUpdate(
                    this.getItemHandler().FLUID_SLOT,
                    itemFluidHandler.getContainer());

            this.sendUpdate();

            RimMod.LOGGER.info("Expected stack is {}", itemFluidHandler.getContainer());
            RimMod.LOGGER.info("SUCCESS");
        }


    }

    private void sendUpdate() {
        setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }


    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        chemHandler.deserializeNBT(registries, tag.getCompound("ChemStack"));
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("ChemStack", chemHandler.serializeNBT(registries));
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ChemicalTankMenu(containerId, playerInventory, this);
    }
}
