package net.rimrim.rimmod.chem.container;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.Chemicals;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.base.AbstractSpecies;
import net.rimrim.rimmod.init.ModChemicals;
import org.jetbrains.annotations.UnknownNullability;

public class ChemicalStackHandler implements INBTSerializable<CompoundTag> {

    private float volume; // m3
    private ChemicalStack chemStack;
    private AbstractSpecies spaceFillingChemical;

    // Is closed or not
    // Implicit that empty handler means filled with air

    public ChemicalStackHandler() {
        this(Chemicals.AIR, 1);
    }

    public ChemicalStackHandler(float volume) {
        this(Chemicals.AIR, volume);
    }

    public ChemicalStackHandler(AbstractSpecies spaceFiller, float volume) {
        this.volume = volume;
        this.spaceFillingChemical = spaceFiller;
        this.chemStack = ChemicalStack.EMPTY;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        this.onContentsChanged();
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
        this.onContentsChanged();
    }

    public ChemicalStack insertChemical(ChemicalStack otherChemStack, boolean simulate) {
        if (otherChemStack.isEmpty()) return ChemicalStack.EMPTY;

        if (otherChemStack.V() <= getRemainingVolume()) {
            // Chemical can fit

            // Just set the chemstack to this if Handler is empty
            if (this.chemStack.isEmpty()) {
                if (!simulate) {
                    this.chemStack = otherChemStack;
                    this.onContentsChanged();
                }
                return ChemicalStack.EMPTY;
            }

            // Add the chemical stack ONLY if same chemical
            if (this.chemStack.is(otherChemStack)) {
                if (!simulate) {
                    this.chemStack.addMass(otherChemStack.m());
                    this.onContentsChanged();
                }
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
                if (!simulate) {
                    this.chemStack = otherChemStack.copyWithAmount(VariableType.VOLUME, insertableVolume);
                    this.onContentsChanged();
                }
                return otherChemStack.copyWithAmount(VariableType.VOLUME, excessVolume);
            }

            // Add chemical only if same
            if (this.chemStack.is(otherChemStack)) {
                if (!simulate) {
                    this.chemStack.addVolume(insertableVolume);
                    this.onContentsChanged();
                }
                return otherChemStack.copyWithAmount(VariableType.VOLUME, excessVolume);
            } else {
                // Return if different chemical
                return otherChemStack;
            }
        }
    }

    public ChemicalStack extractChemical(AbstractSpecies chemical, VariableType varType, float value, boolean simulate) {
        if (this.chemStack.isEmpty() || chemical == Chemicals.AIR) return ChemicalStack.EMPTY;
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
            if (!simulate) {
                this.chemStack.deductMass(requestedMass);
                this.onContentsChanged();
            }
            return this.chemStack.copyWithAmount(VariableType.MASS, requestedMass);
        } else {
            // Extract all
            ChemicalStack chemCopy = this.chemStack.copy();
            if (!simulate) {
                this.chemStack = ChemicalStack.EMPTY;
                this.onContentsChanged();
            }
            return chemCopy;
        }
    }


    protected void onLoad() {
    }

    protected void onContentsChanged() {
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return this.chemStack.save(provider);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        String chem_name = nbt.getString("chemical");

        if (chem_name.equals(this.chemStack.chemical().name)) {
            this.chemStack.setMass(nbt.getFloat("mass"));
        } else if (chem_name.equals("air")) {
            this.chemStack = ChemicalStack.EMPTY;
        } else {
            AbstractSpecies chemical = ModChemicals.CHEMICALS.getRegistry().get().getValue(
                    ResourceLocation.fromNamespaceAndPath(RimMod.MODID,
                            chem_name));
            this.chemStack = new ChemicalStack(chemical, nbt.getFloat("mass"));
        }

        onContentsChanged();
    }
}
