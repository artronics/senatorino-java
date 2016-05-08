package com.artronics.embedded.register.validator;

import com.artronics.embedded.register.RegisterJsonParserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegisterBitsValidator
{
    String NAME_REGEX = "(([A-Z]++)|([A-Z][A-Z,0-9]+[A-Z]))";
    String HEX_REGEX = "(0[x|X][0-9A-Fa-f]+)";
    String NUM_REGEX = "(" + HEX_REGEX + "|(\\d+))";
    String ORDINAL_REGEX = "(\\((\\d)\\))";
    String RANGE_REGEX = "(<(((\\d+):(\\d+))|" + HEX_REGEX + ")>)";
    String NAME_VALIDATOR_REGEX = NAME_REGEX + "(" + ORDINAL_REGEX + "|" + RANGE_REGEX + ")";

    Pattern BIT_NAME_PATTERN = Pattern.compile(NAME_VALIDATOR_REGEX);
    int DEFAULT_LENGTH = 8;


    Matcher validateName(String name) throws RegisterJsonParserException;
}
