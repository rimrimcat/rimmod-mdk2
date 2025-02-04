package net.rimrim.rimmod.transport.heat;

import net.rimrim.rimmod.transport.ITransportHandler;
import net.rimrim.rimmod.transport.TransportHandler;
import net.rimrim.rimmod.transport.util.DirectionalCapabilityCache;

import java.util.Iterator;

public class HeatHandler {


    private final TransportHandler transHandler;

    private int nextConduct;
    private int nextConvect;
    private int nextRadiate;

    public HeatHandler(TransportHandler transHandler) {
        this.transHandler = transHandler;
    }


    public void conduct() {
        if (nextConduct == -1) return;
        else if (nextConduct > 0) {
            nextConduct -= 1;
            return;
        }

        for (Iterator<ITransportHandler> it = this.transHandler.dcache.iterateCap(); it.hasNext(); ) {

            ITransportHandler handler = it.next();
            // DO SOMETHING

        }

    }

    public void convect() {
        if (nextConvect == -1) return;
        else if (nextConvect > 0) {
            nextConvect -= 1;
            return;
        }


    }

    public void radiate() {
        if (nextRadiate == -1) return;
        else if (nextRadiate > 0) {
            nextRadiate -= 1;
            return;
        }



    }

    public void tick() {


    }






}
