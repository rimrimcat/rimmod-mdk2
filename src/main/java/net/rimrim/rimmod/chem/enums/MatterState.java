package net.rimrim.rimmod.chem.enums;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum MatterState implements StringRepresentable {
    PURE_SOLID(0, "pure_solid"),
    PURE_LIQUID(1, "pure_liquid"),
    PURE_VAPOR(2, "pure_vapor"),
//    EQUILIBRIUM_SOLID_LIQUID(3, "equilibrium_solid_liquid"),
//    EQUILIBRIUM_LIQUID_VAPOR(4, "equilibrium_liquid_vapor")
    ;

    public final int stateIndex;
    public final String state;

    private MatterState(
            int stateIndex, String state
    ) {
        this.stateIndex = stateIndex;
        this.state = state;
    }


    @Override
    public String getSerializedName() {
        return this.state;
    }
}
