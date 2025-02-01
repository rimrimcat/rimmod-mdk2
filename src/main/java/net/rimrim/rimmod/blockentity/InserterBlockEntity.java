package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.InserterBlock;
import net.rimrim.rimmod.block.properties.InserterState;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.InserterMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class InserterBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SIZE = 1;
    private long lastUpdateTime; // track last update on client


    private InserterState state;
    private int move_speed; // how much progress increments per tick
    private int progress;


    private final ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            InserterBlockEntity.this.setChanged();
        }
    };
    private static final Component TITLE = Component.translatable("container." + RimMod.MODID + ".inserter");

    public InserterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INSERTER.get(), pos, state);

        this.progress = 0;
        this.state = InserterState.WAIT_SOURCE;
        this.move_speed = 1;
        this.lastUpdateTime = 0;
    }

    public int getContainerSize() {
        return SIZE;
    }

    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(0).isEmpty();
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    // Return the item stack in the specified slot.
    public ItemStack getItem() {
        return this.getItem(0);
    }

    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public void setItem(ItemStack item) {
        this.setItem(0, item);
    }

    public void setItem(int slot, ItemStack item) {
        this.itemHandler.setStackInSlot(slot, item);
        this.setChanged();
    }

    // The display name of the menu. Don't forget to add a translation!
    public Component getDefaultName() {
        return TITLE;
    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        this.state = InserterState.values()[tag.getInt("state")];
        this.progress = tag.getInt("progress");
        this.lastUpdateTime = tag.getLong("lastUpdateTime");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.putInt("state", this.state.stateIndex);
        tag.putInt("progress", this.progress);
        tag.putLong("lastUpdateTime", this.lastUpdateTime);
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }


    // Handle a received update tag here. The default implementation calls #loadAdditional here,
    // so you do not need to override this method if you don't plan to do anything beyond that.
    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new InserterMenu(containerId, playerInventory, this);
    }

    public InserterState getInserterState() {
        return this.state;
    }

    public int getProgress() {
        return this.progress;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    private static class GetterResult {
        private boolean success;
        private @Nullable ItemStack outputItem;

        public GetterResult(boolean success, @Nullable ItemStack outputItem) {
            this.success = success;
            this.outputItem = outputItem;
        }
    }

    private static GetterResult getItem(BlockPos pos, Direction dir, Level level, boolean takeItem) {
        BlockEntity source_entity = level.getBlockEntity(pos);

        if (source_entity != null) {
            // Check for capability and try to take item
            IItemHandler item_cap = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, dir.getOpposite());

            if (item_cap != null) {
                int max_slot = item_cap.getSlots();

                for (int i = 0; i < max_slot; i++) {
                    ItemStack stackInSlot = item_cap.getStackInSlot(i);

                    if (!stackInSlot.isEmpty()) {
                        // Found the first slot with a valid item

                        return (takeItem) ?
                                new GetterResult(true, item_cap.extractItem(i, stackInSlot.getCount(), false)) :
                                new GetterResult(true, null);
                    }
                }
            }
        }
        return new GetterResult(false, null);
    }

    private static int putItem(BlockPos pos, Direction dir, Level level, ItemStack item, boolean simulate) {
        BlockEntity dest_entity = level.getBlockEntity(pos);

        if (dest_entity != null) {
            // Check for capability and try to take item
            IItemHandler item_cap = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, dir.getOpposite());

            if (item_cap != null) {
                int max_slot = item_cap.getSlots();

                for (int i = 0; i < max_slot; i++) {
                    ItemStack stackInSlot = item_cap.getStackInSlot(i);
                    if (stackInSlot.isEmpty()) {
                        // Found the first slot with empty slot
                        // Check if we can insert item


                        if (item_cap.isItemValid(i, item)) {
                            ItemStack ret = item_cap.insertItem(i, item, true);
                            if (simulate && ret.isEmpty()) {
                                return i;
                            } else if (!simulate && ret.isEmpty()) {
                                item_cap.insertItem(i, item, false);
                                return i;
                            }
                        }

                    }
                }
            }
        }
        return -1;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        Direction dir = blockState.getValue(InserterBlock.INSERT_DIRECTION);
        InserterBlockEntity inserter = (InserterBlockEntity) level.getBlockEntity(blockPos);
        if (inserter == null) {
            return;
        }
        if (inserter.state.direction.getStep() == -1) {
            dir = dir.getOpposite();
        }

        int maxProgress = inserter.state.maxProgress;
        inserter.lastUpdateTime = level.getGameTime();
