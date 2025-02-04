package net.rimrim.rimmod.chem.container;

public interface IContainerShape {

    float volume();

    float inner_contact_surface_area(float fluidVolume);

    float ambient_surface_area();


}
