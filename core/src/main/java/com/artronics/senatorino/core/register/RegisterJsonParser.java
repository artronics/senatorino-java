package com.artronics.senatorino.core.register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterJsonParser
{
    private static final Logger log = Logger.getLogger(RegisterJsonParser.class);

    private static final ObjectMapper OM = new ObjectMapper();
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][A-Z,0-9]+(([A-Z]\\*)?)");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("0[xX][0-9a-fA-F]+:0[xX][0-9a-fA-F]+");

    private final InputStream in;
    private final OutputStream out;

    private int bankStartAddr = 0;
    private int bankEndAddr = Integer.MAX_VALUE;

    public RegisterJsonParser(InputStream in, OutputStream out)
    {
        this.in = in;
        this.out = out;
    }

    public void parse() throws IOException
    {
        JsonNode root = OM.readTree(in);

        final PrintStream pout = new PrintStream(out);

        ObjectWriter ow = OM.writerWithDefaultPrettyPrinter();

        String addrRange = root.path("range").asText();
        if (!addrRange.equals("")) {
            validateAddress("bank register", addrRange);

            bankStartAddr = Integer.decode(addrRange.substring(0, addrRange.indexOf(":")));
            bankEndAddr = Integer.decode(addrRange.substring(addrRange.indexOf(":") + 1));
        }
        ArrayNode newRegisters = OM.createArrayNode();

        JsonNode registers = root.path("registers");
        for (JsonNode reg : registers) {
            String name = reg.path("name").asText();
            String address = reg.path("address").asText();
            validate(name, address);

            if (name.substring(name.length() - 1).equals("*")) {

                int startAdd = Integer.decode(address.substring(0, address.indexOf(":")));
                int endAdd = Integer.decode(address.substring(address.indexOf(":") + 1));

                for (int i = 0; i < (endAdd - startAdd); i++) {
                    JsonNode regCopy = reg.deepCopy();

                    String newName = regCopy.get("name").asText();
                    newName = newName.substring(0, newName.length() - 1);

                    String newAdd = String.format("0x%08X", startAdd + i);

                    ((ObjectNode) regCopy).put("name", newName + i);
                    ((ObjectNode) regCopy).put("address", newAdd);

                    newRegisters.add(regCopy);
                }
            }else {
                JsonNode regCopy = reg.deepCopy();
                //It's the same address but changing the format
                String newAdd = String.format("0x%08X", Integer.decode(address));
                ((ObjectNode) regCopy).put("address", newAdd);

                newRegisters.add(regCopy);
            }
        }
        ((ObjectNode) root).set("registers", newRegisters);
        pout.print(ow.writeValueAsString(root));
    }

    public void validate(String name, String address) throws RegisterJsonParserException
    {
        validateName(name);

        if (name.substring(name.length() - 1).equals("*")) {
            validateAddress(name, address);
            validateAddressRange(name, address);
        }
    }

    private void validateAddressRange(String name, String address) throws RegisterJsonParserException
    {
        int startAdd = Integer.decode(address.substring(0, address.indexOf(":")));
        int endAdd = Integer.decode(address.substring(address.indexOf(":") + 1));
        if (endAdd <= startAdd)
            throw new RegisterJsonParserException(
                    "End address must be greate then start address in address range for register name: \"" + name
                            + "\"");

        if (startAdd < bankStartAddr || endAdd > bankEndAddr)
            throw new RegisterJsonParserException("address is not in range of register bank. register name: \"" +
                                                          name + "\"");
    }

    private static void validateAddress(String name, String address) throws RegisterJsonParserException
    {
        Matcher mat = ADDRESS_PATTERN.matcher(address);

        if (!mat.matches())
            throw new RegisterJsonParserException("Address field for register name: \"" + name + "\" is invalid. It " +
                                                          "should be a range with two hex number and \":\" as " +
                                                          "separator. Example 0X00:0xFF");
    }

    private static void validateName(String name) throws RegisterJsonParserException
    {
        Matcher matcher = NAME_PATTERN.matcher(name);

        if (!matcher.matches())
            throw new RegisterJsonParserException("Invalid \"name\" attribute for: \"" + name + "\". Names must obey " +
                                                          "" + "these rules:\n1-All letters must be upper case\n2-It " +
                                                          "must " +
                                                          "starts with a letter\n3-If last caharacter is an asterisk " +
                                                          "(*) the before character must not be a digit");
    }
}
