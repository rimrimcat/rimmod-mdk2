package net.rimrim.rimmod.chem.props.inter;

import net.rimrim.rimmod.chem.correlation.type.UnsetProperty;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;

public interface IVaporProperties {
    float molecular_weight = 0;

    IFunction vapor_density = new UnsetProperty();
    IFunction vapor_viscosity = new UnsetProperty();
    IFunction vapor_heat_capacity = new UnsetProperty();
    IFunction vapor_thermal_conductivity = new UnsetProperty();


    float w();
    float acentric_factor();
}
