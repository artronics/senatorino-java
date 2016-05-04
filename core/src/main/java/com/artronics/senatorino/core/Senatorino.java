package com.artronics.senatorino.core;

import com.artronics.senatorino.ieee802154.Ieee802154;
import com.artronics.senatorino.ieee802154.transceiver.Transceiver;

public class Senatorino
{
    private final Transceiver transceiver;
    private final Ieee802154 ieee802154;

    public Senatorino(Transceiver transceiver)
    {
        this.transceiver = transceiver;
        this.ieee802154 = new Ieee802154(transceiver);
    }


}
