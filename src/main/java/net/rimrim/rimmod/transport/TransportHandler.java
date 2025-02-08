package net.rimrim.rimmod.transport;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.transport.heat.HeatHandlingType;
import net.rimrim.rimmod.transport.heat.IHeatHandler;
import net.rimrim.rimmod.transport.heat.RegularHeatHandler;
import net.rimrim.rimmod.transport.util.DirectionalCapabilityCache;
import org.jetbrains.annotations.Nullable;

public class TransportHandler {

    public DirectionalCapabilityCache<TransportHandler> dcache;
    private final ChemicalStackHandler chemHandler;

    // TODO: MAKE TRANSPORT HANDLER MODULAR
    private final IHeatHandler heatHandler;


    // TODO: ADD OTHER HANDLERS
    public TransportHandler(ChemicalStackHandler chemHandler) {
        this.chemHandler = chemHandler;
        this.heatHandler = new RegularHeatHandler(this);
    }

    public TransportHandler() {
        this.chemHandler = new ChemicalStackHandler(0);
        this.heatHandler = new RegularHeatHandler(this);
    }

    public ChemicalStackHandler chemHandler() {
        return this.chemHandler;
    }

    public IHeatHandler heatHandler() {
        return this.heatHandler;
    }

    public void tick() {
        // TODO tick other handlers
    }

    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        dcache.update((Level) level, pos);
        // TODO Update max temperature difference of heat handler
    }

    public static class Builder {
        private ChemicalStackHandler chemHandler;

        private IHeatHandler heatHandler;
        private HeatHandlingType handlingType;

        public Builder() {

        }

        public Builder chemHandler(ChemicalStackHandler chemHandler) {
            this.chemHandler = chemHandler;
            return this;
        }

        public Builder heatHandler(IHeatHandler heatHandler) {
            this.heatHandler = heatHandler;
            return this;
        }

        public Builder heatType(HeatHandlingType heatType) {
            this.handlingType = heatType;
            return this;
        }

        // TODO: BUILD METHOD





    }


}
