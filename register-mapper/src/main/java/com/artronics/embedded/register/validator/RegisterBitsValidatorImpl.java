package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Matcher;

public class RegisterBitsValidatorImpl implements RegisterBitsValidator
{

    public RegisterBitsValidatorImpl()
    {
    }

    @Override
    public Matcher validateName(String name) throws RegisterJsonParserException
    {
        Matcher matcher = BIT_NAME_PATTERN.matcher(name);
        if (!matcher.matches())
            throw new RegisterJsonParserException(
                    "Parser error in \"bits\" field. bit: \"" + name + "\"");

        return matcher;
    }

}
