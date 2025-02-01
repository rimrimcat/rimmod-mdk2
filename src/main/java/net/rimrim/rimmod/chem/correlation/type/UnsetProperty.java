package net.rimrim.rimmod.chem.correlation.type;

import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public class UnsetProperty implements IFunction {

    public UnsetProperty() {
    }

    public float evaluate(EnumMap<VariableType, Float> processVars) {
        RimMod.LOGGER.error("Unset Property has been called!");
        return 0;
    }
}
