package com.artronics.senatorino.ieee802154.transceiver;

import com.artronics.senatorino.ieee802154.mac.reset.ResetType;

public interface Transceiver
{
    void reset(ResetType resetType);
}
