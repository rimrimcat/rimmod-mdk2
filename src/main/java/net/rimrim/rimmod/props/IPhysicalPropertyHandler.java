package net.rimrim.rimmod.props;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.rimrim.rimmod.props.base.IBasicProperties;
import net.rimrim.rimmod.props.base.ITempDependentProperties;

public interface IPhysicalPropertyHandler extends IBasicProperties, ITempDependentProperties {

    // Mechanisms here
    void updateTemperature(float newTemperature);

    void updateConductibles(Level level, BlockPos pos);

    void conductAll();

    void conduct(IPhysicalPropertyHandler otherHandler);

}
