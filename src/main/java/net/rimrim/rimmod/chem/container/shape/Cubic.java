package net.rimrim.rimmod.chem.container.shape;

import net.minecraft.core.Direction;

public class Cubic implements IContainerShape {
    public final float inner_length;
    public final float inner_volume;
    public final float outer_volume;
    public final float thickness;
    // TODO: DEFORMATIONS

    public Cubic(double inner_volume) {
        this((float) Math.cbrt(inner_volume), 2 / 16f);
    }

    public Cubic(float inner_volume) {
        this((float) Math.cbrt(inner_volume), 2 / 16f);
    }

    public Cubic(float inner_length, float thickness) {
        this.thickness = thickness;

        float outer_length = inner_length + thickness;
        this.outer_volume = outer_length * outer_length * outer_length;

        this.inner_length = inner_length;
        this.inner_volume = inner_length * inner_length * inner_length;
    }


    private float fluidHeight(float fluidVolume) {
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
        float side = fluidHeight(fluidVolume) * inner_length + base;

        return (fluidVolume == this.inner_volume) ? (4 * side + 2 * base) : (4 * side + base);
    }

    @Override
    public float face_surface_area(Direction dir) {
        return (inner_length + thickness) * (inner_length + thickness);
    }


}