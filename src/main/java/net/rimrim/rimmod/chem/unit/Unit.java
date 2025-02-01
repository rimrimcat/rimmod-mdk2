package net.rimrim.rimmod.chem.unit;

import java.util.function.Function;

public record Unit(int unit_class, int id, String symbol, Operation ops) implements IUnit {
//    public Function<Float, Float> converter(Unit otherUnit) {
//        assert (this.unit_class == otherUnit.unit_class);
//        // x -> x
//        if (this.id == otherUnit.id) return (x) -> {
//            return x;
//        };
//        // 1/2/3 -> 0
//        if (otherUnit.id == 0) {
//            return this.ops.asFunction();
//        }
//        // 0 -> 1/2/3
//        if (this.id == 0) {
//            return otherUnit.ops.clone().invert().asFunction();
//        }
//        // 1/2/3 -> 0 -> 1/2/3
//        return this.ops.combine(otherUnit.ops.clone().invert()).asFunction();
//    }

    public Function<Float, Float> converter(IUnit otherUnit_I) {
        if (otherUnit_I instanceof Unit otherUnit) {
            assert (this.unit_class == otherUnit.unit_class());
            // x -> x
            if (this.id == otherUnit.id()) return (x) -> {
                return x;
            };
            // 1/2/3 -> 0
            if (otherUnit.id() == 0) {
                return this.ops.asFunction();
            }
            // 0 -> 1/2/3
            if (this.id == 0) {
                return otherUnit.ops.clone().invert().asFunction();
            }
            // 1/2/3 -> 0 -> 1/2/3
            return this.ops.combine(otherUnit.ops.clone().invert()).asFunction();
        }
        return null;
    }

//    public Operation converterOps(Unit otherUnit) {
//        assert (this.unit_class == otherUnit.unit_class);
//        // x -> x
//        if (this.id == otherUnit.id) return new Operation(0);
//        // 1/2/3 -> 0
//        if (otherUnit.id == 0) {
//            return this.ops.clone();
//        }
//        // 0 -> 1/2/3
//        if (this.id == 0) {
//            return otherUnit.ops.invert();
//        }
//        // 1/2/3 -> 0 -> 1/2/3
//        return this.ops.combine(otherUnit.ops.invert());
//    }

    public Operation converterOps(IUnit otherUnit_I) {
        if (otherUnit_I instanceof Unit otherUnit) {
            assert (this.unit_class == otherUnit.unit_class());
            // x -> x
            if (this.id == otherUnit.id()) return new Operation(0);
            // 1/2/3 -> 0
            if (otherUnit.id() == 0) {
                return this.ops;
            }
            // 0 -> 1/2/3
            if (this.id == 0) {
                return otherUnit.ops.clone().invert();
            }
            // 1/2/3 -> 0 -> 1/2/3
            return this.ops.combine(otherUnit.ops.clone().invert());
        }
        return null;
    }

    public Unit attach(Unit otherUnit) {
        int new_id = this.id + otherUnit.id;
        String new_symbol = this.symbol + otherUnit.symbol;
        Operation new_ops = this.ops.combine(otherUnit.ops);

        if (this.unit_class == 0 || otherUnit.unit_class == 0) {
            // at least one unit is of type GENERAL

            return new Unit(
                    this.unit_class + otherUnit.unit_class,
                    new_id,
                    new_symbol,
                    new_ops
            );
        }
        return null;
    }

    public CompositeUnit times(Unit otherUnit) {
        CompositeUnit cunit = new CompositeUnit();
        cunit.times(this);
        cunit.times(otherUnit);
        return cunit;
    }

    public CompositeUnit per(Unit otherUnit) {
        CompositeUnit cunit = new CompositeUnit();
        cunit.times(this.clone());
        cunit.per(otherUnit.clone());
        return cunit;
    }

    public Unit clone() {
        return new Unit(this.unit_class, this.id, this.symbol, this.ops.clone());
    }

    public int unit_class() {
        return this.unit_class;
    }

    public int id() {
        return this.id;
    }

    public String symbol() {
        return this.symbol;
    }

