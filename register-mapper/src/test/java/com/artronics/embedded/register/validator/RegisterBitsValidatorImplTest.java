package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;
import org.junit.Before;
import org.junit.Test;

public class RegisterBitsValidatorImplTest
{
    private static final String ORDINAL = "(1)";
    private static final String MASK = "<0x0F>";
    private static final String RANGE = "<2:4>";

    private RegisterBitsValidatorImpl validator;
    private String name;

    @Before
    public void setUp() throws Exception
    {
        validator = new RegisterBitsValidatorImpl();
    }

    @Test
    public void name() throws Exception
    {
        System.out.println(RegisterBitsValidator.BIT_NAME_PATTERN);
    }


    /* Name Match */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_contains_lower_case_letter() throws Exception
    {
        name = "1LsD";
        validator.validateName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_starts_with_num() throws Exception
    {
        name = "1LSD";
        validator.validateName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_ends_with_num() throws Exception
    {
        name = "LSD1";
        validator.validateName(name);
    }

    /* Ordinal Match*/
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_ordinal_is_a_range() throws Exception
    {
        name = "LSD(1:4)";
        validator.validateName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_ordinal_is_a_hex() throws Exception
    {
        name = "LSD(0x2)";
        validator.validateName(name);
    }

    /* Mask Match */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_mask_is_not_hex() throws Exception
    {
        name = "LSD<4>";
        validator.validateName(name);
    }

    /* Range Match */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_range_is_hex() throws Exception
    {
        name = "LSD<0x2:0x4>";
        validator.validateName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_range_is_hex2() throws Exception
    {
        name = "LSD<1:0x2a>";
        validator.validateName(name);
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_range_is_hex3() throws Exception
    {
        name = "LSD<0x2:5>";
        validator.validateName(name);
    }
}