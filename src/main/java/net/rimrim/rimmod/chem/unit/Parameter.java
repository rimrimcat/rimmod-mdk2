package net.rimrim.rimmod.chem.unit;

import net.rimrim.rimmod.chem.enums.VariableType;

public class Parameter {

    private final VariableType variable;
    private final IUnit correlationUnit;
    private Limit limit;


    public Parameter(VariableType variable, IUnit correlationUnit) {
        this.variable = variable;
        this.correlationUnit = correlationUnit;
        this.limit = Limit.NONE;
    }

    public Parameter(VariableType variable, IUnit correlationUnit, Limit limit) {
        this.variable = variable;
        this.correlationUnit = correlationUnit;
        this.limit = limit;
    }

    public VariableType variable() {
        return this.variable;
    }

    public IUnit correlationUnit() {
        return this.correlationUnit;
    }

    public Limit limit() {
        return this.limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

}
