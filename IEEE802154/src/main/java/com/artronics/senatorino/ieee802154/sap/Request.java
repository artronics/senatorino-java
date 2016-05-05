package com.artronics.senatorino.ieee802154.sap;

public interface Request<T, P>
{
    P request(T request);
}
