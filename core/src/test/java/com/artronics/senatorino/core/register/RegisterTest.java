package com.artronics.senatorino.core.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RegisterTest
{
    private ObjectMapper om = new ObjectMapper();

    @Test
    public void it_should_parse_hex_string() throws Exception
    {
        String s = "{\"address\":\"0x12\"}";
        Register r = om.readValue(s, Register.class);

        assertThat(r.getAddress(), equalTo(0x12));
    }

}