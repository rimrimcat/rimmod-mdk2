package net.rimrim.rimmod.chem.correlation;

import net.rimrim.rimmod.chem.correlation.type.PropertyCorrelation;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.IUnit;
import net.rimrim.rimmod.chem.unit.Limit;
import net.rimrim.rimmod.chem.unit.Parameter;
import net.rimrim.rimmod.chem.unit.Unit;

public class Correlations {
    // UNITS
    private static final IUnit CELSIUS = Unit.TEMPERATURE.C;
    private static final IUnit KELVIN = Unit.TEMPERATURE.K;

    private static final IUnit HECTOPASCAL =  Unit.GENERAL.HECTO.attach(Unit.PRESSURE.PA);
    private static final IUnit KILOPASCAL =  Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA);

    public static PropertyCorrelation WATER_PSAT_ARDEN_BUCK = new PropertyCorrelation.Builder(Forms.ARDEN_BUCK)
            .constants(6.1121, 18.678, 234.5, 254.14)
            .input(new Parameter(VariableType.TEMPERATURE,
                    CELSIUS,
                    Limit.GREATER_THAN(0)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    HECTOPASCAL))
            .reference("https://en.wikipedia.org/wiki/Arden_Buck_equation")
            .build();


    public static PropertyCorrelation ICE_PSAT_ARDEN_BUCK = new PropertyCorrelation.Builder(Forms.ARDEN_BUCK)
            .constants(6.1115, 23.036, 333.7, 279.82)
            .input(new Parameter(VariableType.TEMPERATURE,
                    CELSIUS,
                    Limit.LESS_THAN(0)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    HECTOPASCAL))
            .reference("https://en.wikipedia.org/wiki/Arden_Buck_equation")
            .build();

    public static PropertyCorrelation WATER_PSAT_ANTOINE = new PropertyCorrelation.Builder(Forms.ANTOINE)
            .constants(16.3872, 3885.70, 230.170)
            .input(new Parameter(VariableType.TEMPERATURE,
                    CELSIUS,
                    Limit.WITHIN(0, 200)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    KILOPASCAL))
            .reference("SVAS 9th ed")
            .build();

    /*
    More complicated but less accurate!!!
     */
    public static PropertyCorrelation WATER_PSAT_REFRIGERANT = new PropertyCorrelation.Builder(Forms.REFRIGERANT_PSAT)
            .constants(9.56756, 5.39806, -6.16183, 1.49572, 0.43300)
            .input(new Parameter(VariableType.TEMPERATURE,
                    KELVIN))
            .input(new Parameter(VariableType.TEMPERATURE_CRITICAL,
                    KELVIN))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    KILOPASCAL,
                    Limit.WITHIN(0.61, 22038.91)))
            .reference("https://doi.org/10.1016/j.proeng.2013.02.095")
            .build();
}
