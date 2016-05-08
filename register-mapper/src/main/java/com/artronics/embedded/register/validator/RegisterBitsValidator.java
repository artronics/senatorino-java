package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Pattern;

public interface RegisterBitsValidator
{
    String NAME_REGEX = "(([A-Z]++)|([A-Z][A-Z,0-9]+[A-Z]))";
    String NUM_REGEX = "((0[x|X][0-9A-Fa-f]+)|(\\d+))";
    String ORDINAL_REGEX = "(\\(" + NUM_REGEX + "\\))";
    String RANGE_REGEX = "(<(" + NUM_REGEX + "):(" + NUM_REGEX + ")>)";
    String NAME_VALIDATOR_REGEX = NAME_REGEX + "(" + ORDINAL_REGEX + "|" + RANGE_REGEX + ")";

    Pattern NAME_PATTERN = Pattern.compile(NAME_VALIDATOR_REGEX);
    int DEFAULT_LENGTH = 8;


    void validateName(String name) throws RegisterJsonParserException;
}
