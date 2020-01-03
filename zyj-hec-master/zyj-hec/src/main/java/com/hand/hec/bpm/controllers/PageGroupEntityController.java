package com.hand.hec.bpm.controllers;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.bpm.dto.PageGroup;
import com.hand.hec.bpm.dto.PageGroupEntity;
import com.hand.hec.bpm.exception.BpmException;
import com.hand.hec.bpm.service.IPageGroupEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageGroupEntityController extends BaseController {

    @Autowired
    private IPageGroupEntityService service;

    @RequestMapping(value = "/bpm/pageGroupEntity/query")
    @ResponseBody
    public ResponseData query(HttpServletRequest request, PageGroupEntity entity,
                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestCtx = createRequestContext(request);


        Criteria criteria = new Criteria(entity);
        criteria.where(new WhereField(PageGroup.FIELD_GROUP_ID, Comparison.EQUAL),
                        new WhereField(PageGroup.FIELD_GROUP_CODE, Comparison.LIKE),
                        new WhereField(PageGroup.FIELD_GROUP_DESC, Comparison.LIKE));
        return new ResponseData(service.selectOptions(requestCtx, entity, criteria, page, pageSize));
    }

    @RequestMapping(value = "/bpm/pageGroupEntity/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody List<PageGroupEntity> dto, HttpServletRequest request,
                    BindingResult result) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        service.setRequest(requestCtx);

        /**
         * 判断当前是删除还是更新， 如果是一条记录，且状态为删除的话，单独执行删除过程
         *
         */
        if (dto.size() == 1 && DTOStatus.DELETE.equals(dto.get(0).get__status())) {
            PageGroupEntity entity = dto.get(0);
            service.deleteByPrimaryKey(entity);
        } else {
            int currentLevel = 1;
            List<PageGroupEntity> currentLevelList = null;
            List<PageGroupEntity> parentLevelList = null;
            Boolean currentLevelFlag = false;
            while (true) {
                //
                // 找到当前层级的entity，执行更新操作，如果当前层级没有entity，那么退出循环
                // 如果当前entity有值，那么找到当前entity中，parentEntityId为空，但是parentEntityCode不为空，则循环找到parentEntity，并设置ID
                // ------------------------------------------------------------------------------
                int currentCount = 0;
                parentLevelList = new ArrayList<PageGroupEntity>();
                if (currentLevelList != null) {
                    parentLevelList.addAll(currentLevelList);
                }
                currentLevelList = new ArrayList<PageGroupEntity>();

                for (PageGroupEntity entity : dto) {
                    /**
                     * 每个实体循环的时候都将currentLevelFlag设置为false，如果当前实体的parentEntityCode在parentLevelList中，说明是当前level
                     */
                    currentLevelFlag = false;
                    /**
                     * 如果当前有父实体信息，则寻找父实体，否则直接添加到currentLevelList，作为根节点
                     */
                    if (currentLevel != 1) {
                        if (entity.getParentEntityCode() != null) {
                            for (PageGroupEntity parentEntity : parentLevelList) {
                                if (parentEntity.getEntityCode().equals(entity.getParentEntityCode())) {
                                    entity.setParentEntityId(parentEntity.getEntityId());
                                    currentLevelFlag = true;
                                    break;
                                }
                            }

                            if (currentLevelFlag && entity.getParentEntityId() != null) {
                                currentLevelList.add(entity);
                                currentCount++;
                            }
                        }
                    } else if (currentLevel == 1 && entity.getParentEntityCode() == null) {
                        currentLevelList.add(entity);
                        currentCount++;
                    }
                }

                if (currentCount == 0) {
                    break;
                }

                currentLevelList = service.batchUpdate(requestCtx, currentLevelList);
                currentLevel++;
            }
        }

        return new ResponseData(true);
    }
}
