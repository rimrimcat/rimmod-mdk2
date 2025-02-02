package net.rimrim.rimmod.chem.enums;

public enum ValidStates {
    SOLID(0),
    LIQUID(1),
    VAPOR(2),
    SOLID_LIQUID(3),
    LIQUID_VAPOR(4),
    SOLID_VAPOR(5),
    ANY(-1)
    ;

    public final int stateIndex;

    private ValidStates(
            int stateIndex
    ) {
        this.stateIndex = stateIndex;
    }


}
