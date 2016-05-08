package com.artronics.embedded.register.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RegisterBitsJsonParser
{
    public ArrayNode parseJsonNode(ArrayNode bits)
    {

        for (JsonNode bit : bits) {
            ((ObjectNode) bit).put("kir", "foo");
        }

        return bits;
    }
}
