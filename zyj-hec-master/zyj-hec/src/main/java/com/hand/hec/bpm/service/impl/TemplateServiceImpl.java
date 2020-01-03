package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.Template;
import com.hand.hec.bpm.mapper.*;
import com.hand.hec.bpm.service.ITemplateService;
import com.hand.hec.bpm.service.ITpltButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

//import com.hand.hap.core.mutl.IPrimaryKeyProvider;

@Service
@Transactional(rollbackFor = Exception.class)
public class TemplateServiceImpl extends BaseServiceImpl<Template> implements ITemplateService {

    @Autowired
    private TemplateMapper mapper;
    /*@Autowired
    private IPrimaryKeyProvider primaryKeyProvider;*/
    @Autowired
    private TpltButtonMapper bpmTpltButtonsMapper;
    @Autowired
    private TpltLayoutBasicMapper bpmTpltLayoutBasicMapper;
    @Autowired
    private TpltLayoutButtonMapper bpmTpltLayoutButtonsMapper;
    @Autowired
    private TpltLayoutFormMapper bpmTpltLayoutFormMapper;
    @Autowired
    private TpltLayoutGridMapper bpmTpltLayoutGridMapper;
    @Autowired
    private TpltLayoutTabMapper bpmTpltLayoutTabMapper;
    @Autowired
    private ITpltButtonService iBpmTpltButtonsService;
    @Autowired
    private TpltTagBasicMapper bpmTpltTagsBasicMapper;
    /*@Autowired
	private BpmPageButtonsMapper bpmPageButtonsMapper;
    @Autowired
	private BpmPageLayoutBasicMapper bpmPageLayoutBasicMapper;
    @Autowired
	private BpmPageLayoutButtonsMapper bpmPageLayoutButtonsMapper;
	@Autowired
	private BpmPageTagsBasicMapper bpmPageTagsBasicMapper;
	@Autowired
	private BpmPageTagsComboboxMapper bpmPageTagsComboboxMapper;
	@Autowired
	private BpmPageTagsComboboxFieldsMapper bpmPageTagsComboboxFieldsMapper;
	@Autowired
	private BpmPageTagsComboboxMappersMapper bpmPageTagsComboboxMappersMapper;
	@Autowired
	private BpmPageTagsComboboxOptionsMapper bpmPageTagsComboboxOptionsMapper;
	@Autowired
	private BpmPageTagsLovFieldsMapper bpmPageTagsLovFieldsMapper;
	@Autowired
	private BpmPageTagsLovMappersMapper bpmPageTagsLovMappersMapper;
	@Autowired
	private BpmPageTagsRadioOptionsMapper bpmPageTagsRadioOptionsMapper;
	@Autowired
	private BpmPageTagDataGuideMapper bpmPageTagDataGuideMapper;
	@Autowired
	private BpmPageTagEventsMapper bpmPageTagEventsMapper;
	@Autowired
	private BpmPageLayoutFormMapper bpmPageLayoutFormMapper;
	@Autowired
	private BpmPageLayoutGridMapper bpmPageLayoutGridMapper;
	@Autowired
	private BpmPageLayoutTabMapper bpmPageLayoutTabMapper;
	@Autowired
	private BpmPageLayoutEventsMapper bpmPageLayoutEventsMapper;


    @Autowired
	private IBpmPageButtonsService iBpmPageButtonsService;
    @Autowired
	private IBpmPageLayoutBasicService iBpmPageLayoutBasicService;
    @Autowired
	private IBpmPageLayoutButtonsService iBpmPageLayoutButtonsService;
    @Autowired
	private IBpmPageLayoutFormService iBpmPageLayoutFormService;
    @Autowired
	private IBpmPageLayoutGridService iBpmPageLayoutGridService;
    @Autowired
	private IBpmPageLayoutTabService iBpmPageLayoutTabService;
    @Autowired
	private IBpmPageTagsBasicService iBpmPageTagsBasicService;
    @Autowired
	private IBpmPageTagsComboboxService iBpmPageTagsComboboxService;
	@Autowired
	private IBpmPageTagsCheckboxService iBpmPageTagsCheckboxService;
	@Autowired
	private IBpmPageTagsDatepickerService iBpmPageTagsDatepickerService;
	@Autowired
	private IBpmPageTagsDatetimepickerService iBpmPageTagsDatetimepickerService;
	@Autowired
	private IBpmPageTagsLabelService iBpmPageTagsLabelService;
	@Autowired
	private IBpmPageTagsLovService iBpmPageTagsLovService;
	@Autowired
	private IBpmPageTagsNumberfieldService iBpmPageTagsNumberfieldService;
	@Autowired
	private IBpmPageTagsRadioService iBpmPageTagsRadioService;
	@Autowired
	private IBpmPageTagsTextfieldService iBpmPageTagsTextfieldService;
	@Autowired
	private IBpmPageTagsTreeService iBpmPageTagsTreeService;
	@Autowired
	private IBpmPageTagsTreegridService iBpmPageTagsTreegridService;
	@Autowired
	private IBpmPageTagsComboboxFieldsService iBpmPageTagsComboboxFieldsService;
	@Autowired
	private IBpmPageTagsComboboxMappersService iBpmPageTagsComboboxMappersService;
	@Autowired
	private IBpmPageTagsComboboxOptionsService iBpmPageTagsComboboxOptionsService;
	@Autowired
	private IBpmPageTagsLovFieldsService iBpmPageTagsLovFieldsService;
	@Autowired
	private IBpmPageTagsLovMappersService iBpmPageTagsLovMappersService;
	@Autowired
	private IBpmPageTagsRadioOptionsService iBpmPageTagsRadioOptionsService;
	@Autowired
	private IBpmPageTagDataGuideService	iBpmPageTagDataGuideService;
	@Autowired
	private IBpmPageTagEventsService iBpmPageTagEventsService;
	@Autowired
	private IBpmPageLayoutEventsService iBpmPageLayoutEventsService;*/

    @Override
    public List<Template> selectAllByLike(Template bpmTemplatesDTO) {
        // TODO Auto-generated method stub
        return mapper.selectAllByLike(bpmTemplatesDTO);
    }

    public List<Template> select(IRequest request, Template condition) {
        // TODO Auto-generated method stub
        return mapper.selectAllByLike(condition);
    }

