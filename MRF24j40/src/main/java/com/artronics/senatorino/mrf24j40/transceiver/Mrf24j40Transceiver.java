package com.artronics.senatorino.mrf24j40.transceiver;

import com.artronics.senatorino.ieee802154.mac.reset.ResetType;
import com.artronics.senatorino.ieee802154.transceiver.Transceiver;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Mrf24j40Transceiver implements Transceiver
{

    /**
     * <p>MRF24J40 has four reset types. 1.Power-On-Reset 2.With Reset pin 3.Software Reset 4.RF State-Machine Reset.
     * See <em>section 3.0</em> of Datasheet</p>
     * <ul>
     * <li>Power-on-Reset: The MRF24J40 has built-in Power-on Reset circuitry that will automatically reset all control
     * registers when power is applied. It is recommended to delay 2 ms after a Reset before accessing the MRF24J40 to
     * allow the RF circuitry to start up and stabilize.</li>
     * <p>
     * <li>RESET Pin: The MRF24J40 can be reset by the host microcontroller by asserting the RESET pin 13 low. All
     * control registers will be reset. The MRF24J40 will be released from Reset approximately 250 us after RESET is
     * released. The RESET pin has an internal weak pull-up resistor. It is recommended to delay 2 ms after a Reset
     * before accessing the MRF24J40 to allow the RF circuitry to start up and stabilize.</li>
     * <p>
     * <li>Software Reset: A Software Reset can be performed by the host microcontroller. The power management circuitry
     * is reset by setting the RSTPWR (0x2A&lt;2&gt;) bit to '1'. The control registers retain their values. The
     * baseband circuitry is reset by setting the RSTBB (0x2A<1>) bit to '1'. The control registers retain their values.
     * The MAC circuitry is reset by setting the RSTMAC (0x2A<0>) bit to '1'. All control registers will be reset. The
     * Resets can be performed individually or together. The bit(s) will be automatically cleared to '0' by hardware. No
     * delay is necessary after a Software Reset.</li>
     * <p>
     * <li>RF State Machine Reset: Perform an RF State Machine Reset by setting to '1' the RFRST (RFCTL 0x36<2>) bit and
     * then clearing to '0'. Delay at least 192 us after performing to allow the RF circuitry to calibrate. The control
     * registers retain their values.
     * <em>Note: The RF state machine should be Reset after the frequency channel has been changed (RFCON0
     * 0x200).</em></li>
     * </ul>
     */
    @Override
    public void reset(ResetType resetType)
    {
        switch (resetType) {
            case SOFTWARE:

                break;
            case RF:
                throw new NotImplementedException();
        }
    }
}
