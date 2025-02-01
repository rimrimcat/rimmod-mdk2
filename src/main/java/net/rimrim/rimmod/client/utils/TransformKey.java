package net.rimrim.rimmod.client.utils;

import net.rimrim.rimmod.block.properties.InserterState;

import java.util.Objects;

public class TransformKey {
    private final String state;
    private final String modelPart;

    public TransformKey(String state, String modelPart) {
        this.state = state;
        this.modelPart = modelPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransformKey key = (TransformKey) o;
        return Objects.equals(state, key.state) && Objects.equals(modelPart, key.modelPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, modelPart);
    }
}
