package net.rimrim.rimmod.transport.heat;

import net.rimrim.rimmod.chem.stack.ChemicalStackHandler;
import net.rimrim.rimmod.transport.ITransportHandler;
import net.rimrim.rimmod.transport.TransportHandler;

import java.util.Iterator;

public class HeatHandler {


    private final TransportHandler transHandler;

    private int nextConduct;
    private int nextConvect;
    private int nextRadiate;

    public HeatHandler(TransportHandler transHandler) {
        this.transHandler = transHandler;
    }

    public TransportHandler transHandler() {
        return this.transHandler;
    }

    /**
     * Conducts heat to chemical inside container.
     *
     */
    public void conductHeatToChemical() {

    }

    /**
     * Conducts heat to other blocks
     *
     * @param otherHandler: HeatHandler of the other block
     */
    public void conductHeatToNeighbor(HeatHandler otherHandler) {
        // Conduction should strictly only from high temp to low temp

        // float T_A = this.transHandler().T();
        // float T_B = otherHandler.transHandler.T();
        // if (T_B > T_A) {
        //     otherHandler.conductHeatTo(this);
        //     return;
        // }
        //
        // float thermal_res = 0.5f / this.thermal_conductivity() + 0.5f / otherHandler.thermal_conductivity();
        // float flux = -(T_B - T_A) / thermal_res;
        //
        // float mC_A = this.mass() * this.heat_capacity();
        // float mC_B = otherHandler.mass() * otherHandler.heat_capacity();
        //
        // this.updateTemperature(-flux * 0.1f / mC_A + T_A);
        // otherHandler.updateTemperature(flux * 0.1f / mC_B + T_B);
    }

    public void tryConduct() {
        if (nextConduct == -1) return;
        else if (nextConduct > 0) {
            nextConduct -= 1;
            return;
        }

        for (Iterator<TransportHandler> it = this.transHandler.dcache.iterateCap(); it.hasNext(); ) {

            TransportHandler handler = it.next();
            // DO SOMETHING

        }

    }

    public void tryConvect() {
        if (nextConvect == -1) return;
        else if (nextConvect > 0) {
            nextConvect -= 1;
            return;
        }


    }

    public void tryRadiate() {
        if (nextRadiate == -1) return;
        else if (nextRadiate > 0) {
            nextRadiate -= 1;
            return;
        }


    }

    public void tick() {


    }


}
