package com.artronics.senatorino.ieee802154;

import com.artronics.senatorino.ieee802154.mac.mlme.Melme;
import com.artronics.senatorino.ieee802154.mac.scan.ScanReq;

public class App
{
    public static void main(String[] args)
    {
        Melme melme = new Melme();
        Melme.Scan.request(new ScanReq.Builder(ScanReq.Type.ED).build());

        System.out.println("MAC");
    }
}
