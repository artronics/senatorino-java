package com.artronics.embedded.register.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class RegisterBitsJsonParserTest
{
    private static final ObjectMapper OM = new ObjectMapper();
    private static final ObjectWriter OW = OM.writerWithDefaultPrettyPrinter();

    private RegisterBitsJsonParser parser;

    @Before
    public void setUp() throws Exception
    {
        parser = new RegisterBitsJsonParser();
    }


    @Test
    public void name() throws Exception
    {
        String s = "{\"bits\":[{\"name\":\"FOO\"}]}";

        ArrayNode bits = createBitsNode(s);
        ArrayNode n = parser.parseJsonNode(bits);
        printNode(n);
    }

    private static ArrayNode createBitsNode(String s) throws IOException
    {
        JsonNode root = OM.readTree(s);

        return (ArrayNode) root.path("bits");
    }

    private static void printNode(JsonNode n) throws JsonProcessingException
    {
        if (n.isArray()) {
            for (JsonNode jsonNode : n) {
                System.out.println(OW.writeValueAsString(jsonNode));
            }
            return;
        }

        System.out.println(OW.writeValueAsString(n));
    }

    private static JsonNode getFirstBit(String json) throws IOException
    {
        JsonNode root = OM.readTree(json);
        JsonNode regs = root.path("registers");
        for (JsonNode reg : regs) {
            JsonNode bits = reg.path("bits");
            for (JsonNode bit : bits) {
                return bit;
            }
            throw new IllegalStateException();
        }
        throw new IllegalStateException();
    }
}