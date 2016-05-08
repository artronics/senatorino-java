package com.artronics.embedded.register.parser;

import com.artronics.embedded.register.RegisterJsonParserException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RegisterJsonParserTest
{
    private static final ObjectMapper OM = new ObjectMapper();
    private static final ObjectWriter OW = OM.writerWithDefaultPrettyPrinter();
    private RegisterJsonParser parser;
    private InputStream in;
    private OutputStream out;
    private String j;

    @Before
    public void setUp() throws Exception
    {
        out = new ByteArrayOutputStream();
    }

    //String to input stream
    private static InputStream json(String str)
    {
        return new ByteArrayInputStream(Charset.forName("UTF-16").encode(str).array());
    }

    @Test
    public void name() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR\",\"address\":\"0x2\"}]}}";
        JsonNode root = OM.readTree(json(s));
        JsonNode regs = root.path("registers");
        for (JsonNode reg : regs) {
            JsonNode jn = reg.deepCopy();
            ((ObjectNode) jn).put("name", "BAZ");
            ((ArrayNode) regs).add(jn);
            ((ArrayNode) regs).remove(0);


            System.out.println(OW.writeValueAsString(jn));
            OW.writeValueAsString(reg);
            break;
        }

        System.out.println(OW.writeValueAsString(root));
    }

    @Test
    public void it_shoud_expand_registers_with_range() throws Exception
    {
        String actStr = "{\"registers\":[{\"name\":\"FOO*\",\"address\":\"0x0A:0x0C\"}]}";
        String expStr = "{\"registers\":[{\"name\":\"FOO0\",\"address\":\"0x0000000A\"}," +
                "{\"name\":\"FOO1\",\"address\":\"0x0000000B\"}]}";


        parser = new RegisterJsonParser(json(actStr), out);
        parser.parse();
        String actJson = new String(out.toString());
        actJson = actJson.trim().replaceAll("\\n| +", "");

        assertThat(actJson, equalTo(expStr));
    }

    @Test
    public void it_should_add_unranged_registers_as_it_is() throws Exception
    {
        String actStr = "{\"registers\":[{\"name\":\"FOO\",\"address\":\"0x0A\"}]}";
        String expStr = "{\"registers\":[{\"name\":\"FOO\",\"address\":\"0x0000000A\"}]}";

        parser = new RegisterJsonParser(json(actStr), out);
        parser.parse();
        String actJson = new String(out.toString());
        actJson = actJson.trim().replaceAll("\\n| +", "");

        assertThat(actJson, equalTo(expStr));
    }

    @Test(expected = RegisterJsonParserException.class)
    public void register_with_range_must_not_contain_bits_field() throws Exception
    {
        String actStr = "{\"registers\":[{\"name\":\"FOO*\",\"address\":\"0x0A:0x12\"" +
                ",\"bits\":[]}]}";

        parser = new RegisterJsonParser(json(actStr), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void bits_field_must_be_an_array() throws Exception
    {
        String actStr = "{\"registers\":[{\"name\":\"FOO\",\"address\":\"0x0A\"" +
                ",\"bits\":{}}]}";

        parser = new RegisterJsonParser(json(actStr), out);
        parser.parse();
    }

    /*
                    VALIDATION
                 */
    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_field_has_low_case_letter() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAr\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_field_has_num_at_begining() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"1BAR\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_name_ends_with_num_and_asterisk() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR2*\",\"address\":\"0x2\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void it_should_throw_exp_if_address_is_range_and_address_is_not_valid() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x2:e1\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void start_address_must_be_less_than_end_address() throws Exception
    {
        String s = "{\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x2F:0X01\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

    @Test(expected = RegisterJsonParserException.class)
    public void both_start_and_end_addresses_must_be_in_range_of_reg_bank() throws Exception
    {
        String s = "{\"range\":\"0x11:0x22\",\"registers\":[{\"name\":\"BAR*\",\"address\":\"0x00:0X20\"}]}}";
        parser = new RegisterJsonParser(json(s), out);
        parser.parse();
    }

}