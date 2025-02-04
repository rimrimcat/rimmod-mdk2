package net.rimrim.rimmod.chem.props.inface;

import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.ProcessVariableType;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public interface ICompoundProperties {

    public MatterState state(EnumMap<ProcessVariableType, Float> processVars);

    float density(EnumMap<ProcessVariableType, Float> processVars);

    float viscosity(EnumMap<ProcessVariableType, Float> processVars);

    float heat_capacity(EnumMap<ProcessVariableType, Float> processVars);

    float thermal_conductivity(EnumMap<ProcessVariableType, Float> processVars);

    float vapor_pressure(EnumMap<ProcessVariableType, Float> processVars);

}
