package net.rimrim.rimmod.transport;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.chem.props.base.AbstractSpecies;
import net.rimrim.rimmod.chem.container.Cubic;
import net.rimrim.rimmod.chem.container.IContainerShape;
import net.rimrim.rimmod.transport.heat.HeatHandler;
import net.rimrim.rimmod.transport.util.DirectionalCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class TransportHandler {

    public DirectionalCapabilityCache<ITransportHandler> dcache;
    private final AbstractSpecies containerMaterial;
    private final ChemicalStackHandler chemHandler;

    // TODO: MAKE TRANSPORTHANDLER MODULAR
    private final HeatHandler heatHandler;


    public TransportHandler(ChemicalStackHandler chemHandler) {
        this.containerMaterial = Chemicals.IRON;

        this.chemHandler = chemHandler;
        this.heatHandler = new HeatHandler(this);
    }

    public ChemicalStackHandler getChemHandler() {
        return this.chemHandler;
    }

    public @Nullable HeatHandler getHeatHandler() {
        return this.heatHandler;
    }

    public void tick() {
        // TODO tick other handlers
    }

    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        dcache.update((Level) level, pos);
    }


}
