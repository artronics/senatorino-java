package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterValidatorTest
{
    private RegisterValidator validator;
    private String name;
    private String address;

    @Before
    public void setUp() throws Exception
    {
        validator = new RegisterValidatorImpl();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void register_name_should_be_all_upper_case() throws Exception
    {
        name = "FoO";
        validator.validateRegisterName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void register_name_shouldnt_start_with_number() throws Exception
    {
        name = "1FOO";
        validator.validateRegisterName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void register_name_shouldnt_end_with_num_if_ends_with_asterisk() throws Exception
    {
        name = "FOO2*";
        validator.validateRegisterName(name);
    }

    @Test
    public void register_name_can_end_with_num_if_doesnt_end_with_asterisk() throws Exception
    {
        name = "FOO2";
        validator.validateRegisterName(name);
    }
    /*
    Address validator
     */

    @Test
    public void lower_bound_should_be_less_than_higher_bound() throws Exception
    {
        address = "0x12:0x34";
        assertTrue(validator.validateAddressRange(address));

        address = "0x35:0x23";
        assertFalse(validator.validateAddressRange(address));
    }

    @Test
    public void address_range_must_be_in_range_if_present() throws Exception
    {
        int min = 0x20;
        int max = 0x50;

        address = "0x25:0x35";
        assertTrue(validator.validateAddressRange(address, min, max));

        address = "0x10:0x35";
        assertFalse(validator.validateAddressRange(address, min, max));

        address = "0x25:0x70";
        assertFalse(validator.validateAddressRange(address, min, max));
    }

    @Test
    public void address_range_contains_higher_and_lower_bound() throws Exception
    {
        int min = 0x20;
        int max = 0x50;

        address = "0x20:0x50";
        assertTrue(validator.validateAddressRange(address, min, max));
    }
}