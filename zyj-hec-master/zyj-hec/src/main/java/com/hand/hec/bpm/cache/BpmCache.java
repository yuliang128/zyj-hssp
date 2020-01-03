package com.hand.hec.bpm.cache;

import com.hand.hap.cache.impl.HashStringRedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * bpm 缓存.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/2/16
 */
public class BpmCache extends HashStringRedisCache<String> {
    
    private Logger logger = LoggerFactory.getLogger(BpmCache.class);

    public BpmCache(){
        this.setType(String.class);
    }

    @Override
    public String getValue(String key){
        return super.getValue(key);
    }

    @Override
    public void setValue(String key,String bpm){
        super.setValue(key,bpm);
    }
    

}
