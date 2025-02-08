package net.rimrim.rimmod.transport.heat;


import net.minecraft.core.Direction;
import net.rimrim.rimmod.transport.TransportHandler;

public interface IHeatHandler {

    /**
     * The transport handler that this heat handler is associated with.
     * This is used for accessing the chemical stack handler of this block.
     *
     * @return the transport handler associated with this heat handler
     */
    TransportHandler transHandler();


    /**
     * Conducts heat to chemical inside container.
     * Uses simple series conduction through flat slab
     */
    void conductHeatToChemical();


    /**
     * Conducts heat to a neighbor block's handler, given by the direction.
     * Uses simple series conduction through flat slab
     *
     * @param otherHandler the handler of the neighbor block
     * @param dir          the direction of the neighbor block
     */
    void conductHeatToNeighbor(RegularHeatHandler otherHandler, Direction dir);

    void tryConduct();

    void tryConvect();

    void tryRadiate();


    /**
     * Calls other handler-specific logic
     */
    void tick();


}
