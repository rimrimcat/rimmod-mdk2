package net.rimrim.rimmod.chem.correlation.type.base;

public record FunctionInput(float[] constants, float[] inputs) {

    public float[] c() {
        return this.constants;
    }

    public float c(int i) {
        return this.constants[i];
    }

    public float[] p() {
        return this.inputs;
    }

    public float p(int i) {
        return this.inputs[i];
    }

}
