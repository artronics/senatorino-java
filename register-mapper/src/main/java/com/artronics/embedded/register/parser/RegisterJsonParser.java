package com.artronics.embedded.register.parser;

import com.artronics.embedded.register.RegisterJsonParserException;
import com.artronics.embedded.register.validator.RegisterValidator;
import com.artronics.embedded.register.validator.RegisterValidatorImpl;
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

public class RegisterJsonParser
{
    private static final Logger log = Logger.getLogger(RegisterJsonParser.class);

    private static final ObjectMapper OM = new ObjectMapper();

    public final RegisterValidator validator;

    private final InputStream in;
    private final OutputStream out;

    private int bankStartAddr = 0;
    private int bankEndAddr = Integer.MAX_VALUE;

    public RegisterJsonParser(InputStream in, OutputStream out)
    {
        this.in = in;
        this.out = out;
        this.validator = new RegisterValidatorImpl();
    }

    public void parse() throws IOException
    {
        JsonNode root = OM.readTree(in);

        final PrintStream pout = new PrintStream(out);

        ObjectWriter ow = OM.writerWithDefaultPrettyPrinter();

        String addrRange = root.path("range").asText();
        if (!addrRange.equals("")) {
            validateBankAddress(addrRange);
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

    private void validateBankAddress(String address) throws RegisterJsonParserException
    {
        if (!validator.validateAddress(address)) {
            throw new RegisterJsonParserException("Address for: \"range\" is not valid in register bank bounds: \n" +
                                                          RegisterValidator.ADDRESS_RANGE_RULES);
        }
        if (!validator.validateAddressRange(address, bankStartAddr, bankEndAddr)) {
            throw new RegisterJsonParserException("Address range for: \"range\" is not valid for in bank bounds: \n"
                                                          + RegisterValidator.ADDRESS_RANGE_RULES);
        }
    }

    public void validate(String name, String address) throws RegisterJsonParserException
    {
        validator.validateRegisterName(name);

        if (name.substring(name.length() - 1).equals("*")) {
            if (!validator.validateAddress(address)) {
                throw new RegisterJsonParserException("Address is not valid for register: \"" +
                                                              name + "\"\n" + RegisterValidator.ADDRESS_RANGE_RULES);
            }
            if (!validator.validateAddressRange(address, bankStartAddr, bankEndAddr)) {
                throw new RegisterJsonParserException("Address range is not valid for register: \"" +
                                                              name + "\"\n" + RegisterValidator.ADDRESS_RANGE_RULES);
            }
        }
    }

}
