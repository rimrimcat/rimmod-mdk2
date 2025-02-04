package net.rimrim.rimmod.chem.container.shape;

import net.minecraft.core.Direction;

public class Cubic implements IContainerShape {
    private final float inner_length;
    private final float inner_volume;
    private final float outer_volume;
    private final float thickness;
    // TODO: DEFORMATIONS

    public Cubic(double inner_volume) {
        this((float) Math.cbrt(inner_volume), 2 / 16f);
    }

    public Cubic(float inner_volume) {
        this((float) Math.cbrt(inner_volume), 2 / 16f);
    }

    public Cubic(float inner_length, float thickness) {
        if (inner_length == 0) {
            thickness = 0.5f;
        }

        this.thickness = thickness;

        float outer_length = inner_length + 2 * thickness;
        this.outer_volume = outer_length * outer_length * outer_length;

        this.inner_length = inner_length;
        this.inner_volume = inner_length * inner_length * inner_length;
    }

    public float thickness() {
        return this.thickness;
    }

    @Override
    public float fluid_height(float fluidVolume) {
        if (inner_length == 0) return 0;
        return fluidVolume / inner_length / inner_length;
    }

    @Override
    public float inner_volume() {
        return this.inner_volume;
    }


    @Override
    public float outer_volume() {
        return this.outer_volume;
    }

    @Override
    public float inner_contact_surface_area(float fluidVolume) {
        if (fluidVolume == 0) return 0;

        float base = inner_length * inner_length;
        float side = fluid_height(fluidVolume) * inner_length + base;

        return (fluidVolume == this.inner_volume) ? (4 * side + 2 * base) : (4 * side + base);
    }

    @Override
    public float outer_face_surface_area(Direction dir) {
        return (inner_length + 2 * thickness) * (inner_length + 2 * thickness);
    }


}