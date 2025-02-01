package net.rimrim.rimmod.test;

import net.rimrim.rimmod.chem.correlation.Correlations;
import net.rimrim.rimmod.chem.correlation.type.PropertyCorrelation;

public class TestCorrelation {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("        PERFORMING TESTS         ");
        System.out.println("=================================");
        long start = System.nanoTime();

        // Try to evaluate PSAT
        System.out.println("");
        PropertyCorrelation buck = Correlations.WATER_PSAT_ARDEN_BUCK;
        PropertyCorrelation antoine = Correlations.WATER_PSAT_ANTOINE;
        PropertyCorrelation refrig = Correlations.WATER_PSAT_REFRIGERANT;
        float Tc = 647.096f;

        for (int i = 0; i <= 20; i++) {
            float T_CELSIUS = i * 10f;
            float T_KELVIN = T_CELSIUS + 273.15f;
            System.out.println("T = " + T_CELSIUS + " C" +
                    " | BCK = " + buck.evaluate(T_KELVIN) +
                    " | ANT = " + antoine.evaluate(T_KELVIN) +
                    " | REF = " + refrig.evaluate(T_KELVIN, Tc) +
                    " "
            );
        }


        long stop = System.nanoTime();
        System.out.println("Time taken: " + ((stop - start)/1000000) + "ms");

        System.out.println("=================================");
        System.out.println("             DONE!               ");
        System.out.println("=================================");

    }
}
