package com.artronics.senatorino.ieee802154.mac.mlme;

public interface Request<T, P>
{
    P request(T request);
}
