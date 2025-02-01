package net.rimrim.rimmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.blockentity.ConductiveBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ConductiveBlock extends BaseEntityBlock implements EntityBlock {
    public static final MapCodec<ConductiveBlock> CODEC = simpleCodec(ConductiveBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public ConductiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConductiveBlockEntity(pos, state);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ConductiveBlockEntity conductiveBlockEntity) {
            conductiveBlockEntity.getPropHandler().updateConductibles((Level) level, pos);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        level.invalidateCapabilities(pos);
    }
}
