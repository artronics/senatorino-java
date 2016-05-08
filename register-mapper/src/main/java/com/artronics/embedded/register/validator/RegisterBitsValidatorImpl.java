package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Matcher;

public class RegisterBitsValidatorImpl implements RegisterBitsValidator
{
    private final int length;

    private String name;
    private int upper;
    private int lower;

    public RegisterBitsValidatorImpl()
    {
        this.length = DEFAULT_LENGTH;
    }

    @Override
    public void validateName(String name) throws RegisterJsonParserException
    {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.matches())
            throw new RegisterJsonParserException(
                    "Parser error in \"bits\" field. bit: \"" + name + "\"");


    }

}
