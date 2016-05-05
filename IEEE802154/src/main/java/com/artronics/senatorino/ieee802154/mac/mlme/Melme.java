package com.artronics.senatorino.ieee802154.mac.mlme;

import com.artronics.senatorino.ieee802154.mac.scan.ScanConf;
import com.artronics.senatorino.ieee802154.mac.scan.ScanOperationImpl;
import com.artronics.senatorino.ieee802154.mac.scan.ScanReq;
import com.artronics.senatorino.ieee802154.pib.PibOperation;
import com.artronics.senatorino.ieee802154.pib.PibOperationImpl;
import com.artronics.senatorino.ieee802154.sap.Request;

public class Melme
{
    public static class Scan
    {
        private static final Request<ScanReq, ScanConf> scanOperation = new ScanOperationImpl();

        public static ScanConf request(ScanReq scanReq)
        {
            return scanOperation.request(scanReq);
        }
    }

    public static class Pib
    {
        private static final PibOperation pibOperation = new PibOperationImpl();

        public static void set(com.artronics.senatorino.ieee802154.pib.Pib.Name name, Object value)
        {
            pibOperation.set(name, value);
        }
    }

}
