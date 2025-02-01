package net.rimrim.rimmod.chem.container;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.PureSpecies;
import org.jetbrains.annotations.UnknownNullability;

public class ChemicalStackHandler implements INBTSerializable<CompoundTag> {

    private float volume; // m3
    private ChemicalStack chemStack = ChemicalStack.EMPTY;

    // Is closed or not

    public ChemicalStackHandler() {
        this.volume = 1;
    }

    public ChemicalStackHandler(float volume) {
        this.volume = volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getRemainingVolume() {
        return (this.chemStack.isEmpty()) ? this.volume : this.volume - this.chemStack.V();
    }

    public void setChemicalAmount(VariableType varType, float value) {
        if (this.chemStack.isEmpty()) return;

        this.chemStack.addAmount(varType, value);
    }

    public ChemicalStack insertChemical(ChemicalStack otherChemStack, boolean simulate) {
        if (otherChemStack.isEmpty()) return ChemicalStack.EMPTY;

        if (otherChemStack.V() <= getRemainingVolume()) {
            // Chemical can fit

            // Just set the chemstack to this if Handler is empty
            if (this.chemStack.isEmpty()) {
                if (!simulate) this.chemStack = otherChemStack;
                return ChemicalStack.EMPTY;
            }

            // Add the chemical stack ONLY if same chemical
            if (this.chemStack.is(otherChemStack)) {
                if (!simulate) this.chemStack.addMass(otherChemStack.m());
                return ChemicalStack.EMPTY;
            } else {
                // Return if different chemical
                return otherChemStack;
            }
        } else {
            // Have excess, cant insert all
            float insertableVolume = this.getRemainingVolume();
            float excessVolume = otherChemStack.V() - insertableVolume;

            // Just set the chemstack to this if Handler is empty
            if (this.chemStack.isEmpty()) {
                if (!simulate) this.chemStack = otherChemStack.copyWithAmount(VariableType.VOLUME, insertableVolume);
                return otherChemStack.copyWithAmount(VariableType.VOLUME, excessVolume);
            }

            // Add chemical only if same
            if (this.chemStack.is(otherChemStack)) {
                if (!simulate) this.chemStack.addVolume(insertableVolume);
                return otherChemStack.copyWithAmount(VariableType.VOLUME, excessVolume);
            } else {
                // Return if different chemical
                return otherChemStack;
            }
        }
    }

    public ChemicalStack extractChemical(PureSpecies chemical, VariableType varType, float value, boolean simulate) {
        if (this.chemStack.isEmpty() || chemical == Chemicals.NONE) return ChemicalStack.EMPTY;
        if (!this.chemStack.is(chemical.name)) return ChemicalStack.EMPTY;

        float requestedMass;
        switch (varType) {
            case MASS -> requestedMass = value;
            case MOLE -> requestedMass = value * chemStack.MW();
            case VOLUME -> requestedMass = value * chemStack.rho();
            default -> requestedMass = value;
        }

        if (requestedMass < this.chemStack.m()) {
            // Can extract, with some remaining in container
            if (!simulate) this.chemStack.deductMass(requestedMass);
            return this.chemStack.copyWithAmount(VariableType.MASS, requestedMass);
        } else {
            // Extract all
            ChemicalStack chemCopy = this.chemStack.copy();
            if (!simulate) this.chemStack = ChemicalStack.EMPTY;
            return chemCopy;
        }
    }


    protected void onLoad() {
    }

    protected void onContentsChanged() {
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

    }
}
