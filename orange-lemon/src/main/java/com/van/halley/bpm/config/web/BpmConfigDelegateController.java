package com.van.halley.bpm.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigDelegate;
import com.van.halley.util.StringUtil;
import com.van.service.BpmConfigBasisService;
import com.van.service.BpmConfigDelegateService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConfigDelegateController {
	@Autowired
	private BpmConfigDelegateService bpmConfigDelegateService;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "bpm-config-delegate-list")
	public String queryModel(Model model, BpmConfigDelegate bpmConfigDelegate, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		bpmConfigDelegateService.query(pageView, bpmConfigDelegate);
		
		/*Map<String, BpmConfigBasis> bpmMapByKey = new HashMap<String, BpmConfigBasis>();
		List<BpmConfigBasis> BpmConfigBasises = bpmConfigBasisService.getAll();
		for(BpmConfigBasis bpmConfigBasis: BpmConfigBasises){
			bpmMapByKey.put(bpmConfigBasis.getBpmKey(), bpmConfigBasis);
		}
		model.addAttribute("bpmMapByKey", bpmMapByKey);*/
		model.addAttribute("bpmConfigBasises", bpmConfigBasisService.getAll());
		model.addAttribute("users", userService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/bpm/bpm-config-delegate-list";
	}

	@RequestMapping(value = "bpm-config-delegate-input")
	public String input(Model model, String id) {
		BpmConfigDelegate item = null;
		if(id != null){
			item = bpmConfigDelegateService.getById(id);
		}
		
		model.addAttribute("bpmConfigBasises", bpmConfigBasisService.getAll());
		model.addAttribute("users", userService.getAll());
		model.addAttribute("item", item);
		return "/content/bpm/bpm-config-delegate-input";
	}

	@RequestMapping(value = "bpm-config-delegate-save")
	public String addModel(BpmConfigDelegate bpmConfigDelegate, String bpmConfigBasisId, String taskAssigneeId, String taskAgentId) {
		bpmConfigDelegate.setBpmConfigBasis(bpmConfigBasisService.getById(bpmConfigBasisId));
		bpmConfigDelegate.setTaskAssignee(userService.getById(taskAssigneeId));
		bpmConfigDelegate.setTaskAgent(userService.getById(taskAgentId));
		
		if(bpmConfigDelegate.getId() != null){
			bpmConfigDelegateService.modify(bpmConfigDelegate);
		}else{
			bpmConfigDelegateService.add(bpmConfigDelegate);
		}
		
		return "redirect: bpm-config-delegate-list.do";
	}

	@RequestMapping(value = "bpm-config-delegate-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmConfigDelegateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-config-delegate-list.do";
	}
	
	/**
	 * 检查是否有重复的代理
	 */
	/*@ResponseBody
	@RequestMapping(value = "bpm-check-delegate")
	public boolean checkDelegate(@RequestParam(required=true, value="userId") String userId,
			@RequestParam(required=true, value="bpmKey") String bpmKey, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_DELEGATE WHERE USER_ID = ? AND BPM_KEY=? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, bpmKey, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_DELEGATE WHERE USER_ID = ? AND BPM_KEY=?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, bpmKey);
			return count == 0;
		}
	}*/
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-delegate-check-configbasisid")
	public boolean checkName(@RequestParam(required=true, value="bpmConfigBasisId") String bpmConfigBasisId, 
			@RequestParam(required=true, value="taskAssigneeId") String taskAssigneeId, String id) {
		if(!StringUtil.isNullOrEmpty(id)){
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_DELEGATE WHERE BPM_CONF_BASIS_ID = ? AND TASK_ASSIGNEE_ID = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, bpmConfigBasisId, taskAssigneeId, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM BPM_CONF_DELEGATE WHERE BPM_CONF_BASIS_ID = ? AND TASK_ASSIGNEE_ID = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, bpmConfigBasisId, taskAssigneeId);
			return count == 0;
		}
	}

}
