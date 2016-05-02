package com.artronics.senatorino.mrf24j40.registers;

public class AddressHelper
{
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
