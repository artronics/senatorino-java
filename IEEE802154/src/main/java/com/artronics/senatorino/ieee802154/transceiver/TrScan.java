package com.artronics.senatorino.ieee802154.transceiver;

import com.artronics.senatorino.ieee802154.mac.scan.ScanConf;
import com.artronics.senatorino.ieee802154.mac.scan.ScanReq;

public interface TrScan
{
    ScanConf scan(ScanReq scanReq);
}
