package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.bpm.dto.DynamicDataLine;

public interface ICoreEngine {

    void fireLayoutWriteBack(IRequest request, Long pageId, String layoutCode, DynamicDataLine dataLine);

}
