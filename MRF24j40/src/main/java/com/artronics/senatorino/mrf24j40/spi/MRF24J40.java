package com.artronics.senatorino.mrf24j40.spi;

import com.pi4j.io.spi.SpiDevice;

import java.io.IOException;

public class MRF24J40
{
    private SpiDevice spi;

    public MRF24J40(SpiDevice spi)
    {
        this.spi = spi;
    }

    public void write(ControlRegisters address, byte data) throws IOException
    {
        byte d[] = new byte[2];
        byte add = address.writeAddress();
        d[0] = add;
        d[1] = data;
        spi.write(d);
    }

    public int read(ControlRegisters address) throws IOException
    {
        byte d[] = new byte[2];
        byte add = address.readAddress();
        d[0] = add;
        d[1] = 0x00;

        byte[] write = spi.write(d);
        return write[1];
    }


    public enum ControlRegisters
    {
        /*
            Control Registeres
         */
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

    public static byte createShortReadAddr(int address)
    {
        return (byte) (
                0x7E & (address << 1)
        );
    }

    public static byte createShortWriteAddr(int address)
    {
        return (byte) (
                (0x7F & (address << 1)) | 0x01
        );
    }

    public static byte[] createLongReadAddr(int address)
    {
        byte msb = 0;
        byte lsb = 0;
        /* For Read Mode last bit must be 0 + 4 bits of don't care */
        /* For Long Address first bit must be 1 */
        msb = (byte) (
                (((address & 0xFFFF) >> 3) & 0x7F) | 0x80
        );
        lsb = (byte) (
                ((address & 0xFFFF) & 0x07) << 5
        );

        byte b[] = new byte[2];
        b[0] = msb;
        b[1] = lsb;

        return b;
    }

    public static short createLongWriteAddr(int address)
    {
        short addr = (short) (address & 0xFFF0);
        /* For Long Address first bit must be 1 */
        addr = (short) (addr | 0x8000);
        /* For Read Mode last bit must be 0 + 4 bits of don't care */
        addr = (short) (addr | 0x0010);

        return addr;
    }
}
