package net.rimrim.rimmod.chem.enums;

import net.rimrim.rimmod.chem.unit.CompositeUnit;
import net.rimrim.rimmod.chem.unit.IUnit;
import net.rimrim.rimmod.chem.unit.Unit;

/**
 * Copy of VariableType, but fewer variables
 */
public enum ProcessVariableType {
    TEMPERATURE(Unit.TEMPERATURE.K),
    PRESSURE(Unit.PRESSURE.BAR),
    MASS(Unit.MASS.KILOGRAM),
    MOLE(Unit.MOLE.KILOMOLE),
    VOLUME(Unit.VOLUME.CUBIC_METER),
    ;

    public final IUnit defaultUnit;

    private ProcessVariableType(IUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    public IUnit defaultUnit() {
        return this.defaultUnit;
    }
}
