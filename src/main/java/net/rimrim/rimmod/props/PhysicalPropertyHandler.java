package net.rimrim.rimmod.props;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.init.ModCapabilities;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class PhysicalPropertyHandler implements IPhysicalPropertyHandler  /*implements ITemperatureHandler */ /*, INBTSerializable<CompoundTag>*/ {
    protected float energy; // internal
    protected float temperature;
    protected float pressure;
    protected float mass;

    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_up;
    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_down;
    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_north;
    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_south;
    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_west;
    private BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache_east;

    public PhysicalPropertyHandler() {
        this(298, 1);
    }

    public PhysicalPropertyHandler(float initTemp) {
        this(initTemp, 1);
    }

    public PhysicalPropertyHandler(float initTemp, float initPress) {
        // Back calculate energy from initial temp
        // U = rho*V*C*T

        this.mass = density(initTemp, initPress) * 1; // rho*V
        this.energy = mass * heat_capacity(initTemp, initPress) * initTemp;
        this.temperature = initTemp;
        this.pressure = initPress;
    }

    // Process Variables
    public float energy() {
        return this.energy;
    }

    public float temperature() {
        return this.temperature;
    }

    public float pressure() {
        return this.pressure;
    }

    public float volume() {
        return mass / density();
    }

    public float mass() {
        return mass;
    }

    public float area(Direction side) {
        return 1f; // m2
    }

    // Properties
    public float density() {
        return this.density(temperature, pressure);
    }

    public float heat_capacity() {
        return this.heat_capacity(temperature, pressure);
    }

    public float thermal_conductivity() {
        return this.thermal_conductivity(temperature, pressure);
    }

    public float density(float temp, float press) {
        return 7850f; // in kg/m3
    }

    public float heat_capacity(float temp, float press) {
        return 554f; // in J/kg-K
    }

    public float thermal_conductivity(float temp, float press) {
        return 80.2f; // in W/m-K
    }

    // Transfer Mechanisms
    public void updateTemperature(float newTemperature) {
        this.temperature = newTemperature;
        this.energy = mass * heat_capacity() * newTemperature;
    }

    public void updateConductibles(Level level, BlockPos pos) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        
        IPhysicalPropertyHandler propHandler_up = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.above(), Direction.DOWN);
        if (propHandler_up != null) {
            this.capCache_up = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.above(),
                    Direction.DOWN
            );
        }

        IPhysicalPropertyHandler propHandler_down = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.below(), Direction.UP);
        if (propHandler_down != null) {
            this.capCache_down = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.below(),
                    Direction.UP
            );
        }

        IPhysicalPropertyHandler propHandler_north = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.north(), Direction.SOUTH);
        if (propHandler_north != null) {
            this.capCache_north = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.north(),
                    Direction.SOUTH
            );
        }

        IPhysicalPropertyHandler propHandler_south = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.south(), Direction.NORTH);
        if (propHandler_south != null) {
            this.capCache_south = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.south(),
                    Direction.NORTH
            );
        }

        IPhysicalPropertyHandler propHandler_west = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.west(), Direction.EAST);
        if (propHandler_west != null) {
            this.capCache_west = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.west(),
                    Direction.EAST
            );
        }

        IPhysicalPropertyHandler propHandler_east = level.getCapability(ModCapabilities.PhysPropHandler.BLOCK, pos.east(), Direction.WEST);
        if (propHandler_east != null) {
            this.capCache_east = BlockCapabilityCache.create(
                    ModCapabilities.PhysPropHandler.BLOCK,
                    serverLevel,
                    pos.east(),
                    Direction.WEST
            );
        }

    }

    public void conductAll() {
        for (BlockCapabilityCache<IPhysicalPropertyHandler, @Nullable Direction> capCache : Arrays.asList(
                this.capCache_up,
                this.capCache_down,
                this.capCache_north,
                this.capCache_south,
                this.capCache_west,
                this.capCache_east
        )) {
            if (capCache != null) {
                IPhysicalPropertyHandler otherHandler = capCache.getCapability();
                if (otherHandler != null) {
                    this.conduct(otherHandler);
                }
            }
        }
    }

    public void conduct(IPhysicalPropertyHandler otherHandler) {
        // Conduction should only work from high temp to low temp
        float T_A = this.temperature();
        float T_B = otherHandler.temperature();
        if (T_B > T_A) {
            otherHandler.conduct(this);
            return;
        }

        float thermal_res = 0.5f / this.thermal_conductivity() + 0.5f / otherHandler.thermal_conductivity();
        float flux = -(T_B - T_A) / thermal_res;

        float mC_A = this.mass() * this.heat_capacity();
        float mC_B = otherHandler.mass() * otherHandler.heat_capacity();

        this.updateTemperature(-flux * 0.1f / mC_A + T_A);
        otherHandler.updateTemperature(flux * 0.1f / mC_B + T_B);
    }

}
