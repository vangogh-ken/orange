package com.van.halley.bpm.config.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.util.StringUtil;
import com.van.service.BasisApplicationService;
import com.van.service.BasisSubstanceTypeService;
import com.van.service.BpmConfigBasisService;
import com.van.service.BpmConfigCategoryService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConfigBasisController {
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private BasisApplicationService basisApplicationService;
	@Autowired
	private BasisSubstanceTypeService basisSubstanceTypeService;
	@Autowired
	private BpmConfigCategoryService bpmConfigCategoryService;
	@Autowired
	private RepositoryQueryService repositoryQueryServiceImpl;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-config-basis-list")
	public String queryModel(Model model, BpmConfigBasis bpmConfigBasis, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		bpmConfigBasisService.query(pageView, bpmConfigBasis);
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-config-basis-list";
	}

	@RequestMapping(value = "bpm-config-basis-input")
	public String input(Model model, String id) {
		BpmConfigBasis item = null;
		if(id != null){
			item = bpmConfigBasisService.getById(id);
		}
		model.addAttribute("categories", bpmConfigCategoryService.getAll());
		model.addAttribute("basisSubstanceTypes", basisSubstanceTypeService.getAll());
		model.addAttribute("basisApplications", basisApplicationService.getAll());
		model.addAttribute("keys", repositoryQueryServiceImpl.getProcessKeys());
		model.addAttribute("item", item);
		return "/content/bpm/bpm-config-basis-input";
	}

	@RequestMapping(value = "bpm-config-basis-save")
	public String addModel(BpmConfigBasis bpmConfigBasis, String categoryId, String basisSubstanceTypeId, String basisApplicationId) {
		bpmConfigBasis.setBpmConfigCategory(bpmConfigCategoryService.getById(categoryId));
		bpmConfigBasis.setBasisSubstanceType(basisSubstanceTypeService.getById(basisSubstanceTypeId));
		bpmConfigBasis.setBasisApplication(basisApplicationService.getById(basisApplicationId));
		
		if(bpmConfigBasis.getId() != null){
			bpmConfigBasisService.modify(bpmConfigBasis);
		}else{
			bpmConfigBasisService.add(bpmConfigBasis);
		}
		
		return "redirect: bpm-config-basis-list.do";
	}

	@RequestMapping(value = "bpm-config-basis-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmConfigBasisService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-config-basis-list.do";
	}
	
	//////////////////////////流程权限分配/////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value = "bpm-config-basis-all")
	public Map<String, Object> roleBpmAllacation(Model model, String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmAll", bpmConfigBasisService.getAll());
		map.put("bpmRole", bpmConfigBasisService.getByRoleId(roleId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-basis-check-bpmkey")
	public boolean checkName(@RequestParam(required=true, value="bpmKey") String bpmKey, String id) {
		if(!StringUtil.isNullOrEmpty(bpmKey)){
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_BASIS WHERE BPM_KEY = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, bpmKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_BASIS WHERE BPM_KEY = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, bpmKey);
			return count == 0;
		}
	}
	
}
