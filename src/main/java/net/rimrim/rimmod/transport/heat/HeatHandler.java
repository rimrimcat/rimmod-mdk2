package net.rimrim.rimmod.transport.heat;

import net.minecraft.core.Direction;
import net.rimrim.rimmod.chem.container.ChemContainer;
import net.rimrim.rimmod.chem.enums.ProcessVariableType;
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
     * Uses simple series conduction through flat slab
     */
    public void conductHeatToChemical() {
        if (this.transHandler.chemHandler() == null) return;
        if (this.transHandler.chemHandler().chemStack().isEmpty()) return;

        ChemicalStackHandler chemHandler = this.transHandler.chemHandler();
        ChemContainer container = chemHandler.container();

        float T_A = chemHandler.T();
        float T_B = container.T();

        float x_A = container.shape().fluid_height(chemHandler.V());
        float x_B = container.shape().thickness();

        float transfer_area = container.shape().inner_contact_surface_area(
                chemHandler.V()
        );

        float thermal_res = (x_A / container.k() + x_B / chemHandler.k()) / transfer_area;

        float flux = -(T_B - T_A) / thermal_res;

        float mC_A = chemHandler.m() * chemHandler.cp();
        float mC_B = container.m() * container.cp();

        chemHandler.chemStack().mapIncrement(ProcessVariableType.TEMPERATURE, -flux * 0.1f / mC_A);
        container.mapIncrement(ProcessVariableType.TEMPERATURE, flux * 0.1f / mC_B);
    }

    /**
     * Conducts heat to other blocks
     * Uses simple series conduction through flat slab
     *
     * @param otherHandler: HeatHandler of the other block
     */
    public void conductHeatToNeighbor(HeatHandler otherHandler, Direction dir) {
        // Conduction to neighbor should strictly only be from high temp to low temp
        ChemContainer thisContainer = this.transHandler.chemHandler().container();
        ChemContainer otherContainer = otherHandler.transHandler.chemHandler().container();

        float T_A = thisContainer.T();
        float T_B = otherContainer.T();
        if (T_B > T_A) {
            return;
        }

        float transfer_area = Math.min(thisContainer.shape().outer_face_surface_area(dir), otherContainer.shape().outer_face_surface_area(dir.getOpposite()));
        float thermal_res = (0.5f / thisContainer.k() + 0.5f / otherContainer.k()) / transfer_area;

        float flux = -(T_B - T_A) / thermal_res;

        float mC_A = thisContainer.m() * thisContainer.cp();
        float mC_B = otherContainer.m() * otherContainer.cp();

        thisContainer.mapIncrement(ProcessVariableType.TEMPERATURE, -flux * 0.1f / mC_A);
        otherContainer.mapIncrement(ProcessVariableType.TEMPERATURE, flux * 0.1f / mC_B);
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
