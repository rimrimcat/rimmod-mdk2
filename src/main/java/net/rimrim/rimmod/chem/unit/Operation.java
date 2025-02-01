package net.rimrim.rimmod.chem.unit;

import java.util.Arrays;
import java.util.function.Function;

public class Operation {
    private final int[] ops;
    // 0 = add
    // 1 = multiply
    private final float[] values;
    private int index;

    public Operation(int num_ops) {
        ops = new int[num_ops];
        values = new float[num_ops];
        index = 0;
    }

    public Operation() {
        this(5);
    }

    public Operation(int[] ops, float[] values) {
        this.ops = ops;
        this.values = values;
        this.index = ops.length;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            switch (ops[i]) {
                case 0:
                    sb.append(" + ").append(values[i]);
                    break;
                case 1:
                    sb.append(" * ").append(values[i]);
                    break;
            }
        }
        return sb.toString();
    }

    public Operation add(float value) {
        ops[index] = 0;
        values[index] = value;
        index += 1;
        return this;
    }

    public Operation multiply(float value) {
        ops[index] = 1;
        values[index] = value;
        index += 1;
        return this;
    }

    public Operation invert() {
        // NOT INPLACE
        int[] invertedOps = new int[ops.length];
        float[] invertedValues = new float[values.length];

        for (int i = ops.length - 1; i >= 0; i--) {
            switch (ops[i]) {
                case 0:
                    invertedOps[ops.length - 1 - i] = 0;
                    invertedValues[ops.length - 1 - i] = -values[i];
                    break;
                case 1:
                    invertedOps[ops.length - 1 - i] = 1;
                    invertedValues[ops.length - 1 - i] = 1f / values[i];
                    break;
            }
        }

        return new Operation(invertedOps, invertedValues);
    }

    public Function<Float, Float> asFunction() {
        return x -> {
            float result = x;
            for (int i = 0; i < index; i++) {
                result = (ops[i] == 0) ? result + values[i] : result * values[i];
            }
            return result;
        };
    }

    public float apply(float x) {
        return this.asFunction().apply(x);
    }

    public Operation clone() {
        Operation clone = new Operation(ops.length);
        System.arraycopy(ops, 0, clone.ops, 0, ops.length);
        System.arraycopy(values, 0, clone.values, 0, values.length);
        clone.index = index;
        return clone;
    }

    public Operation combine(Operation other) {
        Operation combined = new Operation(index + other.index);
        System.arraycopy(ops, 0, combined.ops, 0, index);
        System.arraycopy(values, 0, combined.values, 0, index);
        System.arraycopy(other.ops, 0, combined.ops, index, other.index);
        System.arraycopy(other.values, 0, combined.values, index, other.index);
        combined.index = index + other.index;
        return combined;
    }

    public Operation combine(Operation... ops) {
        int totalLength = index;
        for (Operation op : ops) {
            totalLength += op.index;
        }
        Operation combined = new Operation(totalLength);
        int offset = 0;
        System.arraycopy(this.ops, 0, combined.ops, 0, this.index);
        System.arraycopy(this.values, 0, combined.values, 0, this.index);
        offset += this.index;
        for (Operation op : ops) {
            System.arraycopy(op.ops, 0, combined.ops, offset, op.index);
            System.arraycopy(op.values, 0, combined.values, offset, op.index);
            offset += op.index;
        }
        combined.index = totalLength;
        return combined;
    }


}