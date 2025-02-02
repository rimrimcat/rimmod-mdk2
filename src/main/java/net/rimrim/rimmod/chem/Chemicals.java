package net.rimrim.rimmod.chem;

import net.rimrim.rimmod.chem.correlation.Correlations;
import net.rimrim.rimmod.chem.correlation.type.ConstantProperty;
import net.rimrim.rimmod.chem.props.ChemTags;
import net.rimrim.rimmod.chem.props.PureSpecies;
import net.rimrim.rimmod.chem.props.SpeciesBuilder;
import net.rimrim.rimmod.chem.props.base.AbstractSpecies;
import net.rimrim.rimmod.chem.props.base.PureLiquidSpecies;
import net.rimrim.rimmod.chem.props.base.PureSolidSpecies;
import net.rimrim.rimmod.chem.props.base.PureVaporSpecies;

import java.util.HashMap;

public class Chemicals {
    public static AbstractSpecies AIR = new PureVaporSpecies(new SpeciesBuilder()
            .name("air")
            .MW(28.96)
            .vapor_density(new ConstantProperty(1.225))
            .vapor_heat_capacity(new ConstantProperty(1000))
            .vapor_thermal_conductivity(new ConstantProperty(0))
    );

    public static AbstractSpecies WATER = new PureLiquidSpecies(new SpeciesBuilder()
            .name("water")
            .tags(ChemTags.WATER.toBuilder().liquidOnly().build())
            .fromSmiles("O")
            .Tc(647.096)
            // Convert 322 kg/m3 to molar volume
            //            .solid_density(new ConstantProperty(910))
            .liquid_density(new ConstantProperty(1000))
            .liquid_heat_capacity(new ConstantProperty(4.186))
            .liquid_thermal_conductivity(new ConstantProperty(0.598))
            .liquid_vapor_pressure(Correlations.WATER_PSAT_ANTOINE)
    );

    public static AbstractSpecies IRON = new PureSolidSpecies(new SpeciesBuilder()
            .name("iron")
            .fromSmiles("Fe")
            .solid_density(new ConstantProperty(7.874))
            .solid_heat_capacity(new ConstantProperty(0.451))
            .solid_thermal_conductivity(new ConstantProperty(73))
    );

}
