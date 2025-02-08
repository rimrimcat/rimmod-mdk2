package net.rimrim.rimmod.transport.heat;

import net.rimrim.rimmod.transport.TransportHandler;

public class WeakHeatHandler extends RegularHeatHandler implements IHeatHandler {
    public WeakHeatHandler(TransportHandler transHandler) {
        super(transHandler);
    }

    // TODO: ADD RATE LIMITING
}
