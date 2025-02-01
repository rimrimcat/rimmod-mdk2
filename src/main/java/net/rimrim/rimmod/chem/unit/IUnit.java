package net.rimrim.rimmod.chem.unit;

import java.util.function.Function;

public interface IUnit {

    Function<Float, Float> converter(IUnit otherUnit);
    Operation converterOps(IUnit otherUnit);
}
