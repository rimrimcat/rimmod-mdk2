package net.rimrim.rimmod.props.base;

import net.minecraft.core.Direction;

public interface IBasicProperties {
    float energy();

    float temperature();

    float pressure();

    float volume();

    float mass();

    float area(Direction side);
}
