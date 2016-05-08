package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void it_should_getName() throws Exception
    {
        name = "ASD";
        assertGetBitName(name);
    }

    @Test
    public void it_should_getName_where_name_contains_nums() throws Exception
    {
        name = "A2S4D";
        assertGetBitName(name);
    }

    private void assertGetBitName(String name) throws RegisterJsonParserException
    {
        validator.validateName(name + ORDINAL);
        assertThat(validator.getBitName(), equalTo(name));

        validator.validateName(name + RANGE);
        assertThat(validator.getBitName(), equalTo(name));

        validator.validateName(name + MASK);
        assertThat(validator.getBitName(), equalTo(name));
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
        name = "LSD(12:234)";
        validator.validateName(name);
    }

    /* Mask Match */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_mask_is_not_hex() throws Exception
    {
        name = "LSD<234>";
        validator.validateName(name);
    }

    /* Range Match */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_range_is_hex() throws Exception
    {
        name = "LSD<0x2:0x2a>";
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