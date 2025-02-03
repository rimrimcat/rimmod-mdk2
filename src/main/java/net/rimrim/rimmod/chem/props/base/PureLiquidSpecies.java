package net.rimrim.rimmod.chem.props.base;

import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.SpeciesBuilder;
import net.rimrim.rimmod.chem.props.inface.ILiquidProperties;

import java.util.EnumMap;

public class PureLiquidSpecies extends AbstractSpecies implements ILiquidProperties {

    public final float acentric_factor;

    public final IFunction liquid_density;
    public final IFunction liquid_viscosity;
    public final IFunction liquid_heat_capacity;
    public final IFunction liquid_thermal_conductivity;
    public final IFunction liquid_vapor_pressure;

    public PureLiquidSpecies(SpeciesBuilder builder) {
        super(builder);
        this.acentric_factor = builder.acentric_factor;
        this.liquid_density = builder.liquid_density;
        this.liquid_viscosity = builder.liquid_viscosity;
        this.liquid_heat_capacity = builder.liquid_heat_capacity;
        this.liquid_thermal_conductivity = builder.liquid_thermal_conductivity;
        this.liquid_vapor_pressure = builder.liquid_vapor_pressure;
    }

    @Override
    public float w() {
        return this.acentric_factor;
    }

    @Override
    public float acentric_factor() {
        return this.w();
    }


    @Override
    public MatterState state(EnumMap<VariableType, Float> processVars) {
        return MatterState.LIQUID;
    }

    @Override
    public float density(EnumMap<VariableType, Float> processVars) {
        return liquid_density.evaluate(processVars);
    }

    @Override
    public float viscosity(EnumMap<VariableType, Float> processVars) {
        return liquid_viscosity.evaluate(processVars);
    }

    @Override
    public float heat_capacity(EnumMap<VariableType, Float> processVars) {
        return liquid_heat_capacity.evaluate(processVars);
    }

    @Override
    public float thermal_conductivity(EnumMap<VariableType, Float> processVars) {
        return liquid_thermal_conductivity.evaluate(processVars);
    }

    @Override
    public float vapor_pressure(EnumMap<VariableType, Float> processVars) {
        return liquid_vapor_pressure.evaluate(processVars);
    }
}
