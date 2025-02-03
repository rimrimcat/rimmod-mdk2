package net.rimrim.rimmod.chem.props.base;

import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.SpeciesBuilder;
import net.rimrim.rimmod.chem.props.inface.ISolidProperties;

import java.util.EnumMap;

public class PureSolidSpecies extends AbstractSpecies implements ISolidProperties {

    public final IFunction solid_density;
    public final IFunction solid_viscosity;
    public final IFunction solid_heat_capacity;
    public final IFunction solid_thermal_conductivity;
    public final IFunction solid_vapor_pressure;

    public PureSolidSpecies(SpeciesBuilder builder) {
        super(builder);
        this.solid_density = builder.solid_density;
        this.solid_viscosity = builder.solid_viscosity;
        this.solid_heat_capacity = builder.solid_heat_capacity;
        this.solid_thermal_conductivity = builder.solid_thermal_conductivity;
        this.solid_vapor_pressure = builder.solid_vapor_pressure;
    }


    @Override
    public MatterState state(EnumMap<VariableType, Float> processVars) {
        return MatterState.SOLID;
    }

    @Override
    public float density(EnumMap<VariableType, Float> processVars) {
        return solid_density.evaluate(processVars);
    }

    @Override
    public float viscosity(EnumMap<VariableType, Float> processVars) {
        return solid_viscosity.evaluate(processVars);
    }

    @Override
    public float heat_capacity(EnumMap<VariableType, Float> processVars) {
        return solid_heat_capacity.evaluate(processVars);
    }

    @Override
    public float thermal_conductivity(EnumMap<VariableType, Float> processVars) {
        return solid_thermal_conductivity.evaluate(processVars);
    }

    @Override
    public float vapor_pressure(EnumMap<VariableType, Float> processVars) {
        return solid_vapor_pressure.evaluate(processVars);
    }
}
