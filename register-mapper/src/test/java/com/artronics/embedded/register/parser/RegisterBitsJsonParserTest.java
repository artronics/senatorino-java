package com.artronics.embedded.register.parser;

import com.artronics.embedded.register.RegisterJsonParserException;
import com.artronics.embedded.register.validator.RegisterBitsValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class RegisterBitsJsonParserTest
{
    private static final ObjectMapper OM = new ObjectMapper();
    private static final ObjectWriter OW = OM.writerWithDefaultPrettyPrinter();

    private static final String ORDINAL = "(1)";
    private static final String MASK = "<0x0F>";
    private static final String RANGE = "<2:4>";

    private RegisterBitsJsonParser parser;
    private String name;

    @Before
    public void setUp() throws Exception
    {
        parser = new RegisterBitsJsonParser();
    }


    @Test
    public void name() throws Exception
    {
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_there_is_name_field() throws Exception
    {
        String s = "{\"bits\":[{\"description\":\"FOO\"}]}";

        parser.parseJsonNode(createBitsNode(s));
    }

    private static ArrayNode createBitsNode(String s) throws IOException
    {
        JsonNode root = OM.readTree(s);

        return (ArrayNode) root.path("bits");
    }

    private static JsonNode getFirstBit(String s) throws IOException
    {
        ArrayNode bits = createBitsNode(s);
        for (JsonNode bit : bits) {
            return bit;
        }
        throw new IllegalStateException();
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

    private void assertGetBitName(String name) throws IOException
    {
        String ord = "{\"bits\":[{\"name\":\"" + name + ORDINAL + "\"}]}";
        String rng = "{\"bits\":[{\"name\":\"" + name + RANGE + "\"}]}";
        String msk = "{\"bits\":[{\"name\":\"" + name + MASK + "\"}]}";

        parser.parseJsonNode(createBitsNode(ord));
        assertThat(parser.getBitName(), equalTo(name));

        parser.parseJsonNode(createBitsNode(rng));
        assertThat(parser.getBitName(), equalTo(name));

        parser.parseJsonNode(createBitsNode(msk));
        assertThat(parser.getBitName(), equalTo(name));
    }

    @Test
    public void it_should_getOrdinal() throws Exception
    {
        name = "AS3S(3)";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
        assertThat(parser.getOrdinal(), equalTo(3));
    }

    @Test
    public void it_should_getUpper() throws Exception
    {
        name = "AS3S<1:3>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
        assertThat(parser.getUpper(), equalTo(3));
    }

    @Test
    public void it_should_getLower() throws Exception
    {
        name = "AS3S<1:3>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
        assertThat(parser.getLower(), equalTo(1));
    }

    @Test
    public void it_should_getMask() throws Exception
    {
        name = "AS3S<0x0F>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
        assertThat(parser.getMask(), equalTo(0x0F));
    }

    @Test
    public void it_should_reset_other_parameter_to_Null() throws Exception
    {
        name = "AS3S<1:3>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));

        name = "AS3S(2)";
        bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));

        name = "AS3S<0x0F>";
        bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));

        assertNull(parser.getLower());
        assertNull(parser.getUpper());
        assertNull(parser.getOrdinal());
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_thr_exp_if_lower_if_greater_than_upper() throws Exception
    {
        name = "AS3S<3:1>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_thr_exp_if_upper_is_greater_than_length() throws Exception
    {
        //default length is 8
        name = "AS3S<1:" + RegisterBitsValidator.DEFAULT_LENGTH + ">";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_thr_exp_if_ordinal_is_greater_than_length() throws Exception
    {
        name = "AS3S(8)";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_thr_exp_if_lower_if_mask_is_greater_than_2_to_the_power_length() throws Exception
    {
        name = "AS3S<0x100>";
        String bits = "{\"bits\":[{\"name\":\"" + name + "\"}]}";
        parser.parseJsonNode(createBitsNode(bits));
    }
}