//        RimMod.LOGGER.info("Inserter state " + inserter.state.name);

        switch (inserter.state) {
            case WAIT_SOURCE: {
                BlockPos source_pos = blockPos.relative(dir, 1);
                GetterResult res = getItem(source_pos, dir, level, false);
                if (res.success) {
                    // There is one item to take in chest
                    inserter.state = InserterState.TAKING;

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                } else if (!inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                    // Someone gave the inserter an item through menu
                    inserter.state = InserterState.TRANSFERRING;

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                }

                break;
            }
            case TAKING: {
                if (inserter.progress < maxProgress) {
                    inserter.progress += inserter.move_speed;
                } else {
                    if (!inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        // Someone gave the inserter an item through menu
                        inserter.state = InserterState.TRANSFERRING;
                        inserter.progress = 0;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                        break;
                    }

                    BlockPos source_pos = blockPos.relative(dir, 1);
                    GetterResult res = getItem(source_pos, dir, level, true);
                    if (res.success) {
                        // The Inserter was able to take item from inventory
                        assert res.outputItem != null;
                        inserter.getItemHandler().insertItem(0, res.outputItem, false);

                        inserter.state = InserterState.TRANSFERRING;
                        inserter.progress = 0;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                    } else {
                        // The Inserter wasn't able to successfully take item
                        inserter.state = InserterState.WAIT_SOURCE;
                        inserter.progress = 0;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                    }
                }
                break;
            }
            case TRANSFERRING: {
                if (inserter.progress < maxProgress) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;

                    if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        inserter.state = InserterState.RETURNING;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                    } else {
                        inserter.state = InserterState.WAIT_DESTINATION;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                    }
                }
                break;
            }
            case WAIT_DESTINATION: {
                if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                    // Someone took the item from the inserter through menu
                    inserter.state = InserterState.RETURNING;
                    inserter.progress = 0;

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                    break;
                }

                BlockPos dest_pos = blockPos.relative(dir, 1);
                ItemStack stack = inserter.itemHandler.getStackInSlot(0);
                int res = putItem(dest_pos, dir, level, stack, true);
                if (res != -1) {
                    // The Inserter can put item since inventory has empty slot
                    inserter.state = InserterState.INSERTING;
                    inserter.progress = 0;

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                }
                break;
            }
            case INSERTING: {
                if (inserter.progress < maxProgress) {
                    inserter.progress += inserter.move_speed;
                } else {
                    if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        // Someone took the item from the inserter through menu
                        inserter.state = InserterState.RETURNING;
                        inserter.progress = 0;

                        level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                        break;
                    }

                    BlockPos dest_pos = blockPos.relative(dir, 1);
                    ItemStack item = inserter.itemHandler.extractItem(0, inserter.itemHandler.getStackInSlot(0).getCount(), false);
                    int res = putItem(dest_pos, dir, level, item, false);

                    inserter.progress = 0;
                    inserter.state = InserterState.RETURNING;

                    if (res == -1) {
                        ItemEntity itemEntity = new ItemEntity(level, dest_pos.getX() + 0.5, dest_pos.getY() + 0.5, dest_pos.getZ() + 0.5, item);
                        level.addFreshEntity(itemEntity);
                    }

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                }
                break;
            }
            case RETURNING: {
                if (inserter.progress < maxProgress) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;
                    inserter.state = InserterState.WAIT_SOURCE;

                    level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_CLIENTS);
                }
                break;
            }

        }

    }
}
