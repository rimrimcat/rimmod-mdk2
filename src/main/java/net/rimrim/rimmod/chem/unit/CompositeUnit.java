package net.rimrim.rimmod.chem.unit;

import java.util.HashMap;
import java.util.function.Function;

public class CompositeUnit implements IUnit {
    // COMMON UNITS
    public static final CompositeUnit G_PER_MOLE = Unit.MASS.GRAM.per(Unit.MOLE.KILOMOLE);
    public static final CompositeUnit J_PER_KG_K = Unit.ENERGY.JOULE.per(Unit.MASS.KILOGRAM).per(Unit.TEMPERATURE.K);
    public static final CompositeUnit W_PER_M_K = Unit.POWER.WATT.per(Unit.DISTANCE.METER).per(Unit.TEMPERATURE.K);

    private final HashMap<Integer, Unit> map;

    public CompositeUnit() {
        this.map = new HashMap<>();
    }

    public CompositeUnit(HashMap<Integer, Unit> map) {
        this.map = map;
    }

//    public Function<Float, Float> converter(CompositeUnit otherUnit) {
//        assert (this.map.keySet() == otherUnit.map.keySet());
//
//        Operation ops = new Operation(0);
//
//        for (Integer unit_class : this.map.keySet()) {
//
//            ops = ops.combine(
//                    this.map.get(unit_class).converterOps(otherUnit.map.get(unit_class))
//            );
//        }
//
//        return ops.asFunction();
//    }

    public Function<Float, Float> converter(IUnit otherUnit_I) {
        if (otherUnit_I instanceof CompositeUnit otherUnit) {
            assert (this.map.keySet() == otherUnit.map.keySet());

            Operation ops = new Operation(0);

            for (Integer unit_class : this.map.keySet()) {

                ops = ops.combine(
                        this.map.get(unit_class).converterOps(otherUnit.map.get(unit_class))
                );
            }

            return ops.asFunction();
        }
        return null;
    }

//    public Operation converterOps(CompositeUnit otherUnit) {
//        assert (this.map.keySet() == otherUnit.map.keySet());
//
//        Operation ops = new Operation(0);
//
//        for (Integer unit_class : this.map.keySet()) {
//            Unit this_unit = this.map.get(unit_class);
//            Unit other_unit = otherUnit.map.get(unit_class);
//
//            if (this_unit.id() == other_unit.id()) {
//                continue;
//            }
//
//            Operation obtained_ops = this_unit.converterOps(other_unit);
//
//            ops = ops.combine(
//                    obtained_ops
//            );
//        }
//
//        return ops;
//    }

    public Operation converterOps(IUnit otherUnit_I) {
        if (otherUnit_I instanceof CompositeUnit otherUnit) {
            assert (this.map.keySet() == otherUnit.map.keySet());

            Operation ops = new Operation(0);

            for (Integer unit_class : this.map.keySet()) {
                Unit this_unit = this.map.get(unit_class);
                Unit other_unit = otherUnit.map.get(unit_class);

                if (this_unit.id() == other_unit.id()) {
                    continue;
                }

                Operation obtained_ops = this_unit.converterOps(other_unit);

                ops = ops.combine(
                        obtained_ops
                );
            }

            return ops;
        }
        return null;
    }

    public CompositeUnit times(Unit otherUnit) {
        map.put(otherUnit.unit_class(), otherUnit);
        return this;
    }

    public CompositeUnit per(Unit otherUnit) {
        map.put(-otherUnit.unit_class(), otherUnit);
        return this;
    }

    public int unit_class() {
        return map.keySet().stream().mapToInt(Integer::intValue).sum();
    }

    public int id() {
        return map.values().stream().mapToInt(Unit::id).sum();
    }

    public String symbol() {
        String numerator = map.entrySet().stream()
                .filter(entry -> entry.getKey() > 0)
                .map(entry -> entry.getValue().symbol())
                .reduce((a, b) -> a + "-" + b)
                .orElse("");

        String denominator = map.entrySet().stream()
                .filter(entry -> entry.getKey() < 0)
                .map(entry -> entry.getValue().symbol())
                .reduce((a, b) -> a + "-" + b)
                .orElse("");

        return numerator + "/" + denominator;
    }

}
