package com.hand.hap.intergration.beans;

import java.io.Serializable;

import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;

/**
 * @author xiangyu.qi@hand-china.com on 2017/9/23.
 */
public class HapinterfaceBound implements Serializable {

    private HapInterfaceInbound inbound;

    private HapInterfaceOutbound outbound;


    public HapinterfaceBound(){

    }

    public HapinterfaceBound(HapInterfaceInbound inbound){
        this.inbound = inbound;
    }

    public HapinterfaceBound(HapInterfaceOutbound outbound){
        this.outbound = outbound;
    }

    public HapInterfaceInbound getInbound() {
        return inbound;
    }

    public void setInbound(HapInterfaceInbound inbound) {
        this.inbound = inbound;
    }

    public HapInterfaceOutbound getOutbound() {
        return outbound;
    }

    public void setOutbound(HapInterfaceOutbound outbound) {
        this.outbound = outbound;
    }
}
