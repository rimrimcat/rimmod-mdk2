package net.rimrim.rimmod.chem.props.inter;

import net.rimrim.rimmod.chem.correlation.type.UnsetProperty;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;

public interface ILiquidProperties {

    float acentric_factor = 0;

    IFunction liquid_density = new UnsetProperty();
    IFunction liquid_viscosity = new UnsetProperty();
    IFunction liquid_heat_capacity = new UnsetProperty();
    IFunction liquid_thermal_conductivity = new UnsetProperty();
    IFunction liquid_vapor_pressure = new UnsetProperty();

    float w();
    float acentric_factor();
}
