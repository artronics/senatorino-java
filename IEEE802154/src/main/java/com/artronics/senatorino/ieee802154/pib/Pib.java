package com.artronics.senatorino.ieee802154.pib;

import com.artronics.senatorino.ieee802154.exceptions.PibValidationException;

public class Pib<T>
{
    private final Name name;
    private T value;
    private final Layer layer;
    private final boolean readOnly;
    private String description = "No Description";

    private final PibValidator validator;

    public Pib(Name name, T value, Layer layer, boolean readOnly, PibValidator validator)
    {
        this.name = name;
        this.value = value;
        this.layer = layer;
        this.readOnly = readOnly;
        this.validator = validator;
    }

    public void setValue(T value) throws PibValidationException
    {
        if (validator != null) {
            validator.validate(this);
        }
        this.value = value;
    }

    public Name getName()
    {
        return name;
    }

    public T getValue()
    {
        return value;
    }

    public Layer getLayer()
    {
        return layer;
    }

    public boolean isReadOnly()
    {
        return readOnly;
    }

    public String getDescription()
    {
        return description;
    }

    public enum Name
    {
        macExtendedAddress
    }

    public enum Layer
    {
        MAC,
        PHY
    }
}
