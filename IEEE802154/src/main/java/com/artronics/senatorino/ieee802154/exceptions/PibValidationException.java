package com.artronics.senatorino.ieee802154.exceptions;

import com.artronics.senatorino.ieee802154.pib.Pib;

public class PibValidationException extends Exception
{
    private final Pib pib;

    public PibValidationException(Pib pib)
    {
        this.pib = pib;
    }
}
