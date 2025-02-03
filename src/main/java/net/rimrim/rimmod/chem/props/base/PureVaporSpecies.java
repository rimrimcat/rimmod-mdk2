package net.rimrim.rimmod.chem.props.base;

import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.SpeciesBuilder;
import net.rimrim.rimmod.chem.props.inface.IVaporProperties;

import java.util.EnumMap;

public class PureVaporSpecies extends AbstractSpecies implements IVaporProperties {

    public final float acentric_factor;

    public final IFunction vapor_density;
    public final IFunction vapor_viscosity;
    public final IFunction vapor_heat_capacity;
    public final IFunction vapor_thermal_conductivity;

    public PureVaporSpecies(SpeciesBuilder builder) {
        super(builder);
        this.acentric_factor = builder.acentric_factor;
        this.vapor_density = builder.vapor_density;
        this.vapor_viscosity = builder.vapor_viscosity;
        this.vapor_heat_capacity = builder.vapor_heat_capacity;
        this.vapor_thermal_conductivity = builder.vapor_thermal_conductivity;
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
        return MatterState.VAPOR;
    }

    @Override
    public float density(EnumMap<VariableType, Float> processVars) {
        return vapor_density.evaluate(processVars);
    }

    @Override
    public float viscosity(EnumMap<VariableType, Float> processVars) {
        return vapor_viscosity.evaluate(processVars);
    }

    @Override
    public float heat_capacity(EnumMap<VariableType, Float> processVars) {
        return vapor_heat_capacity.evaluate(processVars);
    }

    @Override
    public float thermal_conductivity(EnumMap<VariableType, Float> processVars) {
        return vapor_thermal_conductivity.evaluate(processVars);
    }

    @Override
    public float vapor_pressure(EnumMap<VariableType, Float> processVars) {
        return 0;
    }
}
