package com.artronics.senatorino.ieee802154.mac;

/**
 * Mac Constants described in <em>section 6.4.2</em> of standard.
 */
public enum MacConstants
{
    /**
     * <p>The number of symbols forming a superframe slot when the superframe order is equal to zero, as described in
     * 5.1.1.1.</p>
     * <dl>
     * <dt>Value: </dt><dd>60</dd>
     * </dl>
     */
    aBaseSlotDuration(60),

    /**
     * The number of symbols forming a superframe when the superframe order is equal to zero.
     * <dl>
     * <dt>Value: </dt><dd>{@link MacConstants#aNumSuperframeSlots} X {@link MacConstants#aBaseSlotDuration}</dd>
     * </dl>
     */
//    aBaseSuperframeDuration(MacConstants.aNumSuperframeSlots.value * MacConstants.aBaseSlotDuration.value),

    /**
     * The number of slots contained in any superframe.
     * <dl>
     * <dt>Value</dt><dd>16</dd>
     * </dl>
     */
    aNumSuperframeSlots(16);

    public int value;

    MacConstants(int value)
    {
        this.value = value;
    }
}
