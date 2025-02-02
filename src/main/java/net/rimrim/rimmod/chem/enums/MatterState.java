package net.rimrim.rimmod.chem.enums;

public enum MatterState {
    SOLID(0, "solid"),
    LIQUID(1, "liquid"),
    VAPOR(2, "vapor");

    public final int stateIndex;
    public final String name;

    private MatterState(
            int stateIndex, String name
    ) {
        this.stateIndex = stateIndex;
        this.name = name;
    }
}
