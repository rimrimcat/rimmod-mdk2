package net.rimrim.rimmod.chem.props.inface;

import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public interface ICompoundProperties {

    public MatterState state(EnumMap<VariableType, Float> processVars);

    float density(EnumMap<VariableType, Float> processVars);

    float viscosity(EnumMap<VariableType, Float> processVars);

    float heat_capacity(EnumMap<VariableType, Float> processVars);

    float thermal_conductivity(EnumMap<VariableType, Float> processVars);

    float vapor_pressure(EnumMap<VariableType, Float> processVars);


}
