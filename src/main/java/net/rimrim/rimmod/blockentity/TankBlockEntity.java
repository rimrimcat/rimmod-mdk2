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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.TankMenu;
import net.rimrim.rimmod.menu.itemhandler.TankItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TankBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SIZE = 2;

    private final TankItemHandler itemHandler = new TankItemHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            process_fluid();
            TankBlockEntity.this.setChanged();
        }
    };


    private final FluidTank fluidTank = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            TankBlockEntity.this.setChanged();
        }
    };

    private static final Component TITLE = Component.translatable("container." + RimMod.MODID + ".tank");
    private boolean fluid_container_processed;
    private boolean item_processed;

    public TankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TANK.get(), pos, state);

    }

    public int getContainerSize() {
        return SIZE;
    }


    public TankItemHandler getItemHandler() {
        return itemHandler;
    }


    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(0).isEmpty() && this.itemHandler.getStackInSlot(1).isEmpty();
    }

    public @NotNull ItemStack getItem() {
        return this.itemHandler.getStackInSlot(0);
    }

    public @NotNull ItemStack getFluidItem() {
        return this.itemHandler.getStackInSlot(this.itemHandler.FLUID_SLOT);
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    public int getAvailableFluidCapacity() {
        return this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
    }


    public Component getDisplayName() {
        return TITLE;
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        fluidTank.readFromNBT(registries, tag.getCompound("FluidTank"));
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.put("FluidTank", fluidTank.writeToNBT(registries, new CompoundTag()));
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }


    // SYNC ON BLOCK UPDATE
    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void process_fluid() {
        if (level == null || level.isClientSide()) return;

        BlockPos blockPos = this.getBlockPos();
        TankBlockEntity tank = this;
        if (tank == null) return;

        ItemStack stack = tank.getFluidItem();
        if (stack.isEmpty()) return;

        if (this.getAvailableFluidCapacity() < 1000) return;

        // Get item's fluid capability
        IFluidHandlerItem itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM, null);
        if (itemFluidHandler == null) return;

        // Check if same fluid or one is empty
        boolean sameFluidType = this.getFluid().is(itemFluidHandler.getFluidInTank(0).getFluid());
        boolean oneIsEmpty = this.getFluidTank().isEmpty() || itemFluidHandler.getFluidInTank(0).isEmpty();
        if (!sameFluidType && !oneIsEmpty) return;

        // Drain item content if possible
        int availableCapacity = this.getAvailableFluidCapacity();
        int drainable = itemFluidHandler.drain(availableCapacity, IFluidHandler.FluidAction.SIMULATE).getAmount();

        if (drainable > 0) {
            this.getFluidTank().fill(itemFluidHandler.drain(drainable, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);

            if (drainable <= availableCapacity) {
                this.getItemHandler().setStackInSlotNoUpdate(this.getItemHandler().FLUID_SLOT,
                        itemFluidHandler.getContainer());
            }

            this.sendUpdate();
        }

        // TODO: FILL CONTAINERS WITH LIQUID

    }

    private void process_item() {
        // TODO: CUSTOM CRAFTING?
    }

    private void sendUpdate() {
        setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TankMenu(containerId, playerInventory, this);
    }
}
