package com.van.halley.rep.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ReportConfig;
import com.van.service.ReportConfigService;

@Controller
@RequestMapping(value = "/report/")
public class ReportCenterController {
	@Autowired
	private ReportConfigService reportConfigService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;


	@RequestMapping(value = "report-view")
	public String input(Model model, @RequestParam(required=true, value="id") String id) {
		ReportConfig reportConfig = reportConfigService.getById(id);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(reportConfig.getDataSql());
		model.addAttribute("list", list);
        model.addAttribute("type", reportConfig.getType());
        model.addAttribute("start", reportConfig.getStart());
        model.addAttribute("end", reportConfig.getEnd());
        model.addAttribute("step", reportConfig.getStep());
        model.addAttribute("unit", reportConfig.getUnit());
        
        model.addAttribute("title", reportConfig.getTitle());
        model.addAttribute("width", reportConfig.getWidth());
        model.addAttribute("height", reportConfig.getHeight());
		return "/content/report/comm-percent-report";
	}

}
