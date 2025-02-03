package net.rimrim.rimmod.chem.props.inface;

import net.rimrim.rimmod.chem.correlation.type.UnsetProperty;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;

public interface ISolidProperties {

    float acentric_factor = 0;

    IFunction solid_density = new UnsetProperty();
    IFunction solid_heat_capacity = new UnsetProperty();
    IFunction solid_thermal_conductivity = new UnsetProperty();
    IFunction solid_vapor_pressure = new UnsetProperty();
}
