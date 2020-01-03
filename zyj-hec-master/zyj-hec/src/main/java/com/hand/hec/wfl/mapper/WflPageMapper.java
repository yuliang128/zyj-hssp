package com.hand.hec.wfl.mapper;

        import com.hand.hap.mybatis.common.Mapper;
        import com.hand.hec.wfl.dto.WflPage;

        import java.util.List;

public interface WflPageMapper extends Mapper<WflPage>{
   public List<WflPage> selectWflPage(WflPage wflPage);
}