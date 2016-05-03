package com.artronics.senatorino.mrf24j40.registers;

import org.junit.Test;

import static com.artronics.senatorino.mrf24j40.registers.AddressHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class AddressHelperTest
{
    @Test
    public void createShortReadAddr_test() throws Exception
    {
        int value = 0x12;
        byte exp = (byte) 0x24;

        byte act = createShortReadAddr(value);
        assertThat(act, equalTo(exp));
    }

    @Test
    public void createShortWriteAddr_test() throws Exception
    {
        int value = 0x12;
        byte exp = (byte) 0x25;

        byte act = createShortWriteAddr(value);
        assertThat(act, equalTo(exp));
    }

    @Test
    public void createLongReadAddr_test() throws Exception
    {
        /*
            MSB must be 1 (because of long address)
            Last 4 bits is don't care (it is always zero)
            Last bit (except dont cares) must be 0 because of write mode
         */
        int value = 0x12;
        byte exp[] = new byte[2];
        exp[0] = (byte) 0x82;
        exp[1] = (byte) 0x40;

        byte[] act = createLongReadAddr(value);

        System.out.println(Integer.toHexString(act[0] & 0xff) + " " + act[1]);
        assertThat(act, equalTo(exp));
    }

    @Test
    public void createLongWriteAddr_test() throws Exception
    {
        /*
            MSB must be 1 (because of long address)
            Last 4 bits is don't care (it is always zero)
            Last bit (except dont cares) must be 1 because of write mode
         */
        int value = 0xFF000F0F;
        short exp = (short) 0x8F10;

        short act = createLongWriteAddr(value);

        assertThat(act, equalTo(exp));
    }


}