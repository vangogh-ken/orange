package com.van.halley.common.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.util.swf.ConvertSwfUtil;

@Controller
@RequestMapping("/base/")
public class OnlineViewController {
	@ResponseBody
	@RequestMapping("done-online-view")
	public String convertToSwf(@RequestParam(value="sourceFileName", required=true)String sourceFileName){
		return ConvertSwfUtil.convert(sourceFileName);
	}
	
	@RequestMapping("to-online-view")
	public String onlineView(@RequestParam(value="sourceFileName", required=true)String sourceFileName, Model model){
		model.addAttribute("sourceFileName", sourceFileName);
		return "/alpha-pre/alpha-preview";
	}
}
