package net.rimrim.rimmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import net.rimrim.rimmod.blockentity.TankBlockEntity;
import net.rimrim.rimmod.init.ModBlockEntities;
import org.jetbrains.annotations.Nullable;


public class TankBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<TankBlock> CODEC = simpleCodec(TankBlock::new);


    public TankBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TankBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer().isShiftKeyDown()) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        } else {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
    }

//    @SuppressWarnings("unchecked") // Due to generics, an unchecked cast is necessary here.
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
//        // You can return different tickers here, depending on whatever factors you want. A common use case would be
//        // to return different tickers on the client or server, only tick one side to begin with,
//        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
//        return type == ModBlockEntities.TANK_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>) TankBlockEntity::tick : null;
//    }


    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (player instanceof ServerPlayer sPlayer && level.getBlockEntity(pos) instanceof TankBlockEntity blockEntity) {
            sPlayer.openMenu(blockEntity, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level.isClientSide() && type == ModBlockEntities.TANK.get()) ? null : TankBlockEntity::tick;
    }
}
