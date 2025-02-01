package net.rimrim.rimmod.chem.correlation.type.base;

import java.util.function.Function;

public record BaseForm(int num_constants, int num_inputs, Function<FunctionInput, Float> formula) {


    public int num_constants() {
        return this.num_constants;
    }

    public int num_inputs() {
        return this.num_inputs;
    }

    public Function<FunctionInput, Float> formula() {
        return this.formula;
    }


}
