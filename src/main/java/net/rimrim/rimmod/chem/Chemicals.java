package net.rimrim.rimmod.chem;

import net.rimrim.rimmod.chem.correlation.Correlations;
import net.rimrim.rimmod.chem.correlation.type.ConstantProperty;
import net.rimrim.rimmod.chem.props.ChemTags;
import net.rimrim.rimmod.chem.props.PureSpecies;

import java.util.HashMap;

public class Chemicals {
    public static PureSpecies NONE = new PureSpecies.Builder()
            .name("none")
            .build();

    public static PureSpecies WATER = new PureSpecies.Builder()
            .name("water")
            .tags(ChemTags.WATER)
            .fromSmiles("O")
            .Tc(647.096)
            // Convert 322 kg/m3 to molar volume
//            .solid_density(new ConstantProperty(910))
            .liquid_density(new ConstantProperty(1000))
            .liquid_heat_capacity(new ConstantProperty(4.186))
            .liquid_thermal_conductivity(new ConstantProperty(0.598))
//            .solid_vapor_pressure(Correlations.ICE_PSAT_ARDEN_BUCK)
            .liquid_vapor_pressure(Correlations.WATER_PSAT_ANTOINE)
            .build();


}
