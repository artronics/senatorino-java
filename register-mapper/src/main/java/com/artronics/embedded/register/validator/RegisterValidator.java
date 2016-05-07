package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Pattern;

public interface RegisterValidator
{
    Pattern NAME_PATTERN = Pattern.compile("[A-Z][A-Z,0-9]+(([A-Z]\\*)?)");
    Pattern ADDRESS_PATTERN = Pattern.compile("0[xX][0-9a-fA-F]+:0[xX][0-9a-fA-F]+");

    String NAME_VALIDAOTR_RULES = "Rules for register name:\n" +
            "1-All letters must be upper case.\n" +
            "2-It must start with a letter.\n" +
            "3-If address is a range then last charachter in register's name must be \"*\"\n" +
            "4-The character before \"*\" must be a letter.\n";

    String ADDRESS_RANGE_RULES = "Rules for address range:\n" +
            "1-Address must be in hex format.\n" +
            "2-Use \":\" as address delimitre without any white spaces.\n" +
            "3-Lower bound must be less than high bound.\n" +
            "4-Both lower and higher bound must be in range of register bank" +
            " boiunds, if present.";

    void validateRegisterName(String name) throws RegisterJsonParserException;

    boolean validateAddress(String address);

    boolean validateAddressRange(String address, int min, int max);

    boolean validateAddressRange(String address);
}
