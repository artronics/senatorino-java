package com.artronics.senatorino.ieee802154.sap;

import com.artronics.senatorino.ieee802154.pib.Pib;

public interface Set
{
    void set(Pib.Name name, Object value);
}
