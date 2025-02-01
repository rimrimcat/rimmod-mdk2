package net.rimrim.rimmod.chem.unit;

import net.rimrim.rimmod.RimMod;

public class Limit {
    public static final Limit NONE = new Limit(Type.NONE, 0);

    public static Limit GREATER_THAN(float value1) {
        return new Limit(Type.GREATER_THAN, value1);
    }

    public static Limit GREATER_THAN(double value1) {
        return new Limit(Type.GREATER_THAN,(float) value1);
    }


    public static Limit LESS_THAN(float value1) {
        return new Limit(Type.LESS_THAN, value1);
    }
    public static Limit LESS_THAN(double value1) {
        return new Limit(Type.LESS_THAN, (float) value1);
    }


    public static Limit WITHIN(float value1, float value2) {
        return new Limit(Type.WITHIN, value1, value2);
    }

    public static Limit WITHIN(double value1, double value2) {
        return new Limit(Type.WITHIN, (float) value1, (float) value2);
    }

    private final Type type;
    private final float value1;
    private final float value2;

    public Limit(Type type, float value1, float value2) {
        this.type = type;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Limit(Type type, float value1) {
        this.type = type;
        this.value1 = value1;
        this.value2 = 0;
    }


    private boolean checkLimit(float otherValue) {
        return switch (this.type) {
            case GREATER_THAN -> otherValue > this.value1;
            case LESS_THAN -> otherValue < this.value1;
            case WITHIN -> otherValue >= this.value1 && otherValue <= this.value2;
            case NONE -> true;
        };
    }

    public void check(float otherValue) {
        boolean chk = checkLimit(otherValue);

        if (!chk) {
            String msg = "Value is not ";
            switch (this.type) {
                case GREATER_THAN -> msg += "greater than " + this.value1;
                case LESS_THAN -> msg += "less than " + this.value1;
                case WITHIN -> msg += "within [" + this.value1 + ", " + this.value2 + "]";
            }
//            RimMod.LOGGER.error("Value outside limit! " + msg);
            System.out.println("Value outside limit! " + msg);
        }
    }

    public enum Type {
        GREATER_THAN(1),
        LESS_THAN(-1),
        WITHIN(0),
        NONE(69);


        private final int id;

        private Type(int id) {
            this.id = id;
        }


    }

}
