package com.artronics.senatorino.ieee802154.mac.mlme;

import com.artronics.senatorino.ieee802154.mac.scan.ScanConf;
import com.artronics.senatorino.ieee802154.mac.scan.ScanOperationImpl;
import com.artronics.senatorino.ieee802154.mac.scan.ScanReq;

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

}
