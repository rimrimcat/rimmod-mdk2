package net.rimrim.rimmod.chem.props.base;

import net.rimrim.rimmod.chem.props.ChemTags;
import net.rimrim.rimmod.chem.props.SpeciesBuilder;
import net.rimrim.rimmod.chem.props.inface.ICompoundProperties;
import net.rimrim.rimmod.chem.props.inface.IConstantProperties;

public abstract class AbstractSpecies implements IConstantProperties, ICompoundProperties {

    public final String name;
    public final ChemTags tags;
    public final int color;

    public final float molecular_weight;
    public final float critical_temperature;
    public final float critical_pressure;
    public final float critical_molar_volume;
    public final float critical_compressibility_factor;

    /**
     * Construct a new AbstractSpecies.
     * @param builder A SpeciesBuilder which contains all the necessary information to construct a species.
     */
    protected AbstractSpecies(SpeciesBuilder builder) {
        this.name = builder.name;
        this.tags = builder.tags;
        this.color = builder.color;
        this.molecular_weight = builder.molecular_weight;
        this.critical_temperature = builder.critical_temperature;
        this.critical_pressure = builder.critical_pressure;
        this.critical_molar_volume = builder.critical_molar_volume;
        this.critical_compressibility_factor = builder.critical_compressibility_factor;
    }

    /**
     * Return the molecular weight of the species in kg/kmol.
     * @return The molecular weight of the species in kg/kmol.
     */
    public float MW() {
        return this.molecular_weight;
    }

    /**
     * Return the critical temperature of the species in K.
     * @return The critical temperature of the species in K.
     */
    public float Tc() {
        return this.critical_temperature;
    }

    /**
     * Return the critical pressure of the species in bar.
     * @return The critical pressure of the species in bar.
     */
    public float Pc() {
        return this.critical_pressure;
    }

    /**
     * Return the critical molar volume of the species in cubic meters per kmol.
     * @return The critical molar volume of the species in cubic meters per kmol.
     */
    public float Vc() {
        return this.critical_molar_volume;
    }

    /**
     * Return the critical compressibility factor of the species.
     * @return The critical compressibility factor of the species.
     */
    public float Zc() {
        return this.critical_compressibility_factor;
    }

    // Alias
    public float molecular_weight() {
        return this.MW();
    }

    public float critical_temperature() {
        return this.Tc();
    }

    public float critical_pressure() {
        return this.Pc();
    }

    public float critical_molar_volume() {
        return this.Vc();
    }

    public float critical_compressibility_factor() {
        return this.Zc();
    }
}
