package com.artronics.senatorino.mrf24j40.spi;

import com.pi4j.io.spi.SpiDevice;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MRF24J40Test
{

    private MRF24J40 mrf24J40;

    @Mock
    private SpiDevice spi;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        mrf24J40 = new MRF24J40(spi);
    }

    @Test
    public void first_bit_must_be_1_in_write_mode() throws Exception
    {

    }


}