package com.artronics.senatorino.ieee802154.pib;

import com.artronics.senatorino.ieee802154.exceptions.PibValidationException;

interface PibValidator<T>
{
    void validate(Pib<T> pib) throws PibValidationException;
}
