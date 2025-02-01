package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.props.PhysicalPropertyHandler;

public class ConductiveBlockEntity extends BlockEntity {

    private final PhysicalPropertyHandler propHandler = new PhysicalPropertyHandler();


    public ConductiveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONDUCTIVE_BLOCK.get(), pos, state);
        propHandler.updateConductibles(level, pos);
    }


    public PhysicalPropertyHandler getPropHandler() {
        return propHandler;
    }




}
