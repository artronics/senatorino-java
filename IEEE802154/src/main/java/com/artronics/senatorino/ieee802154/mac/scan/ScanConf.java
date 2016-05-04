package com.artronics.senatorino.ieee802154.mac.scan;

import com.artronics.senatorino.ieee802154.mac.MacDefinitions;
import com.artronics.senatorino.ieee802154.mac.pan.PanDescriptor;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ScanConf
{
    private final ScanReq scanReq;
    private final MacDefinitions.macStatus status;
    private EnumSet<MacDefinitions.macChannels> unscannedChannels;
    private List<Integer> energyDetectList = new ArrayList<>();
    private List<PanDescriptor> panDescriptorList = new ArrayList<>();

    public ScanConf(ScanReq scanReq, MacDefinitions.macStatus status)
    {
        this.scanReq = scanReq;
        this.status = status;
    }

    public ScanReq getScanReq()
    {
        return scanReq;
    }

    public MacDefinitions.macStatus getStatus()
    {
        return status;
    }

    public EnumSet<MacDefinitions.macChannels> getUnscannedChannels()
    {
        return unscannedChannels;
    }

    public void setUnscannedChannels(
            EnumSet<MacDefinitions.macChannels> unscannedChannels)
    {
        this.unscannedChannels = unscannedChannels;
    }

    public List<Integer> getEnergyDetectList()
    {
        return energyDetectList;
    }

    public void setEnergyDetectList(List<Integer> energyDetectList)
    {
        this.energyDetectList = energyDetectList;
    }

    public List<PanDescriptor> getPanDescriptorList()
    {
        return panDescriptorList;
    }

    public void setPanDescriptorList(List<PanDescriptor> panDescriptorList)
    {
        this.panDescriptorList = panDescriptorList;
    }

}
