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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.container.ChemicalStackHandler;
import net.rimrim.rimmod.init.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class ChemicalTankBlockEntity extends BlockEntity implements MenuProvider {

    private final ChemicalStackHandler chemHandler = new ChemicalStackHandler(10) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            ChemicalTankBlockEntity.this.setChanged();
        }
    };

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

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }
}
