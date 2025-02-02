package net.rimrim.rimmod.chem.props;

import net.rimrim.rimmod.chem.enums.MatterState;

public record ChemTags(boolean isWater,
                       boolean isOrganic,
                       boolean isInorganic,
                       MatterState[] validStates
) {
    public static ChemTags NONE = new Builder().build();
    public static ChemTags WATER = new Builder().water().inorganic().build();

    // TODO: isSolvent

    public boolean isWater() {
        return this.isWater;
    }

    public boolean isOrganic() {
        return this.isOrganic;
    }

    public boolean isInorganic() {
        return this.isInorganic;
    }

    public MatterState[] validStates() {
        return this.validStates;
    }

    public boolean isSolidOnly() {
        return validStates.length == 1 && validStates[0] == MatterState.SOLID;
    }

    public boolean isLiquidOnly() {
        return validStates.length == 1 && validStates[0] == MatterState.LIQUID;
    }

    public boolean isVaporOnly() {
        return validStates.length == 1 && validStates[0] == MatterState.VAPOR;
    }

    public Builder toBuilder() {
        Builder newbuilder = new Builder();

        newbuilder.isWater = this.isWater;
        newbuilder.isOrganic = this.isOrganic;
        newbuilder.isInorganic = this.isInorganic;
        newbuilder.validStates = this.validStates;

        return newbuilder;
    }

    public static class Builder {
        private boolean isWater = false;
        private boolean isOrganic = false;
        private boolean isInorganic = false;

        private MatterState[] validStates = new MatterState[]{MatterState.SOLID};

        public Builder water() {
            this.isWater = true;
            return this;
        }

        public Builder organic() {
            this.isOrganic = true;
            return this;
        }

        public Builder inorganic() {
            this.isInorganic = true;
            return this;
        }

        public Builder solidOnly() {
            this.validStates = new MatterState[]{MatterState.SOLID};
            return this;
        }

        public Builder liquidOnly() {
            this.validStates = new MatterState[]{MatterState.LIQUID};
            return this;
        }

        public Builder vaporOnly() {
            this.validStates = new MatterState[]{MatterState.VAPOR};
            return this;
        }

        public ChemTags build() {
            return new ChemTags(this.isWater, this.isOrganic, this.isInorganic, this.validStates);
        }

    }

}