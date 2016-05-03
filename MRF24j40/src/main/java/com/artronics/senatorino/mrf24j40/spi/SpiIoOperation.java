package com.artronics.senatorino.mrf24j40.spi;

import com.artronics.senatorino.mrf24j40.registers.Registers;

import java.io.IOException;

public interface SpiIoOperation
{
    void write(Registers.ControlReg address, byte data) throws IOException;

    int read(Registers.ControlReg address) throws IOException;

    void update(Registers.ControlReg address, byte flags) throws IOException;

    void update(Registers.ControlReg address, byte flags, boolean isReset) throws IOException;

}
