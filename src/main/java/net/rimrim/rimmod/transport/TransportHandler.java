package net.rimrim.rimmod.transport;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.props.inface.IPropertyAccess;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.chem.props.base.AbstractSpecies;
import net.rimrim.rimmod.transport.heat.HeatHandler;
import net.rimrim.rimmod.transport.util.DirectionalCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class TransportHandler {

    public DirectionalCapabilityCache<TransportHandler> dcache;
    private final ChemicalStackHandler chemHandler;

    // TODO: MAKE TRANSPORT HANDLER MODULAR
    // TODO: ENUMS FOR HEAT HANDLER TYPES?
    private final HeatHandler heatHandler;


    public TransportHandler(ChemicalStackHandler chemHandler) {
        this.chemHandler = chemHandler;
        this.heatHandler = new HeatHandler(this);
    }

    public TransportHandler() {
        this.chemHandler = new ChemicalStackHandler(0);
        this.heatHandler = new HeatHandler(this);
    }

    public ChemicalStackHandler chemHandler() {
        return this.chemHandler;
    }

    public @Nullable HeatHandler heatHandler() {
        return this.heatHandler;
    }

    public void tick() {
        // TODO tick other handlers
    }

    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        dcache.update((Level) level, pos);
    }


}
