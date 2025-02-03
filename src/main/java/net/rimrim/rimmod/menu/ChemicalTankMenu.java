package net.rimrim.rimmod.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.rimrim.rimmod.blockentity.ChemicalTankBlockEntity;
import net.rimrim.rimmod.init.ModMenus;
import net.rimrim.rimmod.menu.slot.AntiFluidItemSlot;
import net.rimrim.rimmod.menu.slot.FluidContainerSlot;

public class ChemicalTankMenu extends AbstractContainerMenu {
    private final ChemicalTankBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    private final int containerRows = 1;

    public ChemicalTankMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public ChemicalTankMenu(int containerId, Inventory playerInventory, BlockEntity blockEntity) {
        super(ModMenus.CHEMICAL_TANK_MENU.get(), containerId);  // You'll need to register this menu type

        if (blockEntity instanceof ChemicalTankBlockEntity be) {
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect entity class %s passed into Menu".formatted(blockEntity.getClass().getName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());


        // Add Chemical Tank slots
        this.addSlot(new AntiFluidItemSlot(this.blockEntity.getItemHandler(), 0, 62, 36));
        this.addSlot(new FluidContainerSlot(this.blockEntity.getItemHandler(), 1, 98, 36));
        this.addStandardInventorySlots(playerInventory, 8, 84);

    }


    @Override
    public boolean stillValid(Player player) {
        return stillValid(levelAccess, player, blockEntity.getBlockState().getBlock());
    }

    public ChemicalTankBlockEntity getBlockEntity() {
        return this.blockEntity;
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.containerRows * 9) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }


}
