package net.rimrim.rimmod.chem.props;

import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.MatterState;

public abstract class PureDependentProperty {

    private IFunction solid_density;
    private IFunction liquid_density;
    private IFunction vapor_density;

    private IFunction solid_viscosity;
    private IFunction liquid_viscosity;
    private IFunction vapor_viscosity;

    private IFunction solid_heat_capacity;
    private IFunction liquid_heat_capacity;
    private IFunction vapor_heat_capacity;

    private IFunction solid_thermal_conductivity;
    private IFunction liquid_thermal_conductivity;
    private IFunction vapor_thermal_conductivity;

    private IFunction solid_vapor_pressure;
    private IFunction liquid_vapor_pressure;


    public boolean isBoiling(float temperature, float pressure) {
        return false;
    }

    public boolean isSolidifying(float temperature, float pressure) {
        return false;
    }

    public MatterState state(float temperature, float pressure) {
        return null;
    }

    public float heat_of_vaporization(float temperature, float pressure) {
        return 0;
    }

    public float heat_of_melting(float temperature, float pressure) {
        return 0;
    }

    // These functions should check for state and use the correct type
    public float density(float temperature, float pressure) {
        return 0;
    }

    public float viscosity(float temperature, float pressure) {
        return 0;
    }

    public float heat_capacity(float temperature, float pressure) {
        return 0;
    }

    public float thermal_conductivity(float temperature, float pressure) {
        return 0;
    }

    public float vapor_pressure(float temperature, float pressure) {
        return 0;
    }

}
