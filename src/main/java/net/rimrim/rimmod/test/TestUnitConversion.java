package net.rimrim.rimmod.test;

import net.rimrim.rimmod.chem.unit.CompositeUnit;
import net.rimrim.rimmod.chem.unit.Unit;

public class TestUnitConversion {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("        PERFORMING TESTS         ");
        System.out.println("=================================");

        // Convert 25 C to K
        System.out.println();
        System.out.println("Converting 25 C to K");
        float tempK = Unit.TEMPERATURE.C.converter(Unit.TEMPERATURE.K).apply(25f);
        System.out.println("Expected: 298.15, Got: " + tempK);

        // Create unit km
        System.out.println();
        System.out.println("Creating new unit KILO + METER");
        Unit km = Unit.GENERAL.KILO.attach(Unit.DISTANCE.METER);
        System.out.println("Expected: Km, Got: " + km.symbol());

        // Convert km to meter
        System.out.println();
        System.out.println("Converting 1 km to meter");
        float outmeter = km.converter(Unit.DISTANCE.METER).apply(1f);
        System.out.println("Expected: 1000.0, Got: " + outmeter);

        // Convert kPa to bar
        System.out.println();
        System.out.println("Converting 1 kPa to bar");
        float outbar = Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA).converter(Unit.PRESSURE.BAR).apply(1f);
        System.out.println("Expected: 0.01, Got: " + outbar);


        // Convert bar to kPa
        System.out.println();
        System.out.println("Converting 1 bar to KPa");
        float outkpa = Unit.PRESSURE.BAR.converter(Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA)).apply(1f);
        System.out.println("Expected: 100.0, Got: " + outkpa);

        // Create composite unit
        System.out.println();
        System.out.println("Creating new unit METER / SECOND");
        CompositeUnit cunit = Unit.DISTANCE.METER.per(Unit.TIME.SECOND);
        System.out.println("Expected: m/s, Got: " + cunit.symbol());

        // Convert composite unit
        System.out.println();
        System.out.println("Converting 1 bar-s to KPa-s");
        CompositeUnit bar_s = Unit.PRESSURE.BAR.per(Unit.TIME.SECOND);
        CompositeUnit kPa_s = Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA).per(Unit.TIME.SECOND);
        float out_KPa_s = bar_s.converter(kPa_s).apply(1f);
        System.out.println("Expected: 100.0, Got: " + out_KPa_s);

        System.out.println("=================================");
        System.out.println("             DONE!               ");
        System.out.println("=================================");

    }
}
