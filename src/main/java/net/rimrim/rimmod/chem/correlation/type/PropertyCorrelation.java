package net.rimrim.rimmod.chem.correlation.type;

import net.rimrim.rimmod.chem.correlation.type.base.BaseForm;
import net.rimrim.rimmod.chem.correlation.type.base.FunctionInput;
import net.rimrim.rimmod.chem.correlation.type.base.IFunction;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.Limit;
import net.rimrim.rimmod.chem.unit.Parameter;

import java.util.EnumMap;
import java.util.function.Function;

public class PropertyCorrelation implements IFunction {
    private final Function<Float, Float>[] inputConvs;
    private final Function<Float, Float> outputConv;
    private final VariableType[] accessedVars;

    private final float[] constants;
    private final Function<FunctionInput, Float> formula;
    private final String reference;

    private final Limit[] inputLimits;
    private final Limit outputLimit;
    // TODO: LIMITS?

    @SuppressWarnings("unchecked")
    public PropertyCorrelation(Parameter[] inputs, Parameter output, float[] constants, Function<FunctionInput, Float> formula, String reference) {
        this.constants = constants;
        this.formula = formula;
        this.reference = reference;

        this.inputConvs = new Function[inputs.length]; // TODO: This might cause problems but dunno
        this.accessedVars = new VariableType[inputs.length];
        this.inputLimits = new Limit[inputs.length];

        // create input converters
        for (int i = 0; i < inputs.length; i++) {
            inputConvs[i] = inputs[i].variable().defaultUnit().converter(inputs[i].correlationUnit());
            accessedVars[i] = inputs[i].variable();
            inputLimits[i] = inputs[i].limit();
        }

        // create output converter
        this.outputConv = output.correlationUnit().converter(output.variable().defaultUnit());
        this.outputLimit = output.limit();
    }


    public float evaluate(EnumMap<VariableType, Float> processVars) {
        float[] inputs = new float[accessedVars.length];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = inputConvs[i].apply(processVars.get(accessedVars[i]));

            // limit check for input
            inputLimits[i].check(inputs[i]);
        }

        float unconvertedOutput = formula.apply(new FunctionInput(this.constants, inputs));

        // limit check for output
        outputLimit.check(unconvertedOutput);

        return outputConv.apply(unconvertedOutput);
    }

    public float evaluate(float... vals) {
        float[] inputs = new float[accessedVars.length];

        for (int i = 0; i < inputs.length; i++) {


            inputs[i] = inputConvs[i].apply(vals[i]);

            // limit check for input
            inputLimits[i].check(inputs[i]);
        }

        float unconvertedOutput = formula.apply(new FunctionInput(this.constants, inputs));


        // limit check for output
        outputLimit.check(unconvertedOutput);

        return outputConv.apply(unconvertedOutput);
    }

    public static class Builder {
        private Parameter[] inputs;
        private Parameter output;
        private float[] constants;
        private Function<FunctionInput, Float> formula;
        private String reference = "no ref";

        private int paramIndex = 0;

        public Builder(BaseForm base) {
            inputs = new Parameter[base.num_inputs()];
            formula = base.formula();
        }

        public Builder constants(float... constants) {
            this.constants = constants;
            return this;
        }

        public Builder constants(double... dconstants) {
            this.constants = new float[dconstants.length];
            for (int i = 0; i < dconstants.length; i++) {
                this.constants[i] = (float) dconstants[i];
            }

            return this;
        }

        public Builder input(Parameter p) {
            this.inputs[paramIndex] = p;
            paramIndex += 1;
            return this;
        }

        public Builder output(Parameter p) {
            this.output = p;
            return this;
        }

        public Builder formula(Function<FunctionInput, Float> form) {
            this.formula = form;
            return this;
        }

        public Builder reference(String ref) {
            this.reference = ref;
            return this;
        }

        public PropertyCorrelation build() {
            return new PropertyCorrelation(inputs, output, constants, formula, reference);
        }


    }


}
