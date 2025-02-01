package net.rimrim.rimmod.chem.container;

import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.PureSpecies;
import net.rimrim.rimmod.chem.Chemicals;

import java.util.EnumMap;
import java.util.Objects;

import static net.rimrim.rimmod.chem.enums.VariableType.*;


public class ChemicalStack {
    public static final ChemicalStack EMPTY = new ChemicalStack(Chemicals.NONE, 0);

    private final PureSpecies chemical;
    private final EnumMap<VariableType, Float> processVars;
    // TODO: IMPLEMENT PHASES

    public ChemicalStack(PureSpecies chemical, VariableType var, float value) {
        this.chemical = chemical;
        this.processVars = new EnumMap<>(VariableType.class);

        processVars.put(TEMPERATURE, 25 + 273.15f);
        processVars.put(PRESSURE, 1f);

        addAmount(var, value);
    }

    public ChemicalStack(PureSpecies chemical, float mass) {
        this(chemical, MASS, mass);
    }

    public ChemicalStack(PureSpecies chemical, EnumMap<VariableType, Float> pVars) {
        this.chemical = chemical;
        this.processVars = pVars;
    }

    public float T() {
        return this.processVars.get(VariableType.TEMPERATURE);
    }

    public float P() {
        return this.processVars.get(VariableType.PRESSURE);
    }

    public float m() {
        return this.processVars.get(MASS);
    }

    public float MW() {
        return this.chemical.MW();
    }

    public float rho() {
        return chemical.density(T(), P());
    }

    public float V() {
        return this.processVars.get(VOLUME);
    }

    public PureSpecies chemical() {
        return this.chemical;
    }

    public void mapIncrement(VariableType varType, float value) {
        this.processVars.put(varType, this.processVars.get(varType) + value);
    }

    public void mapDecrement(VariableType varType, float value) {
        this.processVars.put(varType, this.processVars.get(varType) - value);
    }


    public void addAmount(VariableType varType, float value) {
        switch (varType) {
            case MASS -> addMass(value);
            case MOLE -> addMole(value);
            case VOLUME -> addVolume(value);
        }
    }

    public void addMass(float mass) {
        // by default, kg
        mapIncrement(MASS, mass);
        // Update other vars
        float new_m = m();
        processVars.put(MOLE, new_m / MW());
        processVars.put(VOLUME, new_m / rho());
    }

    public void addMole(float mole) {
        this.addMass(mole * MW());
    }

    public void addVolume(float volume) {
        this.addMass(volume * rho());
    }

    public void deductAmount(VariableType varType, float value) {
        switch (varType) {
            case MASS -> deductMass(value);
            case MOLE -> deductMole(value);
            case VOLUME -> deductVolume(value);
        }
    }

    public void deductMass(float mass) {
        // by default, kg
        mapDecrement(MASS, mass);
        // Update other vars
        float new_m = m();
        processVars.put(MOLE, new_m / MW());
        processVars.put(VOLUME, new_m / rho());
    }

    public void deductMole(float mole) {
        this.deductMass(mole * MW());
    }

    public void deductVolume(float volume) {
        this.deductMass(volume * rho());
    }


    public void setAmount(VariableType varType, float value) {
        switch (varType) {
            case MASS -> setMass(value);
            case MOLE -> setMole(value);
            case VOLUME -> setVolume(value);
        }
    }

    public void setMass(float mass) {
        this.processVars.put(MASS, mass);

        // Update other vars
        processVars.put(MOLE, mass / MW());
        processVars.put(VOLUME, mass / rho());
    }

    public void setMole(float mole) {
        this.setMass(mole * MW());
    }

    public void setVolume(float volume) {
        this.setMass(volume * rho());
    }


    public boolean is(ChemicalStack chemStack) {
        return this.chemical == chemStack.chemical;
    }

    public boolean is(String name) {
        return Objects.equals(this.chemical.name, name);
    }

    public boolean isEmpty() {
        return this == EMPTY || this.chemical == Chemicals.NONE || m() == 0;
    }

    public ChemicalStack copy() {
        return (this.isEmpty()) ? EMPTY : new ChemicalStack(this.chemical, m());
    }

    public ChemicalStack copyWithAmount(VariableType varType, float value) {
        ChemicalStack chemCopy = this.copy();
        chemCopy.setAmount(varType, value);
        return chemCopy;
    }

    public String toString() {
        return m() + " kg " + this.chemical.name;
    }


}
