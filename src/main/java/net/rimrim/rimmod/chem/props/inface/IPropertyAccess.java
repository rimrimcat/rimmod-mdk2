package net.rimrim.rimmod.chem.props.inface;

import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.VariableType;

import static net.rimrim.rimmod.chem.enums.VariableType.*;

public interface IPropertyAccess {

    /**
     * Get the temperature of the substance in K.
     *
     * @return The temperature of the substance in K.
     */
    float T();

    /**
     * Get the pressure of the substance in bar.
     *
     * @return The pressure of the substance in bar.
     */
    float P();

    /**
     * Get the mass of the substance in kg.
     *
     * @return The mass of the substance in kg.
     */
    float m();

    /**
     * Get the mole of the substance in kmol.
     *
     * @return The mole of the substance in kmol.
     */
    float mol();

    /**
     * Get the molecular weight of the substance in kg/kmol.
     *
     * @return The molecular weight of the substance in kg/kmol.
     */
    float MW();

    /**
     * Get the density of the substance.
     *
     * @return The density of the substance in kg/m^3.
     */
    float rho();


    /**
     * Get the volume of the substance in m^3.
     *
     * @return The volume of the substance in m^3.
     */
    float V();

    /**
     * Get the state of the substance.
     *
     * @return The state of the substance.
     */
    MatterState state();

    /**
     * Get the thermal conductivity of the substance in W/m-K.
     *
     * @return The thermal conductivity of the substance in W/m-K.
     */
    float k();
}
