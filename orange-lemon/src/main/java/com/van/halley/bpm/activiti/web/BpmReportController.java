package com.van.halley.bpm.activiti.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report/")
public class BpmReportController {
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("bpm-active-process")
    public String mostActiveProcess(Model model) {
        String sql = "select pd.name_ as name,count(pd.name_) as percent"
                + " from act_hi_procinst pi,act_re_procdef pd where pi.proc_def_id_ =pd.id_ group by pd.name_";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        model.addAttribute("list", list);
        model.addAttribute("type", "donut");
        model.addAttribute("title", "BPM流程活跃度统计信息");
        return "/content/report/comm-percent-report";
    }

    // ~ ======================================================================
    @Resource
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
