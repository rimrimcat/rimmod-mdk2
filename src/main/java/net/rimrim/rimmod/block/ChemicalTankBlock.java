package net.rimrim.rimmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rimrim.rimmod.blockentity.ChemicalTankBlockEntity;
import net.rimrim.rimmod.blockentity.TankBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ChemicalTankBlock extends Block implements EntityBlock {
    public static final MapCodec<ChemicalTankBlock> CODEC = simpleCodec(ChemicalTankBlock::new);

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 13.0, 13.0, 13.0);


    public ChemicalTankBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ChemicalTankBlockEntity(blockPos, blockState);
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
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // TODO: MENU AND TICKER
}
