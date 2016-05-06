package com.artronics.senatorino.core.register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterJsonParser
{
    private static final Logger log = Logger.getLogger(RegisterJsonParser.class);

    private static final ObjectMapper OM = new ObjectMapper();
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][A-Z,0-9]+(([A-Z]\\*)?)");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("0[xX][0-9a-fA-F]+:0[xX][0-9a-fA-F]+");

    private final InputStream in;

    private int startAddress;
    private int endAddress;

    public RegisterJsonParser(InputStream in)
    {
        this.in = in;
    }

    public void parse() throws IOException
    {
        JsonNode root = OM.readTree(in);

        String addrRange = root.path("range").asText();
        validateAddress("bank register", addrRange);
        startAddress = Integer.decode(addrRange.substring(0, addrRange.indexOf(":")));
        endAddress = Integer.decode(addrRange.substring(addrRange.indexOf(":") + 1));

        JsonNode registers = root.path("registers");
        JsonNode n = root.path("registers");
        log.debug(OM.writerWithDefaultPrettyPrinter().writeValueAsString(n));
        for (JsonNode reg : registers) {
            String name = reg.path("name").asText();
            String address = reg.path("address").asText();
            validate(name, address);
        }
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

        if (startAdd < startAddress || endAdd > endAddress)
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
