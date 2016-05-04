package com.artronics.senatorino.mrf24j40.spi;

import com.artronics.senatorino.mrf24j40.registers.Registers;

import java.io.IOException;

public interface SpiIoOperation
{
    int DEFAULT_SPI_SPEED = 1000000; // 1MHz (range is 500kHz - 32MHz)

    enum spiChannel
    {
        CS0,
        CS1
    }

    void write(Registers.ControlReg address, byte data) throws IOException;

    int read(Registers.ControlReg address) throws IOException;

    void update(Registers.ControlReg address, byte flags) throws IOException;

    void update(Registers.ControlReg address, byte flags, boolean isReset) throws IOException;

}
