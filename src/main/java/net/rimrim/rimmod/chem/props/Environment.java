package net.rimrim.rimmod.chem.props;

public class Environment {

    private boolean isConstantPressure;
    private boolean isConstantTemperature;


    public Environment() {
        this.isConstantTemperature = false;
        this.isConstantPressure = false;
    }

    public Environment constantP() {
        this.isConstantPressure = true;
        return this;
    }

    public Environment constantT() {
        this.isConstantTemperature = true;
        return this;
    }


}
