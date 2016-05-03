package com.artronics.senatorino.mrf24j40.registers;

public class RegisterBits
{
    // SOFTRST 0x2A
    public final static byte RSTMAC = 1 << 0;
    public final static byte RSTBB = 1 << 1;
    public final static byte RSTPWR = 1 << 2;

    //RFCTL 0x36
    public final static byte RFRST = 1 << 2;
}
