package com.artronics.senatorino.ieee802154.mac.scan;

import com.artronics.senatorino.ieee802154.mac.mlme.Request;
import com.artronics.senatorino.ieee802154.transceiver.TrScan;

public class ScanOperationImpl implements Request<ScanReq, ScanConf>
{
    private TrScan trScan;

    @Override
    public ScanConf request(ScanReq scanRequest)
    {
        return trScan.scan(scanRequest);
    }
}
