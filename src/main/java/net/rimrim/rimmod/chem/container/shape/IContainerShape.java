package net.rimrim.rimmod.chem.container.shape;

import net.minecraft.core.Direction;

public interface IContainerShape {

    float inner_volume();

    float outer_volume();

    float inner_contact_surface_area(float fluidVolume);

    float face_surface_area(Direction dir);

}
