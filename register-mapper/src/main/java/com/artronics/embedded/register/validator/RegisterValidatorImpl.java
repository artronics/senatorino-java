package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Matcher;

public class RegisterValidatorImpl implements RegisterValidator
{
    @Override
    public void validateRegisterName(String name) throws RegisterJsonParserException
    {

        Matcher matcher = NAME_PATTERN.matcher(name);

        if (!matcher.matches())
            throw new RegisterJsonParserException("Invalid register's name for: \""
                                                          + name + "\"\n" + NAME_VALIDAOTR_RULES);
    }

    @Override
    public boolean validateAddress(String address)
    {
        Matcher mat = ADDRESS_PATTERN.matcher(address);

        return mat.matches();

    }

    @Override
    public boolean validateAddressRange(String address, int min, int max)
    {
        int startAdd = Integer.decode(address.substring(0, address.indexOf(":")));
        int endAdd = Integer.decode(address.substring(address.indexOf(":") + 1));

        if (endAdd <= startAdd)
            return false;

        return !(startAdd < min || endAdd > max);

    }

    @Override
    public boolean validateAddressRange(String address)
    {
        int min = 0;
        int max = Integer.MAX_VALUE;

        return validateAddressRange(address, min, max);
    }
}