    @Override
    public List<Template> queryTemplateLov(IRequest request, Template bpmTemplates) {
        return mapper.queryTemplateLov(bpmTemplates);
    }

    @Override
    public Template setTemplateChange(IRequest request, Template bpmTemplatesDTO) {
        Long template_id = bpmTemplatesDTO.getTemplateId();
        //循环group
        List<HashMap> hashMap = mapper.getPageGroup(template_id);
        if (hashMap.size() > 0) {
            for (HashMap hp : hashMap) {
//                changeBpmPageButtons(request, template_id, Long.parseLong(hp.get("page_id").toString()));
//                changeBpmPageLayoutBasic(request, template_id, Long.parseLong(hp.get("page_id").toString()));
            }

        }
        return bpmTemplatesDTO;
    }


    @Override
    public List<Template> queryDatesetFetch(IRequest request, Long pageId) {
        return mapper.queryDatesetFetch(pageId);
    }
/*
	@Override
	public void changeBpmPageButtons(IRequest request,Long templateId,Long pageId){
		//
		TpltButton queryBpmTpltButtons = new TpltButton();
		queryBpmTpltButtons.setTemplateId(templateId);
		List<TpltButton> bpmTpltButtonsList = bpmTpltButtonsMapper.queryByParas(queryBpmTpltButtons);
		if (bpmTpltButtonsList.size() == 0){
			return;
		}
		for (TpltButton bpmTpltButtons : bpmTpltButtonsList){
			BpmPageButtons queryBpmPageButtons = new BpmPageButtons();
			queryBpmPageButtons.setButtonCode(bpmTpltButtons.getButtonCode());
			queryBpmPageButtons.setPageId(pageId);
			List<BpmPageButtons> bpmPageButtonsList = bpmPageButtonsMapper.queryByParas(queryBpmPageButtons);

			if (bpmPageButtonsList.size() == 0){
				BpmPageButtons bpmPageButtons = new BpmPageButtons();
				bpmPageButtons.setPageId(pageId);
				bpmPageButtons.setButtonSequence(bpmTpltButtons.getButtonSequence());
				bpmPageButtons.setButtonCode(bpmTpltButtons.getButtonCode());
				bpmPageButtons.setButtonDesc(bpmTpltButtons.getButtonDesc());
				bpmPageButtons.setOperationType(bpmTpltButtons.getOperationType());
				bpmPageButtons.setTemplateFlag("Y");
				bpmPageButtons.setId(bpmTpltButtons.getId());
				bpmPageButtons.setText(bpmTpltButtons.getText());
				bpmPageButtons.setClick(bpmTpltButtons.getClick());
				bpmPageButtons.setType(bpmTpltButtons.getType());
				bpmPageButtons.setWidth(bpmTpltButtons.getWidth());
				bpmPageButtons.setHeight(bpmTpltButtons.getHeight());
				bpmPageButtons.setIcon(bpmTpltButtons.getIcon());
				bpmPageButtons.setDisabled(bpmTpltButtons.getDisabled());
				bpmPageButtons.setHidden(bpmTpltButtons.getHidden());
				bpmPageButtons.setStyle(bpmTpltButtons.getStyle());
				bpmPageButtons.setBtnstyle(bpmTpltButtons.getBtnstyle());

				iBpmPageButtonsService.insert(context,bpmPageButtons);
			}

			if (bpmPageButtonsList.size() == 1){

				BpmPageButtons bpmPageButtons = new BpmPageButtons();
				bpmPageButtons.setPageId(pageId);
				bpmPageButtons.setButtonSequence(bpmTpltButtons.getButtonSequence());
				bpmPageButtons.setButtonCode(bpmTpltButtons.getButtonCode());
				bpmPageButtons.setButtonDesc(bpmTpltButtons.getButtonDesc());
				bpmPageButtons.setOperationType(bpmTpltButtons.getOperationType());
				bpmPageButtons.setTemplateFlag("Y");
				bpmPageButtons.setId(bpmTpltButtons.getId());
				bpmPageButtons.setText(bpmTpltButtons.getText());
				bpmPageButtons.setClick(bpmTpltButtons.getClick());
				bpmPageButtons.setType(bpmTpltButtons.getType());
				bpmPageButtons.setWidth(bpmTpltButtons.getWidth());
				bpmPageButtons.setHeight(bpmTpltButtons.getHeight());
				bpmPageButtons.setIcon(bpmTpltButtons.getIcon());
				bpmPageButtons.setDisabled(bpmTpltButtons.getDisabled());
				bpmPageButtons.setHidden(bpmTpltButtons.getHidden());
				bpmPageButtons.setStyle(bpmTpltButtons.getStyle());
				bpmPageButtons.setBtnstyle(bpmTpltButtons.getBtnstyle());

				iBpmPageButtonsService.updatePageButton(context,bpmPageButtons);
			}
		}

		BpmPageButtons queryBpmPageButtons = new BpmPageButtons();
		queryBpmPageButtons.setTemplateFlag("Y");
		queryBpmPageButtons.setPageId(pageId);
		//删除
		List<BpmPageButtons> bpmPageButtonsList = bpmPageButtonsMapper.queryByParas(queryBpmPageButtons);
		if (bpmPageButtonsList.size() == 0){
			return;
		}

		for (BpmPageButtons bpmPageButtons : bpmPageButtonsList){
			queryBpmTpltButtons = new TpltButton();
			queryBpmTpltButtons.setTemplateId(templateId);
			queryBpmTpltButtons.setButtonCode(bpmPageButtons.getButtonCode());
			bpmTpltButtonsList = bpmTpltButtonsMapper.queryByParas(queryBpmTpltButtons);

			if (bpmTpltButtonsList.size() == 0){
				queryBpmTpltButtons.setButtonId(bpmPageButtons.getButtonId());
				bpmPageButtonsMapper.deleteByPrimaryKey(queryBpmTpltButtons);
			}
		}




	}

	@Override
	public void changeBpmPageLayoutBasic(IRequest request,Long templateId,Long pageId){
		//获取BpmTpltLayoutBasic实例
		//
		TpltLayoutBasic queryBpmTpltLayoutBasic = new TpltLayoutBasic();
		queryBpmTpltLayoutBasic.setTemplateId(templateId);
		List<TpltLayoutBasic> bpmTpltLayoutBasicList = bpmTpltLayoutBasicMapper.queryByParas(queryBpmTpltLayoutBasic);
		if (bpmTpltLayoutBasicList.size() > 0){
			for (TpltLayoutBasic bpmTpltLayoutBasic : bpmTpltLayoutBasicList){
				BpmPageLayoutBasic queryBpmPageLayoutBasic = new BpmPageLayoutBasic();
				queryBpmPageLayoutBasic.setLayoutCode(bpmTpltLayoutBasic.getLayoutCode());
				queryBpmPageLayoutBasic.setPageId(pageId);
				queryBpmPageLayoutBasic.setTemplateFlag("Y");
				List<BpmPageLayoutBasic> bpmPageLayoutBasicList = bpmPageLayoutBasicMapper.queryByParas(queryBpmPageLayoutBasic);

				if (bpmPageLayoutBasicList.size() == 0){
					BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic();
					bpmPageLayoutBasic.setPageId(pageId);
					bpmPageLayoutBasic.setLayoutSequence(bpmTpltLayoutBasic.getLayoutSequence());
					bpmPageLayoutBasic.setLayoutCode(bpmTpltLayoutBasic.getLayoutCode());
					bpmPageLayoutBasic.setLayoutDesc(bpmTpltLayoutBasic.getLayoutDesc());
					bpmPageLayoutBasic.setLayoutType(bpmTpltLayoutBasic.getLayoutType());
					bpmPageLayoutBasic.setParentLayoutId(bpmTpltLayoutBasic.getParentLayoutId());
					bpmPageLayoutBasic.setLayoutLevel(bpmTpltLayoutBasic.getLayoutLevel());
					bpmPageLayoutBasic.setTemplateFlag("Y");
					bpmPageLayoutBasic.setDataset(bpmTpltLayoutBasic.getDataset());
					bpmPageLayoutBasic.setTabGroupNumber(bpmTpltLayoutBasic.getTabGroupNumber());
					bpmPageLayoutBasic.setId(bpmTpltLayoutBasic.getId());
					bpmPageLayoutBasic.setWidth(bpmTpltLayoutBasic.getWidth());
					bpmPageLayoutBasic.setHeight(bpmTpltLayoutBasic.getHeight());
					bpmPageLayoutBasic.setMarginwidth(bpmTpltLayoutBasic.getMarginwidth());
					bpmPageLayoutBasic.setMarginheight(bpmTpltLayoutBasic.getMarginheight());
					bpmPageLayoutBasic.setStyle(bpmTpltLayoutBasic.getStyle());
					bpmPageLayoutBasic.setHidden(bpmTpltLayoutBasic.getHidden());

					iBpmPageLayoutBasicService.insert(context,bpmPageLayoutBasic);

					//插入bpm_page_layout_buttons
					TpltLayoutButton queryBpmTpltLayoutButtons = new TpltLayoutButton();
					queryBpmTpltLayoutButtons.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					List<TpltLayoutButton> bpmTpltLayoutButtonsList = bpmTpltLayoutButtonsMapper.queryByParas(queryBpmTpltLayoutButtons);

					if (bpmTpltLayoutButtonsList.size() > 0){

						for (TpltLayoutButton bpmTpltLayoutButtons : bpmTpltLayoutButtonsList){
							//构造方法
							BpmPageLayoutButtons bpmPageLayoutButtons = new BpmPageLayoutButtons(bpmTpltLayoutButtons,bpmPageLayoutBasic.getLayoutId());
							iBpmPageLayoutButtonsService.insertPageLayoutButtons(context,bpmPageLayoutButtons);
						}

					}

					//插入bpm_page_layout_form
					TpltLayoutForm queryBpmTpltLayoutForm = new TpltLayoutForm();
					queryBpmTpltLayoutForm.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					List<TpltLayoutForm> bpmTpltLayoutFormList = bpmTpltLayoutFormMapper.queryByParas(queryBpmTpltLayoutForm);

					if (bpmTpltLayoutFormList.size() > 0){

						for (TpltLayoutForm bpmTpltLayoutForm : bpmTpltLayoutFormList){
							//构造方法
							BpmPageLayoutForm bpmPageLayoutForm = new BpmPageLayoutForm(bpmTpltLayoutForm,bpmPageLayoutBasic.getLayoutId());
							iBpmPageLayoutFormService.insertPageLayouForm(context,bpmPageLayoutForm);
						}

					}

					//插入bpm_page_layout_grid
					TpltLayoutGrid queryBpmTpltLayoutGrid = new TpltLayoutGrid();
					queryBpmTpltLayoutGrid.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					List<TpltLayoutGrid> bpmTpltLayoutGridList = bpmTpltLayoutGridMapper.queryByParas(queryBpmTpltLayoutGrid);

					if (bpmTpltLayoutGridList.size() > 0){

						for (TpltLayoutGrid bpmTpltLayoutGrid : bpmTpltLayoutGridList){
							//构造方法
							BpmPageLayoutGrid bpmPageLayoutGrid = new BpmPageLayoutGrid(bpmTpltLayoutGrid,bpmPageLayoutBasic.getLayoutId());
							iBpmPageLayoutGridService.insert(context,bpmPageLayoutGrid);
						}

					}

					//插入bpm_page_layout_tab
					TpltLayoutTab queryBpmTpltLayoutTab = new TpltLayoutTab();
					queryBpmTpltLayoutTab.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					List<TpltLayoutTab> bpmTpltLayoutTabList = bpmTpltLayoutTabMapper.queryByParas(queryBpmTpltLayoutTab);

					if (bpmTpltLayoutTabList.size() > 0){

						for (TpltLayoutTab bpmTpltLayoutTab : bpmTpltLayoutTabList){
							//构造方法
							BpmPageLayoutTab bpmPageLayoutTab = new BpmPageLayoutTab(bpmTpltLayoutTab,bpmPageLayoutBasic.getLayoutId());
							iBpmPageLayoutTabService.insert(context,bpmPageLayoutTab);
						}

					}

					//插入bpm_page_layout_basic
					TpltTagBasic queryBpmTpltTagsBasic = new TpltTagBasic();
					queryBpmTpltTagsBasic.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					List<TpltTagBasic> bpmTpltTagsBasicList = bpmTpltTagsBasicMapper.queryByParas(queryBpmTpltTagsBasic);

					if (bpmTpltTagsBasicList.size() > 0){
						//获取bpm_page_layout_basic实例
						bpmPageLayoutBasic = new BpmPageLayoutBasic();
						bpmPageLayoutBasic.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
						bpmPageLayoutBasic = bpmPageLayoutBasicMapper.queryByParas(bpmPageLayoutBasic).get(0);

						for (TpltTagBasic bpmTpltTagsBasic : bpmTpltTagsBasicList){
							//构造方法
							BpmPageTagsBasic bpmPageTagsBasic = new BpmPageTagsBasic(bpmTpltTagsBasic);
							bpmPageTagsBasic.setBindtarget(bpmPageLayoutBasic.getDataset());
							bpmPageTagsBasic.setLayoutId(bpmPageLayoutBasic.getLayoutId());
							Long tagId = (Long) primaryKeyProvider.getPrimaryKeyValue("select nextval('bpm_tplt_tags_basic_s')", Long.class);
							bpmPageTagsBasic.setTagId(tagId);
							//判断是否layout_id和tag_code的数据是否存在
							BpmPageTagsBasic queryBpmPageTagsBasic = new BpmPageTagsBasic();
							queryBpmPageTagsBasic.setLayoutId(bpmPageTagsBasic.getLayoutId());
							queryBpmPageTagsBasic.setTagCode(bpmPageTagsBasic.getTagCode());
							List<BpmPageTagsBasic> queryBpmPageTagsBasicList = iBpmPageTagsBasicService.select(context,queryBpmPageTagsBasic);
							if (queryBpmPageTagsBasicList.size() == 0){
								iBpmPageTagsBasicService.insertPageTagsBasic(context,bpmPageTagsBasic);

								insertBpmPageTagsAll(context,bpmPageTagsBasic.getTagId());
							}

						}

					}


				}

				if (bpmPageLayoutBasicList.size() == 1){
					queryBpmPageLayoutBasic = new BpmPageLayoutBasic();
					queryBpmPageLayoutBasic.setTemplateFlag("Y");
					queryBpmPageLayoutBasic.setLayoutCode(bpmTpltLayoutBasic.getLayoutCode());
					queryBpmPageLayoutBasic.setPageId(pageId);
					queryBpmPageLayoutBasic = bpmPageLayoutBasicMapper.queryByParas(queryBpmPageLayoutBasic).get(0);

					BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic(bpmTpltLayoutBasic);
					bpmPageLayoutBasic.setPageId(pageId);
					bpmPageLayoutBasic.setLayoutId(queryBpmPageLayoutBasic.getLayoutId());
					iBpmPageLayoutBasicService.updateByPrimaryKey(context,bpmPageLayoutBasic);

					//bpm_tplt_layout_form
					TpltLayoutForm bpmTpltLayoutForm = new TpltLayoutForm();
					bpmTpltLayoutForm.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					bpmTpltLayoutForm = bpmTpltLayoutFormMapper.queryByParas(bpmTpltLayoutForm).get(0);

					//bpm_page_layout_form
					BpmPageLayoutForm bpmPageLayoutForm = new BpmPageLayoutForm(bpmTpltLayoutForm,queryBpmPageLayoutBasic.getLayoutId());
					iBpmPageLayoutFormService.updatePageLayouForm(context,bpmPageLayoutForm);

					//bpm_tplt_layout_grid
					TpltLayoutGrid bpmTpltLayoutGrid = new TpltLayoutGrid();
					bpmTpltLayoutGrid.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					bpmTpltLayoutGrid = bpmTpltLayoutGridMapper.queryByParas(bpmTpltLayoutGrid).get(0);
					//bpm_page_layout_grid
					BpmPageLayoutGrid bpmPageLayoutGrid = new BpmPageLayoutGrid(bpmTpltLayoutGrid,queryBpmPageLayoutBasic.getLayoutId());
					iBpmPageLayoutGridService.updateByPrimaryKey(context,bpmPageLayoutGrid);

					//bpm_tplt_layout_tab
					TpltLayoutTab bpmTpltLayoutTab = new TpltLayoutTab();
					bpmTpltLayoutTab.setLayoutId(bpmTpltLayoutBasic.getLayoutId());
					bpmTpltLayoutTab = bpmTpltLayoutTabMapper.queryByParas(bpmTpltLayoutTab).get(0);
					//bpm_page_layout_tab
					BpmPageLayoutTab bpmPageLayoutTab = new BpmPageLayoutTab(bpmTpltLayoutTab,queryBpmPageLayoutBasic.getLayoutId());
					iBpmPageLayoutTabService.updateByPrimaryKey(context,bpmPageLayoutTab);

					changeBpmPageLayoutButtons(context,bpmTpltLayoutBasic.getLayoutId(),bpmPageLayoutBasic.getLayoutId());
					changeBpmPageTagsBasic(context,bpmTpltLayoutBasic.getLayoutId(),bpmPageLayoutBasic.getLayoutId());

				}
			}
		}


		BpmPageLayoutBasic queryBpmPageLayoutBasic = new BpmPageLayoutBasic();
		queryBpmPageLayoutBasic.setTemplateFlag("Y");
		queryBpmPageLayoutBasic.setPageId(pageId);
		//删除
		List<BpmPageLayoutBasic> queryBpmPageLayoutBasicList = bpmPageLayoutBasicMapper.queryByParas(queryBpmPageLayoutBasic);
		if (queryBpmPageLayoutBasicList.size() > 0){
			for (BpmPageLayoutBasic bpmPageLayoutBasic : queryBpmPageLayoutBasicList){
				queryBpmTpltLayoutBasic = new TpltLayoutBasic();
				queryBpmTpltLayoutBasic.setTemplateId(templateId);
				queryBpmTpltLayoutBasic.setLayoutCode(bpmPageLayoutBasic.getLayoutCode());
				List<TpltLayoutBasic> queryBpmTpltLayoutBasicList = bpmTpltLayoutBasicMapper.queryByParas(queryBpmTpltLayoutBasic);

				if (queryBpmTpltLayoutBasicList.size() == 0){
					delete_bpm_page_layout(context,bpmPageLayoutBasic.getLayoutId());
				}
			}
		}
	}

	public void changeBpmPageLayoutButtons(IRequest request,Long p_tplt_layout_id,Long p_page_layout_id){
		//bpm_tplt_layout_buttons
		TpltLayoutButton queryBpmTpltLayoutButtons = new TpltLayoutButton();
		queryBpmTpltLayoutButtons.setLayoutId(p_tplt_layout_id);
		List<TpltLayoutButton> queryBpmTpltLayoutButtonsList = bpmTpltLayoutButtonsMapper.queryByParas(queryBpmTpltLayoutButtons);

		if (queryBpmTpltLayoutButtonsList.size() > 0){
			for(TpltLayoutButton bpmTpltLayoutButtons : queryBpmTpltLayoutButtonsList){
				//bpm_page_layout_buttons
				BpmPageLayoutButtons querybpmPageLayoutButtons = new BpmPageLayoutButtons();
				querybpmPageLayoutButtons.setButtonCode(bpmTpltLayoutButtons.getButtonCode());
				querybpmPageLayoutButtons.setLayoutId(p_page_layout_id);
				querybpmPageLayoutButtons.setTemplateFlag("Y");

				List<BpmPageLayoutButtons> bpmPageLayoutButtonsList = bpmPageLayoutButtonsMapper.queryByParas(querybpmPageLayoutButtons);
				if (bpmPageLayoutButtonsList.size() == 0){
					BpmPageLayoutButtons bpmPageLayoutButtons = new BpmPageLayoutButtons(bpmTpltLayoutButtons,p_page_layout_id);
					iBpmPageLayoutButtonsService.insertPageLayoutButtons(context,bpmPageLayoutButtons);
				}else {
					if (bpmPageLayoutButtonsList.size() == 1){
						BpmPageLayoutButtons bpmPageLayoutButtons = new BpmPageLayoutButtons(bpmTpltLayoutButtons,p_page_layout_id);
						bpmPageLayoutButtons.setButtonId(bpmPageLayoutButtonsList.get(0).getButtonId());
						iBpmPageLayoutButtonsService.updateByPrimaryKey(context,bpmPageLayoutButtons);
					}
				}

			}
		}

		//删除
		BpmPageLayoutButtons queryBpmPageLayoutButtons = new BpmPageLayoutButtons();
		queryBpmPageLayoutButtons.setLayoutId(p_page_layout_id);
		queryBpmPageLayoutButtons.setTemplateFlag("Y");
		List<BpmPageLayoutButtons> queryBpmPageLayoutButtonsList = bpmPageLayoutButtonsMapper.queryByParas(queryBpmPageLayoutButtons);

		if (queryBpmPageLayoutButtonsList.size() > 0 ){
			for (BpmPageLayoutButtons bpmPageLayoutButtons : queryBpmPageLayoutButtonsList){
				queryBpmTpltLayoutButtons = new TpltLayoutButton();
				queryBpmTpltLayoutButtons.setLayoutId(p_tplt_layout_id);
				queryBpmTpltLayoutButtons.setButtonCode(bpmPageLayoutButtons.getButtonCode());

				queryBpmTpltLayoutButtonsList = bpmTpltLayoutButtonsMapper.queryByParas(queryBpmTpltLayoutButtons);
				if(queryBpmTpltLayoutButtonsList.size() == 0){
					iBpmPageLayoutButtonsService.deleteByPrimaryKey(bpmPageLayoutButtons);
				}
			}
		}

	}

	public void changeBpmPageTagsBasic(IRequest request,Long p_tplt_layout_id,Long p_page_layout_id){
		//bpm_page_layout_basic
		BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic();
		bpmPageLayoutBasic.setLayoutId(p_page_layout_id);
		bpmPageLayoutBasic = bpmPageLayoutBasicMapper.queryByParas(bpmPageLayoutBasic).get(0);

		//循环bpm_tplt_tags_basic
		TpltTagBasic queryBpmTpltTagsBasic = new TpltTagBasic();
		queryBpmTpltTagsBasic.setLayoutId(p_tplt_layout_id);
		List<TpltTagBasic> queryBpmTpltTagsBasicList = bpmTpltTagsBasicMapper.queryByParas(queryBpmTpltTagsBasic);

		if (queryBpmTpltTagsBasicList.size() > 0){
			for (TpltTagBasic bpmTpltTagsBasic : queryBpmTpltTagsBasicList){
				BpmPageTagsBasic bpmPageTagsBasic = new BpmPageTagsBasic();
				bpmPageTagsBasic.setLayoutId(p_page_layout_id);
				bpmPageTagsBasic.setTagCode(bpmTpltTagsBasic.getTagCode());
				bpmPageTagsBasic.setTemplateFlag("Y");
				List<BpmPageTagsBasic> bpmPageTagsBasicList = bpmPageTagsBasicMapper.queryByParas(bpmPageTagsBasic);

				if (bpmPageTagsBasicList.size() == 0){
					Long tagId = (Long)iPrimaryKeyProvider.getPrimaryKeyValue("select nextval('bpm_page_tags_basic_s')",Long.class);
					bpmPageTagsBasic = new BpmPageTagsBasic(bpmTpltTagsBasic);
					bpmPageTagsBasic.setBindtarget(bpmPageLayoutBasic.getDataset());
					bpmPageTagsBasic.setLayoutId(p_page_layout_id);
					bpmPageTagsBasic.setTagId(tagId);
					iBpmPageTagsBasicService.insertPageTagsBasic(context,bpmPageTagsBasic);
					insertBpmPageTagsAll(context,bpmPageTagsBasic.getTagId());
				}else {
					if (bpmPageTagsBasicList.size() == 1){
						bpmPageTagsBasic = new BpmPageTagsBasic(bpmTpltTagsBasic);
						bpmPageTagsBasic.setBindtarget(bpmPageLayoutBasic.getDataset());
						bpmPageTagsBasic.setLayoutId(p_page_layout_id);
						bpmPageTagsBasic.setTagId(bpmPageTagsBasicList.get(0).getTagId());

						iBpmPageTagsBasicService.updateByPrimaryKey(context,bpmPageTagsBasic);
					}
				}
			}
		}

		BpmPageTagsBasic bpmPageTagsBasic = new BpmPageTagsBasic();
		bpmPageTagsBasic.setLayoutId(p_page_layout_id);
		bpmPageTagsBasic.setTemplateFlag("Y");
		List<BpmPageTagsBasic> bpmPageTagsBasicList = bpmPageTagsBasicMapper.queryByParas(bpmPageTagsBasic);

		if (bpmPageTagsBasicList.size() > 0){
			for (BpmPageTagsBasic bpmPageTagsBasics : bpmPageTagsBasicList){
				TpltTagBasic bpmTpltTagsBasic = new TpltTagBasic();
				bpmTpltTagsBasic.setLayoutId(p_page_layout_id);
				bpmTpltTagsBasic.setTagCode(bpmPageTagsBasics.getTagCode());
				List<TpltTagBasic> bpmTpltTagsBasicList = bpmTpltTagsBasicMapper.queryByParas(bpmTpltTagsBasic);

				if (bpmTpltTagsBasicList.size() == 0){
					delete_bpm_p_t_basic_directly(context,bpmPageTagsBasics.getTagId());
				}
			}
		}

	}

	public void delete_bpm_page_layout(IRequest request,Long p_layout_id){
		//bpm_page_tags_basic
		BpmPageTagsBasic queryBpmPageTagsBasic = new BpmPageTagsBasic();
		queryBpmPageTagsBasic.setLayoutId(p_layout_id);
		List<BpmPageTagsBasic> queryBpmPageTagsBasicList = bpmPageTagsBasicMapper.queryByParas(queryBpmPageTagsBasic);

		if (queryBpmPageTagsBasicList.size() > 0){
			for (BpmPageTagsBasic bpmPageTagsBasic : queryBpmPageTagsBasicList){
				delete_bpm_p_t_basic_directly(context,bpmPageTagsBasic.getTagId());
			}
		}

		//删除BPM动态页面布局组件基本信息表
		BpmPageLayoutBasic bpmPageLayoutBasic = new BpmPageLayoutBasic();
		bpmPageLayoutBasic.setLayoutId(p_layout_id);
		iBpmPageLayoutBasicService.deleteByPrimaryKey(bpmPageLayoutBasic);

		//删除BPM页面布局组件按钮
		BpmPageLayoutButtons queryBpmPageLayoutButtons = new BpmPageLayoutButtons();
		queryBpmPageLayoutButtons.setLayoutId(p_layout_id);
		List<BpmPageLayoutButtons> queryBpmPageLayoutButtonsList = bpmPageLayoutButtonsMapper.queryByParas(queryBpmPageLayoutButtons);
		if (queryBpmPageLayoutButtonsList.size() > 0){
			for (BpmPageLayoutButtons bpmPageLayoutButtons : queryBpmPageLayoutButtonsList){
				iBpmPageLayoutButtonsService.deleteByPrimaryKey(bpmPageLayoutButtons);
			}
		}

		//删除布局组件事件定义
		BpmPageLayoutEvents queryBpmPageLayoutEvents = new BpmPageLayoutEvents();
		queryBpmPageLayoutEvents.setLayoutId(p_layout_id);
		List<BpmPageLayoutEvents> queryBpmPageLayoutEventsList = bpmPageLayoutEventsMapper.queryByParas(queryBpmPageLayoutEvents);
		if(queryBpmPageLayoutEventsList.size() > 0){
			for (BpmPageLayoutEvents bpmPageLayoutEvents : queryBpmPageLayoutEventsList){
				iBpmPageLayoutEventsService.deleteByPrimaryKey(bpmPageLayoutEvents);
			}
		}

		//删除BPM动态页面布局组件表单类型属性表
		BpmPageLayoutForm queryBpmPageLayoutForm = new BpmPageLayoutForm();
		queryBpmPageLayoutForm.setLayoutId(p_layout_id);
		List<BpmPageLayoutForm> queryBpmPageLayoutFormList = bpmPageLayoutFormMapper.queryByParas(queryBpmPageLayoutForm);
		if (queryBpmPageLayoutFormList.size() > 0){
			for (BpmPageLayoutForm bpmPageLayoutForm : queryBpmPageLayoutFormList){
				iBpmPageLayoutFormService.deleteByPrimaryKey(bpmPageLayoutForm);
			}
		}

		//删除BPM动态页面布局组件列表类型属性表
		BpmPageLayoutGrid queryBpmPageLayoutGrid = new BpmPageLayoutGrid();
		queryBpmPageLayoutGrid.setLayoutId(p_layout_id);
		List<BpmPageLayoutGrid> queryBpmPageLayoutGridList = bpmPageLayoutGridMapper.queryByParas(queryBpmPageLayoutGrid);

		if (queryBpmPageLayoutGridList.size() > 0){
			for (BpmPageLayoutGrid bpmPageLayoutGrid : queryBpmPageLayoutGridList){
				iBpmPageLayoutGridService.deleteByPrimaryKey(bpmPageLayoutGrid);
			}
		}

		//删除BPM动态页面布局组件Tab页类型属性表
		BpmPageLayoutTab queryBpmPageLayoutTab = new BpmPageLayoutTab();
		queryBpmPageLayoutTab.setLayoutId(p_layout_id);
		List<BpmPageLayoutTab> queryBpmPageLayoutTabList = bpmPageLayoutTabMapper.queryByParas(queryBpmPageLayoutTab);
		if (queryBpmPageLayoutTabList.size() > 0){
			for (BpmPageLayoutTab bpmPageLayoutTab : queryBpmPageLayoutTabList){
				iBpmPageLayoutTabService.deleteByPrimaryKey(bpmPageLayoutTab);
			}
		}
	}

	public void insertBpmPageTagsAll(IRequest request,Long p_tag_id){
		BpmPageTagsCombobox bpmPageTagsCombobox = new BpmPageTagsCombobox();
		bpmPageTagsCombobox.setTagId(p_tag_id);
		iBpmPageTagsComboboxService.insert(context,bpmPageTagsCombobox);

		BpmPageTagsCheckbox bpmPageTagsCheckbox = new BpmPageTagsCheckbox();
		bpmPageTagsCheckbox.setTagId(p_tag_id);
		iBpmPageTagsCheckboxService.insert(context,bpmPageTagsCheckbox);

		BpmPageTagsDatepicker bpmPageTagsDatepicker = new BpmPageTagsDatepicker();
		bpmPageTagsDatepicker.setTagId(p_tag_id);
		iBpmPageTagsDatepickerService.insert(context,bpmPageTagsDatepicker);

		BpmPageTagsDatetimepicker bpmPageTagsDatetimepicker = new BpmPageTagsDatetimepicker();
		bpmPageTagsDatetimepicker.setTagId(p_tag_id);
		iBpmPageTagsDatetimepickerService.insert(context,bpmPageTagsDatetimepicker);

		BpmPageTagsLabel bpmPageTagsLabel = new BpmPageTagsLabel();
		bpmPageTagsLabel.setTagId(p_tag_id);
		iBpmPageTagsLabelService.insert(context,bpmPageTagsLabel);

		BpmPageTagsLov bpmPageTagsLov = new BpmPageTagsLov();
		bpmPageTagsLov.setTagId(p_tag_id);
		iBpmPageTagsLovService.insert(context,bpmPageTagsLov);

		BpmPageTagsNumberfield bpmPageTagsNumberfield = new BpmPageTagsNumberfield();
		bpmPageTagsNumberfield.setTagId(p_tag_id);
		iBpmPageTagsNumberfieldService.insert(context,bpmPageTagsNumberfield);

		BpmPageTagsRadio bpmPageTagsRadio = new BpmPageTagsRadio();
		bpmPageTagsRadio.setTagId(p_tag_id);
		iBpmPageTagsRadioService.insert(context,bpmPageTagsRadio);

		BpmPageTagsTextfield bpmPageTagsTextfield = new BpmPageTagsTextfield();
		bpmPageTagsTextfield.setTagId(p_tag_id);
		iBpmPageTagsTextfieldService.insert(context,bpmPageTagsTextfield);

		BpmPageTagsTree bpmPageTagsTree = new BpmPageTagsTree();
		bpmPageTagsTree.setTagId(p_tag_id);
		iBpmPageTagsTreeService.insert(context,bpmPageTagsTree);

		BpmPageTagsTreegrid bpmPageTagsTreegrid = new BpmPageTagsTreegrid();
		bpmPageTagsTreegrid.setTagId(p_tag_id);
		iBpmPageTagsTreegridService.insert(context,bpmPageTagsTreegrid);

	}

	public void delete_bpm_p_t_basic_directly(IRequest request,Long p_tag_id){
		//删除BPM页面标签基本信息表
		BpmPageTagsBasic bpmPageTagsBasic = new BpmPageTagsBasic();
		bpmPageTagsBasic.setTagId(p_tag_id);
		iBpmPageTagsBasicService.deleteByPrimaryKey(bpmPageTagsBasic);
		//删除BPM页面标签checkBox特殊属性
		BpmPageTagsCheckbox bpmPageTagsCheckbox = new BpmPageTagsCheckbox();
		bpmPageTagsCheckbox.setTagId(p_tag_id);
		iBpmPageTagsCheckboxService.deleteByPrimaryKey(bpmPageTagsCheckbox);
		//删除BPM页面标签combobox字段表
		BpmPageTagsComboboxFields queryBpmPageTagsComboboxFields = new BpmPageTagsComboboxFields();
		queryBpmPageTagsComboboxFields.setTagId(p_tag_id);
		List<BpmPageTagsComboboxFields> queryBpmPageTagsComboboxFieldsList = bpmPageTagsComboboxFieldsMapper.queryByParas(queryBpmPageTagsComboboxFields);
		if (queryBpmPageTagsComboboxFieldsList.size() > 0){
			for (BpmPageTagsComboboxFields bpmPageTagsComboboxFields : queryBpmPageTagsComboboxFieldsList){
				iBpmPageTagsComboboxFieldsService.deleteByPrimaryKey(bpmPageTagsComboboxFields);
			}
		}
		//删除BPM页面标签comboBox映射关系表
		BpmPageTagsComboboxMappers queryBpmPageTagsComboboxMappers = new BpmPageTagsComboboxMappers();
		queryBpmPageTagsComboboxMappers.setTagId(p_tag_id);
		List<BpmPageTagsComboboxMappers> queryBpmPageTagsComboboxMappersList = bpmPageTagsComboboxMappersMapper.queryByParas(queryBpmPageTagsComboboxMappers);
		if(queryBpmPageTagsComboboxMappersList.size() > 0){
			for (BpmPageTagsComboboxMappers bpmPageTagsComboboxMappers : queryBpmPageTagsComboboxMappersList){
				iBpmPageTagsComboboxMappersService.deleteByPrimaryKey(bpmPageTagsComboboxMappers);
			}
		}

		//删除BPM页面标签combobox选项表
		BpmPageTagsComboboxOptions queryBpmPageTagsComboboxOptions = new BpmPageTagsComboboxOptions();
		queryBpmPageTagsComboboxOptions.setTagId(p_tag_id);
		List<BpmPageTagsComboboxOptions> queryBpmPageTagsComboboxOptionsList = bpmPageTagsComboboxOptionsMapper.queryByParas(queryBpmPageTagsComboboxOptions);
		if(queryBpmPageTagsComboboxOptionsList.size() > 0){
			for (BpmPageTagsComboboxOptions bpmPageTagsComboboxOptions : queryBpmPageTagsComboboxOptionsList){
				iBpmPageTagsComboboxOptionsService.deleteByPrimaryKey(bpmPageTagsComboboxOptions);
			}
		}

		//删除BPM页面标签comobox特殊属性
		BpmPageTagsCombobox bpmPageTagsCombobox = new BpmPageTagsCombobox();
		bpmPageTagsCombobox.setTagId(p_tag_id);
		iBpmPageTagsComboboxService.deleteByPrimaryKey(bpmPageTagsCombobox);

		//删除BPM页面标签DatePicker特殊属性
		BpmPageTagsDatepicker bpmPageTagsDatepicker = new BpmPageTagsDatepicker();
		bpmPageTagsDatepicker.setTagId(p_tag_id);
		iBpmPageTagsDatepickerService.deleteByPrimaryKey(bpmPageTagsDatepicker);

		//删除BPM页面标签DateTimePicker特殊属性
		BpmPageTagsDatetimepicker bpmPageTagsDatetimepicker = new BpmPageTagsDatetimepicker();
		bpmPageTagsDatetimepicker.setTagId(p_tag_id);
		iBpmPageTagsDatetimepickerService.deleteByPrimaryKey(bpmPageTagsDatetimepicker);

		//删除BPM页面标签label特殊属性
		BpmPageTagsLabel bpmPageTagsLabel = new BpmPageTagsLabel();
		bpmPageTagsLabel.setTagId(p_tag_id);
		iBpmPageTagsLabelService.deleteByPrimaryKey(bpmPageTagsLabel);

		//删除BPM页面标签lov特殊属性
		BpmPageTagsLov bpmPageTagsLov = new BpmPageTagsLov();
		bpmPageTagsLov.setTagId(p_tag_id);
		iBpmPageTagsLovService.deleteByPrimaryKey(bpmPageTagsLov);

		//删除BPM页面标签lov字段表
		BpmPageTagsLovFields queryBpmPageTagsLovFields = new BpmPageTagsLovFields();
		queryBpmPageTagsLovFields.setTagId(p_tag_id);
		List<BpmPageTagsLovFields> queryBpmPageTagsLovFieldsList = bpmPageTagsLovFieldsMapper.queryByParas(queryBpmPageTagsLovFields);
		if(queryBpmPageTagsLovFieldsList.size() > 0){
			for (BpmPageTagsLovFields bpmPageTagsLovFields : queryBpmPageTagsLovFieldsList){
				iBpmPageTagsLovFieldsService.deleteByPrimaryKey(bpmPageTagsLovFields);
			}
		}

		//删除BPM页面标签lov映射关系表
		BpmPageTagsLovMappers queryBpmPageTagsLovMappers = new BpmPageTagsLovMappers();
		queryBpmPageTagsLovMappers.setTagId(p_tag_id);
		List<BpmPageTagsLovMappers> queryBpmPageTagsLovMappersList = bpmPageTagsLovMappersMapper.queryByParas(queryBpmPageTagsLovMappers);
		if(queryBpmPageTagsLovMappersList.size() > 0){
			for (BpmPageTagsLovMappers bpmPageTagsLovMappers : queryBpmPageTagsLovMappersList){
				iBpmPageTagsLovMappersService.deleteByPrimaryKey(bpmPageTagsLovMappers);
			}
		}

		//删除BPM页面标签numberfield特殊属性
		BpmPageTagsNumberfield bpmPageTagsNumberfield = new BpmPageTagsNumberfield();
		bpmPageTagsNumberfield.setTagId(p_tag_id);
		iBpmPageTagsNumberfieldService.deleteByPrimaryKey(bpmPageTagsNumberfield);

		//删除BPM页面标签radio特殊属性
		BpmPageTagsRadio bpmPageTagsRadio = new BpmPageTagsRadio();
		bpmPageTagsRadio.setTagId(p_tag_id);
		iBpmPageTagsRadioService.deleteByPrimaryKey(bpmPageTagsRadio);

		//删除BPM页面标签radio选项表
		BpmPageTagsRadioOptions queryBpmPageTagsRadioOptions = new BpmPageTagsRadioOptions();
		queryBpmPageTagsRadioOptions.setTagId(p_tag_id);
		List<BpmPageTagsRadioOptions> queryBpmPageTagsRadioOptionsList = bpmPageTagsRadioOptionsMapper.queryByParas(queryBpmPageTagsRadioOptions);
		if(queryBpmPageTagsRadioOptionsList.size() > 0){
			for (BpmPageTagsRadioOptions bpmPageTagsRadioOptions : queryBpmPageTagsRadioOptionsList){
				iBpmPageTagsRadioOptionsService.deleteByPrimaryKey(bpmPageTagsRadioOptions);
			}
		}

		//删除BPM页面标签TextField特殊属性
		BpmPageTagsTextfield bpmPageTagsTextfield = new BpmPageTagsTextfield();
		bpmPageTagsTextfield.setTagId(p_tag_id);
		iBpmPageTagsTextfieldService.deleteByPrimaryKey(bpmPageTagsTextfield);

		//删除BPM页面标tree特殊属性
		BpmPageTagsTree bpmPageTagsTree = new BpmPageTagsTree();
		bpmPageTagsTree.setTagId(p_tag_id);
		iBpmPageTagsTreeService.deleteByPrimaryKey(bpmPageTagsTree);

		//删除BPM页面标treegrid特殊属性
		BpmPageTagsTreegrid bpmPageTagsTreegrid = new BpmPageTagsTreegrid();
		bpmPageTagsTreegrid.setTagId(p_tag_id);
		iBpmPageTagsTreegridService.deleteByPrimaryKey(bpmPageTagsTreegrid);

		//删除BPM动态页面标签数据向导
		BpmPageTagDataGuide	queryBpmPageTagDataGuide = new BpmPageTagDataGuide();
		queryBpmPageTagDataGuide.setTagId(p_tag_id);
		List<BpmPageTagDataGuide> queryBpmPageTagDataGuideList = bpmPageTagDataGuideMapper.queryByParas(queryBpmPageTagDataGuide);
		if (queryBpmPageTagDataGuideList.size() > 0){
			for (BpmPageTagDataGuide bpmPageTagDataGuide : queryBpmPageTagDataGuideList){
				iBpmPageTagDataGuideService.deleteByPrimaryKey(bpmPageTagDataGuide);
			}
		}

		//删除标签事件定义
		BpmPageTagEvents queryBpmPageTagEvents = new BpmPageTagEvents();
		queryBpmPageTagEvents.setTagId(p_tag_id);
		List<BpmPageTagEvents> queryBpmPageTagEventsList = bpmPageTagEventsMapper.queryByParas(queryBpmPageTagEvents);
		if (queryBpmPageTagEventsList.size() > 0){
			for (BpmPageTagEvents bpmPageTagEvents : queryBpmPageTagEventsList){
				iBpmPageTagEventsService.deleteByPrimaryKey(bpmPageTagEvents);
			}
		}
	}
*/
}
