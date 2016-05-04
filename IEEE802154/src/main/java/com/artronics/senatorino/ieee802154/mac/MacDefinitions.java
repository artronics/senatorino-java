package com.artronics.senatorino.ieee802154.mac;

public interface MacDefinitions
{
    enum macStatus
    {
        SUCCESS
    }
    enum macChannels
    {

        CH11((byte) 11),;

        private byte ch;

        macChannels(byte ch)
        {
            this.ch = ch;
        }
    }
}
