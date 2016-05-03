package com.artronics.senatorino.mrf24j40.spi;

import com.artronics.senatorino.mrf24j40.registers.Registers;
import com.pi4j.io.spi.SpiDevice;

import java.io.IOException;

public class Mrf24j40SpiIoOperation implements SpiIoOperation
{
    private SpiDevice spi;

    public Mrf24j40SpiIoOperation(SpiDevice spi)
    {
        this.spi = spi;
    }

    @Override
    public void write(Registers.ControlReg address, byte data) throws IOException
    {
        byte d[] = new byte[2];
        byte add = address.writeAddress();
        d[0] = add;
        d[1] = data;
        spi.write(d);
    }

    @Override
    public int read(Registers.ControlReg address) throws IOException
    {
        byte d[] = new byte[2];
        byte add = address.readAddress();
        d[0] = add;
        d[1] = 0x00;

        byte[] write = spi.write(d);
        return write[1];
    }

    @Override
    public void update(Registers.ControlReg address, byte flags) throws IOException
    {
        byte readValue = (byte) read(address);
        readValue = (byte) (readValue | flags);
        write(address, readValue);
    }

    @Override
    public void update(Registers.ControlReg address, byte flags, boolean isReset) throws IOException
    {
        if (isReset) {
            byte readValue = (byte) read(address);
            readValue = (byte) (readValue & (~flags));
            write(address, readValue);

        }else
            update(address, flags);

    }
}
