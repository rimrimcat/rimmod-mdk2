package net.rimrim.rimmod.chem.correlation.type.base;

import net.rimrim.rimmod.chem.enums.ProcessVariableType;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public interface IFunction {

    float evaluate(EnumMap<ProcessVariableType, Float> processVars);
}