    public Operation ops() {
        return this.ops;
    }


    public static class GENERAL {
        public static final int uc = 0;

        public static final Unit UNITY = new Unit(uc, 0, "", new Operation(0));

        public static final Unit DECI = new Unit(uc, -1, "d", new Operation(1).multiply(0.1f));
        public static final Unit CENTI = new Unit(uc, -2, "c", new Operation(1).multiply(0.01f));
        public static final Unit MILLI = new Unit(uc, -3, "m", new Operation(1).multiply(0.001f));
        public static final Unit MICRO = new Unit(uc, -6, "µ", new Operation(1).multiply(0.000001f));
        public static final Unit NANO = new Unit(uc, -9, "n", new Operation(1).multiply(0.000000001f));
        public static final Unit PICO = new Unit(uc, -12, "p", new Operation(1).multiply(0.000000000001f));
        public static final Unit FEMTO = new Unit(uc, -15, "f", new Operation(1).multiply(0.000000000000001f));
        public static final Unit ATTO = new Unit(uc, -18, "a", new Operation(1).multiply(0.000000000000000001f));

        public static final Unit DECA = new Unit(uc, 1, "D", new Operation(1).multiply(10f));
        public static final Unit HECTO = new Unit(uc, 2, "H", new Operation(1).multiply(100f));
        public static final Unit KILO = new Unit(uc, 3, "K", new Operation(1).multiply(1000f));
        public static final Unit MEGA = new Unit(uc, 6, "M", new Operation(1).multiply(1000000f));
        public static final Unit GIGA = new Unit(uc, 9, "G", new Operation(1).multiply(1000000000f));
        public static final Unit TERA = new Unit(uc, 12, "T", new Operation(1).multiply(1000000000000f));
        public static final Unit PETA = new Unit(uc, 15, "P", new Operation(1).multiply(1000000000000000f));
        public static final Unit EXA = new Unit(uc, 18, "E", new Operation(1).multiply(1000000000000000000f));
    }

    public static class TEMPERATURE {
        public static final int uc = 1;

        public static final Unit K = new Unit(uc, 0, "K", new Operation(0));
        // THESE OTHER OPERATIONS MUST BE TO CONVERT TO KELVIN
        public static final Unit R = new Unit(uc, 100, "R", new Operation(1).multiply(1 / 1.8f));
        public static final Unit C = new Unit(uc, 200, "C", new Operation(1).add(273.15f));
        public static final Unit F = new Unit(uc, 300, "F", new Operation(2).add(459.67f).multiply(1 / 1.8f));
    }

    public static class PRESSURE {
        public static final int uc = 2;

        public static final Unit BAR = new Unit(uc, 0, "bar", new Operation(0));
        public static final Unit PA = new Unit(uc, 100, "Pa", new Operation(1).multiply(1e-5f));
    }

    public static class TIME {
        public static final int uc = 3;

        public static final Unit SECOND = new Unit(uc, 0, "s", new Operation(0));
    }

    public static class DISTANCE {
        public static final int uc = 4;

        public static final Unit METER = new Unit(uc, 0, "m", new Operation(0));

    }

    public static class MASS {
        public static final int uc = 5;

        public static final Unit KILOGRAM = new Unit(uc, 0, "kg", new Operation(0));
        public static final Unit GRAM = new Unit(uc, 100, "g", new Operation(1).multiply(0.001f));

    }

    public static class MOLE {
        public static final int uc = 6;

        public static final Unit KILOMOLE = new Unit(uc, 0, "Kmol", new Operation(0));
        public static final Unit MOLE = new Unit(uc, 100, "mol", new Operation(1).multiply(0.001f));
    }

    public static class ENERGY {
        public static final int uc = 7;

        public static final Unit JOULE = new Unit(uc, 0, "J", new Operation(0));

    }

    public static class POWER {
        public static final int uc = 8;

        public static final Unit WATT = new Unit(uc, 0, "W", new Operation(0));

    }

    public static class VOLUME {
        public static final int uc = 9;

        public static final Unit CUBIC_METER = new Unit(uc, 0, "m3", new Operation(0));
    }

}
