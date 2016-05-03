package com.artronics.senatorino.ieee802154.transceiver;

import com.artronics.senatorino.ieee802154.mac.reset.ResetType;

import java.io.IOException;
import java.util.EnumSet;

public interface Transceiver
{
    void reset(EnumSet<ResetType> resetType) throws IOException;
}
