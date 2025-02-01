package net.rimrim.rimmod.props.base;

public interface ITempDependentProperties {

    // Props
    float density();

    float heat_capacity();

    float thermal_conductivity();

    //
    float density(float temp, float press);

    float heat_capacity(float temp, float press);

    float thermal_conductivity(float temp, float press);


}
