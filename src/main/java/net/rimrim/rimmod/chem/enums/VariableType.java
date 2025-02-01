package net.rimrim.rimmod.chem.enums;

import net.rimrim.rimmod.chem.unit.CompositeUnit;
import net.rimrim.rimmod.chem.unit.IUnit;
import net.rimrim.rimmod.chem.unit.Unit;

public enum VariableType {
    MOLECULAR_WEIGHT(0, CompositeUnit.G_PER_MOLE),
    HEAT_CAPACITY(1, CompositeUnit.J_PER_KG_K),
    THERMAL_CONDUCTIVITY(2, CompositeUnit.W_PER_M_K),
    MASS(3, Unit.MASS.KILOGRAM),
    MOLE(4, Unit.MOLE.KILOMOLE),
    VOLUME(5, Unit.VOLUME.CUBIC_METER),


    TEMPERATURE(1000, Unit.TEMPERATURE.K),
    TEMPERATURE_SATURATION(1100, Unit.TEMPERATURE.K),
    TEMPERATURE_CRITICAL(1200, Unit.TEMPERATURE.K),
    PRESSURE(2000, Unit.PRESSURE.BAR),
    PRESSURE_SATURATION(2100, Unit.PRESSURE.BAR),
    ;

    public int id;
    public final IUnit defaultUnit;

    private VariableType(int id, IUnit defaultUnit) {
        this.id = id;
        this.defaultUnit = defaultUnit;
    }

    public VariableType renumber(int ind) {
        this.id += ind;
        return this;
    }

    public int id() {
        return this.id;
    }

    public IUnit defaultUnit() {
        return this.defaultUnit;
    }

}
