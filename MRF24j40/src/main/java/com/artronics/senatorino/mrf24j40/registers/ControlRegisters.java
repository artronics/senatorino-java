package com.artronics.senatorino.mrf24j40.registers;

import static com.artronics.senatorino.mrf24j40.registers.AddressHelper.createShortReadAddr;
import static com.artronics.senatorino.mrf24j40.registers.AddressHelper.createShortWriteAddr;

public enum ControlRegisters
{

    AKCTMOUT(0x12);

    private int address;

    ControlRegisters(int address)
    {
        this.address = address;
    }

    public byte readAddress()
    {
        return createShortReadAddr(address);
    }

    public byte writeAddress()
    {
        return createShortWriteAddr(address);
    }
}
