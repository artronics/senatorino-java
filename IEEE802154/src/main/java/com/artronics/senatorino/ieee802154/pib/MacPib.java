package com.artronics.senatorino.ieee802154.pib;

import com.artronics.senatorino.ieee802154.exceptions.PibValidationException;

/**
 * <p>The MAC PIB comprises the attributes required to manage the MAC sublayer of a device. The attributes contained in
 * the MAC PIB are presented in Table 52 (section 6.4.2). Attributes marked with a dagger (&#8224) are read-only
 * attributes (i.e., attribute can only be set by the MAC sublayer), which can be read by the next higher layer using
 * the MLME-GET.request primitive. All other attributes can be read or written by the next higher layer using the
 * MLME-GET.request or MLME-SET.request primitives, respectively. Attributes marked with a diamond (&#9830) are optional
 * for an RFD; attributes marked with an asterisk (*) are optional for both device types (i.e., RFD and FFD).</p>
 */
public class MacPib
{
    /**
     * The extended address assigned to the device.
     * <dl>
     * <dt>Range: </dt><dd>Device specific</dd>
     * <dt>Description: </dt><dd>The extended address assigned to the device.</dd>
     * <dt>Default: </dt><dd>Implementation specific</dd>
     * </dl>
     */
//    byte[] macExtendedAddress = new byte[4];
    Pib<byte[]> macExtendedAddress =
            new Pib(Pib.Name.macExtendedAddress, null, Pib.Layer.MAC, false, new PibValidator<byte[]>()
            {
                @Override
                public void validate(Pib<byte[]> pib) throws PibValidationException
                {
                    if (pib.getValue() != null && pib.getValue().length != 4) {
                        throw new PibValidationException(pib);
                    }
                }
            });

    public Pib<byte[]> getMacExtendedAddress()
    {
        return macExtendedAddress;
    }

}
