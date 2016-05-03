package com.artronics.senatorino.ieee802154.mac.reset;

import java.io.IOException;
import java.util.EnumSet;

public interface Reset
{
    void reset(EnumSet<ResetType> resetType) throws IOException;

    enum ResetType
    {
        MAC,
        POWER_MANAGEMENT,
        BASE_BAND,
        RF,
    }

}
