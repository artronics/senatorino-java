package com.artronics.senatorino.ieee802154;

import com.artronics.senatorino.ieee802154.mac.mlme.Melme;
import com.artronics.senatorino.ieee802154.mac.scan.ScanReq;
import com.artronics.senatorino.ieee802154.transceiver.Transceiver;

public class Ieee802154
{
    private final Transceiver transceiver;

    public Ieee802154(Transceiver transceiver)
    {
        this.transceiver = transceiver;
    }

    public static void main(String[] args)
    {
        Melme melme = new Melme();
        Melme.Scan.request(new ScanReq.Builder(ScanReq.Type.ED).build());

        System.out.println("MAC");
    }
}
