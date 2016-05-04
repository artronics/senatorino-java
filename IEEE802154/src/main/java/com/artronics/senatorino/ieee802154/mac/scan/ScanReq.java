package com.artronics.senatorino.ieee802154.mac.scan;

import com.artronics.senatorino.ieee802154.mac.MacDefinitions;
import com.artronics.senatorino.ieee802154.mac.security.SecurityDto;

import java.util.EnumSet;

public class ScanReq
{
    public enum Type
    {
        ED, //Energy Detection
        ACTIVE,
        PASSIVE,
        ORPHAN
    }

    private final Type scanType;
    private final EnumSet<MacDefinitions.macChannels> scanChannels;
    private final int scanDuration;
    private final int channelPage;
    private final SecurityDto security;

    private ScanReq(Builder builder)
    {
        this.scanType = builder.scanType;
        this.scanChannels = builder.scanChannels;
        this.scanDuration = builder.scanDuration;
        this.channelPage = builder.channelPage;
        this.security = builder.security;
    }

    public Type getScanType()
    {
        return scanType;
    }

    public EnumSet<MacDefinitions.macChannels> getScanChannels()
    {
        return scanChannels;
    }

    public int getScanDuration()
    {
        return scanDuration;
    }

    public int getChannelPage()
    {
        return channelPage;
    }

    public SecurityDto getSecurity()
    {
        return security;
    }

    public static class Builder
    {
        private final Type scanType;
        private EnumSet<MacDefinitions.macChannels> scanChannels =
                EnumSet.allOf(MacDefinitions.macChannels.class);
        private int scanDuration = 1;
        private int channelPage;
        private SecurityDto security;

        public Builder(Type scanType)
        {
            this.scanType = scanType;
        }

        public ScanReq build()
        {
            return new ScanReq(this);
        }

        public Builder scanChannels(EnumSet<MacDefinitions.macChannels> scanChannels)
        {
            this.scanChannels = scanChannels;
            return this;
        }

        public Builder scanDuration(int scanDuration)
        {
            this.scanDuration = scanDuration;
            return this;
        }

        public Builder channelPage(int channelPage)
        {
            this.channelPage = channelPage;
            return this;
        }

        public Builder security(SecurityDto security)
        {
            this.security = security;
            return this;
        }

    }

}
