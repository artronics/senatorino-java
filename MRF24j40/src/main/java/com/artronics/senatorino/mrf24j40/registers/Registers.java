package com.artronics.senatorino.mrf24j40.registers;

import static com.artronics.senatorino.mrf24j40.registers.AddressHelper.createShortReadAddr;
import static com.artronics.senatorino.mrf24j40.registers.AddressHelper.createShortWriteAddr;

public class Registers
{
    public enum ControlReg
    {
        ACKTMOUT(0x12),
        SOFTRST(0x2A),
        RFCTL(0x36);
        private int address;

        ControlReg(int address)
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
}
