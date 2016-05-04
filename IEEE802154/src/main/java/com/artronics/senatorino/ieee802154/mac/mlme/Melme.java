package com.artronics.senatorino.ieee802154.mac.mlme;

import com.artronics.senatorino.ieee802154.mac.scan.ScanOperation;
import com.artronics.senatorino.ieee802154.mac.scan.ScanOperationImpl;
import com.artronics.senatorino.ieee802154.mac.scan.ScanPrimitive;

public class Melme
{
    public static class Scan
    {
        private static final ScanOperation scanOperation = new ScanOperationImpl();

        public static void request(ScanPrimitive primitive)
        {
            scanOperation.request(primitive);
        }
    }

    public Scan scan()
    {
        return new Scan();
    }
}
