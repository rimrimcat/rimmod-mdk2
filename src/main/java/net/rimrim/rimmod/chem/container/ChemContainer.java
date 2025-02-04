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
    private final float mass;
    private final float density;
    private final EnumMap<ProcessVariableType, Float> containerVars;

    public ChemContainer(IContainerShape shape, AbstractSpecies material) {
        this.shape = shape;
        this.material = material;

        containerVars = new EnumMap<>(ProcessVariableType.class);
        containerVars.put(ProcessVariableType.TEMPERATURE, 25 + 273.15f);
        containerVars.put(ProcessVariableType.PRESSURE, 1f);

        this.density = material.density(containerVars);
        this.mass = (shape.outer_volume() - shape.inner_volume()) * this.density;
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

    // IPropertyAccess
    public float T() {
        return containerVars.get(ProcessVariableType.TEMPERATURE);
    }

    public float P() {
        return containerVars.get(ProcessVariableType.PRESSURE);
    }

    public float m() {
        return this.mass;
    }

    public float mol() {
        return m() / MW();
    }

    public float MW() {
        return material.MW();
    }

    public float rho() {
        return this.density;
    }

    public float V() {
        return m() / rho();
    }

    public MatterState state() {
        return MatterState.SOLID;
    }

    public float k() {
        return material.thermal_conductivity(containerVars);
    }


}
