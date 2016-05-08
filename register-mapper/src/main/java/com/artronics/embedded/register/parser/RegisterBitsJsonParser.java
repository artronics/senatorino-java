package com.artronics.embedded.register.parser;

import com.artronics.embedded.register.RegisterJsonParserException;
import com.artronics.embedded.register.validator.RegisterBitsValidator;
import com.artronics.embedded.register.validator.RegisterBitsValidatorImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.regex.Matcher;

import static com.artronics.embedded.register.validator.RegisterBitsValidator.DEFAULT_LENGTH;

public class RegisterBitsJsonParser
{
    private final RegisterBitsValidator validator;
    private final int length;

    private String bitName;

    private Integer mask;

    private Integer upper;
    private Integer lower;
    private Integer ordinal;

    public RegisterBitsJsonParser()
    {
        this.length = DEFAULT_LENGTH;
        this.validator = new RegisterBitsValidatorImpl();
    }

    public ArrayNode parseJsonNode(ArrayNode bits) throws RegisterJsonParserException
    {
        //reset all values to null
        ordinal = null;
        upper = null;
        lower = null;
        mask = null;

        for (JsonNode bit : bits) {
            JsonNode jname = bit.path("name");
            if (jname.isMissingNode()) {
                throw new RegisterJsonParserException("\"name\" field is mandatory.");
            }
            String name = bit.get("name").asText();
            Matcher matcher = validator.validateName(name);

            bitName = matcher.group(1);
            String ordinalGrp = matcher.group(5);
            if (ordinalGrp != null) {
                ordinal = Integer.parseInt(matcher.group(6));
            }else {
                String rangeOrMaskGrp = matcher.group(7);
                //range
                if (rangeOrMaskGrp.contains(":")) {
                    lower = Integer.parseInt(matcher.group(10));
                    upper = Integer.parseInt(matcher.group(11));
                    if (lower > upper)
                        throw new RegisterJsonParserException("Parser error in bit name: \""
                                                                      + bitName + "\". " +
                                                                      "Lower bound is greater than " +
                                                                      "upper bound");
                }
                //mask
                else {
                    mask = Integer.decode(matcher.group(8));
                }
            }
        }


        return bits;
    }

    public String getBitName()
    {
        return bitName;
    }

    public Integer getOrdinal()
    {
        return ordinal;
    }

    public Integer getUpper()
    {
        return upper;
    }

    public Integer getLower()
    {
        return lower;
    }

    public Integer getMask()
    {
        return mask;
    }
}
