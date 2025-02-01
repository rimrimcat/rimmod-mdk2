package net.rimrim.rimmod.chem.props;

public record ChemTags(boolean isWater, boolean isOrganic, boolean isInorganic) {
    public static ChemTags WATER = new Builder().water().inorganic().build();

    public boolean isWater() {
        return this.isWater;
    }

    public boolean isOrganic() {
        return this.isOrganic;
    }

    public boolean isInorganic() {
        return this.isInorganic;
    }

    public static class Builder {
        private boolean isWater = false;
        private boolean isOrganic = false;
        private boolean isInorganic = false;

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

        public ChemTags build() {
            return new ChemTags(this.isWater, this.isOrganic, this.isInorganic);
        }


    }

}
