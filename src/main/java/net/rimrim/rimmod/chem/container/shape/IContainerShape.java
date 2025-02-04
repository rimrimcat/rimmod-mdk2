package net.rimrim.rimmod.chem.container.shape;

import net.minecraft.core.Direction;

public interface IContainerShape {

    float thickness();

    float inner_volume();

    float outer_volume();

    float fluid_height(float fluidVolume);

    float inner_contact_surface_area(float fluidVolume);

    float outer_face_surface_area(Direction dir);

}
