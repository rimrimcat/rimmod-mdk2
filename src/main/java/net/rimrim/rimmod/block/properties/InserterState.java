package net.rimrim.rimmod.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum InserterState implements StringRepresentable {
    WAIT_SOURCE(0, Direction.AxisDirection.NEGATIVE, "wait_source", 0), // wait for item
    TAKING(1, Direction.AxisDirection.NEGATIVE, "taking", 20),
    TRANSFERRING(2, Direction.AxisDirection.NEGATIVE, "transferring", 40), //
    WAIT_DESTINATION(3, Direction.AxisDirection.POSITIVE, "wait_destination", 0),
    INSERTING(4, Direction.AxisDirection.POSITIVE, "inserting", 20),
    RETURNING(5, Direction.AxisDirection.POSITIVE, "returning", 40); //

    public final int stateIndex;
    public final String name;
    public final Direction.AxisDirection direction;
    public final int maxProgress;

    private InserterState(
            int stateIndex, Direction.AxisDirection direction, String name, int maxProgress
    ) {
        this.stateIndex = stateIndex;
        this.direction = direction;
        this.name = name;
        this.maxProgress = maxProgress;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public boolean equals(InserterState other) {
        return this.stateIndex == other.stateIndex;
    }
}
