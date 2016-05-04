package com.artronics.senatorino.ieee802154.mac.mlme;

public interface Request<T extends Primitive>
{
    void request(T primitive);
}
