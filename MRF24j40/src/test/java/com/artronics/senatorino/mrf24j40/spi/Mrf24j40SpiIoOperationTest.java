package com.artronics.senatorino.mrf24j40.spi;

import com.artronics.senatorino.mrf24j40.registers.Registers;
import com.pi4j.io.spi.SpiDevice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Mrf24j40SpiIoOperationTest
{
    private SpiIoOperation mrf;

    @Mock
    private SpiDevice spi;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        mrf = new Mrf24j40SpiIoOperation(spi);
    }

    @Test
    public void name() throws Exception
    {
        ArgumentCaptor<Byte> argCap = ArgumentCaptor.forClass(Byte.class);
        mrf.write(Registers.ControlReg.SOFTRST, (byte) 34);
        verify(spi).write(argCap.capture());

        System.out.println(argCap.getValue());
    }

    @Test
    public void update_method_should_set_flags() throws Exception
    {
        ArgumentCaptor<Byte> argCap = ArgumentCaptor.forClass(Byte.class);

        byte[] wrtReturnVal = new byte[2];
        //first byte doesn't matter
        wrtReturnVal[0] = Registers.ControlReg.ACKTMOUT.readAddress();
        wrtReturnVal[1] = 0x11;
        when(spi.write((byte[]) anyVararg())).thenReturn(wrtReturnVal);

        // 0x11 | 0x88 = 0x99
        mrf.update(Registers.ControlReg.ACKTMOUT, (byte) 0x88);
        verify(spi, times(2)).write(argCap.capture());

        List<Byte> allValues = argCap.getAllValues();

        Integer exp = 0x99;
        assertThat(allValues.get(allValues.size() - 1), equalTo(exp.byteValue()));
    }

    @Test
    public void update_method_should_reset_flags() throws Exception
    {
        ArgumentCaptor<Byte> argCap = ArgumentCaptor.forClass(Byte.class);

        byte[] wrtReturnVal = new byte[2];
        //first byte doesn't matter
        wrtReturnVal[0] = Registers.ControlReg.ACKTMOUT.readAddress();
        wrtReturnVal[1] = 0x33;
        when(spi.write((byte[]) anyVararg())).thenReturn(wrtReturnVal);

        // 0x33 & ~0x01 = 0x32
        mrf.update(Registers.ControlReg.ACKTMOUT, (byte) 0x01, true);
        verify(spi, times(2)).write(argCap.capture());

        List<Byte> allValues = argCap.getAllValues();

        Integer exp = 0x32;
        assertThat(allValues.get(allValues.size() - 1), equalTo(exp.byteValue()));
    }
}