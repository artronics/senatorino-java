package com.artronics.senatorino.core.register;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class RegisterJsonParserTest
{
    private RegisterJsonParser parser;
    private InputStream in;
    private String j;

    @Before
    public void setUp() throws Exception
    {
        FileInputStream f = new FileInputStream("src/test/resources/register.json");
    }

    //String to input stream
    private static InputStream json(String str)
    {
        return new ByteArrayInputStream(Charset.forName("UTF-16").encode(str).array());
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_field_has_low_case_letter() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAr\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_field_has_num_at_begining() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"1BAR\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_ends_with_num_and_asterisk() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR2*\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_address_is_range_and_address_is_not_valid() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x2:e1\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void start_address_must_be_less_than_end_address() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x2F:0X01\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void both_start_and_end_addresses_must_be_in_range_of_reg_bank() throws Exception
    {
        String s = "{\"range\":\"0x11:0x22\",\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x00:0X20\"}]}}";
        parser = new RegisterJsonParser(json(s));
        parser.parse();
    }

    @Test
    public void name() throws Exception
    {
        String s = "{\"foo\":{\"registers\":[{\"name\":\"BAR\",\"address\":\"0x2\"}]}}";

    }
}