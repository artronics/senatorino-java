package com.artronics.senatorino.mrf24j40;

import com.artronics.senatorino.mrf24j40.spi.Mrf24j40SpiIoOperation;
import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;

import java.io.IOException;

public class App
{
    // SpiIoOperation device
    public static SpiDevice spi = null;

    // SpiIoOperation operations
    public static byte INIT_CMD = (byte) 0xD0;

    public static void main(String[] args) throws IOException, InterruptedException
    {
        System.out.println("Hello World!");

        System.out.println("<--Pi4J--> SPI test program using MCP3002 AtoD Chip");

        // create Spi object instance for SpiIoOperation for communication
        spi = SpiFactory.getInstance(SpiChannel.CS0,
                                     SpiDevice.DEFAULT_SPI_SPEED, // default spi speed 1 MHz
                                     SpiDevice.DEFAULT_SPI_MODE); // default spi mode 0

        Thread.sleep(1000);
        read();

    }

    public static void read() throws IOException
    {
        // send test ASCII message
        byte packet[] = new byte[2];
        packet[0] = (0x0 << 7) | (0x12 << 1) | 0x0;  // address byte
        packet[1] = 0x00;  // dummy

        System.out.println("-----------------------------------------------");
        System.out.println("[TX] " + bytesToHex(packet));
        byte[] result = spi.write(packet);
        System.out.println("[RX] " + bytesToHex(result));
        System.out.println("-----------------------------------------------");
        System.out.println(((result[0] << 8) | result[1]) & 0x3FF);

//        int data = mrf24J40.read(Registers.ControlReg.AKCTMOUT);
//        System.out.println(Integer.toHexString(data));
        System.out.println("write 50");
//        mrf24J40.write(Mrf24j40SpiIoOperation.ControlRegisters.AKCTMOUT, (byte) 50);

//        data = mrf24J40.read(Mrf24j40SpiIoOperation.ControlRegisters.AKCTMOUT);
//        System.out.println(Integer.toHexString(data));
    }

    public static String bytesToBinary(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j];
            sb.append(Integer.toBinaryString(v));
        }
        return sb.toString();
    }

    public static String bytesToHex(byte... bytes)
    {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
