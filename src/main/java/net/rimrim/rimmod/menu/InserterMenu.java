package net.rimrim.rimmod.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import net.rimrim.rimmod.init.ModMenus;

public class InserterMenu extends AbstractContainerMenu {
    private final InserterBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    private final int containerRows = 1;

    // Client Constructor
    public InserterMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

   // Server Constructor
    public InserterMenu(int containerId, Inventory playerInventory, BlockEntity blockEntity) {
        super(ModMenus.INSERTER_MENU.get(), containerId);

        if (blockEntity instanceof InserterBlockEntity be) {
            this.blockEntity = be;
        }
        else {
            throw new IllegalStateException("Incorrect entity class %s passed into Menu".formatted(blockEntity.getClass().getName()) );
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Add slots
        this.addSlot(new SlotItemHandler(this.blockEntity.getItemHandler(), 0, 80, 36));
        this.addStandardInventorySlots(playerInventory, 8, 84);
    }



    @Override
    public boolean stillValid(Player player) {
        return stillValid(levelAccess, player, blockEntity.getBlockState().getBlock());
    }

    public InserterBlockEntity getBlockEntity() {
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


