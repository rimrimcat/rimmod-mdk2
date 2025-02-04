package net.rimrim.rimmod.chem.container;

import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.container.shape.Cubic;
import net.rimrim.rimmod.chem.container.shape.IContainerShape;
import net.rimrim.rimmod.chem.enums.MatterState;
import net.rimrim.rimmod.chem.enums.ProcessVariableType;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.base.AbstractSpecies;
import net.rimrim.rimmod.chem.props.inface.IPropertyAccess;

import java.util.EnumMap;

public class ChemContainer implements IPropertyAccess {

    private final IContainerShape shape;
    private final AbstractSpecies material;
    private final float density;
    private final EnumMap<ProcessVariableType, Float> containerVars;

    public ChemContainer(IContainerShape shape, AbstractSpecies material) {
        this.shape = shape;
        this.material = material;

        containerVars = new EnumMap<>(ProcessVariableType.class);
        containerVars.put(ProcessVariableType.TEMPERATURE, 25 + 273.15f);
        containerVars.put(ProcessVariableType.PRESSURE, 1f);

        density = material.density(containerVars);

        float mass = (shape.outer_volume() - shape.inner_volume()) * density;
        containerVars.put(ProcessVariableType.MASS, mass);
        containerVars.put(ProcessVariableType.MOLE, mass / material.MW());
        containerVars.put(ProcessVariableType.VOLUME, mass / density);

    }

    public ChemContainer(IContainerShape shape) {
        this(shape, Chemicals.IRON);
    }

    public ChemContainer(float volume) {
        this(new Cubic(volume));
    }

    public IContainerShape shape() {
        return this.shape;
    }

    public AbstractSpecies material() {
        return this.material;
    }

    public void mapIncrement(ProcessVariableType varType, float value) {
        this.containerVars.put(varType, this.containerVars.get(varType) + value);
    }

    public void mapDecrement(ProcessVariableType varType, float value) {
        this.containerVars.put(varType, this.containerVars.get(varType) - value);
    }


    // IPropertyAccess
    public float T() {
        return containerVars.get(ProcessVariableType.TEMPERATURE);
    }

    public float P() {
        return containerVars.get(ProcessVariableType.PRESSURE);
    }

    public float m() {
        return containerVars.get(ProcessVariableType.MASS);
    }

    public float mol() {
        return containerVars.get(ProcessVariableType.MOLE);
    }

    public float MW() {
        return material.MW();
    }

    public float rho() {
        return this.density;
    }

    public float V() {
        return containerVars.get(ProcessVariableType.VOLUME);
    }

    public MatterState state() {
        return MatterState.SOLID;
    }

    public float k() {
        return material.thermal_conductivity(containerVars);
    }

    public float cp() {
        return material.heat_capacity(containerVars);
    }


}
