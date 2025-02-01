package net.rimrim.rimmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import net.rimrim.rimmod.init.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class InserterBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<InserterBlock> CODEC = simpleCodec(InserterBlock::new);
    public static final EnumProperty<Direction> INSERT_DIRECTION = EnumProperty.create("insert_direction", Direction.class, Direction.Plane.HORIZONTAL);;

    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 12.0, 2.0, 12.0);

    public InserterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(INSERT_DIRECTION, Direction.NORTH)
//                .setValue(STATE, InserterState.WAIT_SOURCE)
//                .setValue(PROGRESS, 0)
        );
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INSERT_DIRECTION);
//        builder.add(STATE);
//        builder.add(PROGRESS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(INSERT_DIRECTION, context.getHorizontalDirection().getOpposite())
//                .setValue(STATE, InserterState.WAIT_SOURCE)
//                .setValue(PROGRESS, 0)
                ;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InserterBlockEntity(pos, state);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(INSERT_DIRECTION, rot.rotate(state.getValue(INSERT_DIRECTION)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(INSERT_DIRECTION)));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (player instanceof ServerPlayer sPlayer && level.getBlockEntity(pos) instanceof InserterBlockEntity inserterBlockEntity) {
            sPlayer.openMenu((MenuProvider) inserterBlockEntity, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof InserterBlockEntity) {
                ItemStackHandler inventory = ((InserterBlockEntity) be).getItemHandler();
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack item = inventory.getStackInSlot(i);
                    ItemEntity itemEntity = new ItemEntity(level, 0.5 + pos.getX(), 0.5 + pos.getY(), 0.5 + pos.getZ(), item);
                    level.addFreshEntity(itemEntity);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;  // This makes the block itself invisible
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level.isClientSide() && type == ModBlockEntities.INSERTER.get()) ? null : InserterBlockEntity::tick;
    }
}





