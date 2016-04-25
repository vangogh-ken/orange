package com.van.halley.out.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ServerInfo;
import com.van.halley.db.persistence.entity.ServerStatus;
import com.van.halley.util.Common;
import com.van.halley.util.PropertiesUtils;
import com.van.service.ServerInfoService;

/**
 * 服务器监控的处理 可以收集的信息包括： 1，
 * CPU信息，包括基本信息（vendor、model、mhz、cacheSize）和统计信息（user、sys、idle 、nice、wait） 2，
 * 文件系统信息，包括Filesystem、Size、Used、Avail、Use%、Type 3， 事件信息，类似Service Control
 * Manager 4， 内存信息，物理内存和交换内存的总数、使用数、剩余数；RAM的大小 5， 网络信息，包括网络接口信息和网络路由信息 6，
 * 进程信息，包括每个进程的内存、CPU占用数、状态、参数、句柄 7， IO信息，包括IO的状态，读写大小等 8， 服务状态信息 9，
 * 系统信息，包括操作系统版本，系统资源限制情况，系统运行时间以及负载，JAVA的版本信息等.
 * 
 */
@Controller
@RequestMapping(value = "/server-info/")
public class ServerInfoController {
	@Autowired
	private ServerInfoService serverInfoService;

	@RequestMapping(value = "server-info-list")
	public String queryUserLogin(Model model, ServerInfo serverInfo,
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		serverInfoService.query(pageView, serverInfo);
		model.addAttribute("pageView", pageView);
		return "/content/server-info/server-info-list";
	}

	@RequestMapping(value = "show")
	public String show() {
		return "/content/server-info/server-info-show";
	}

	@RequestMapping(value = "forecast")
	public String forecast() {
		return "/content/server-info/server-info-forecast";
	}

	/**
	 * 获取服务器基本信息
	 * 
	 * @return
	 * @throws Exception
	 * @ResponseBody
	 * @RequestMapping(value = "info") public Map<String, Object>
	 *                       serverBaseInfo() throws Exception { Map<String,
	 *                       Object> dataMap = new HashMap<String, Object>();
	 *                       ServerStatus status = Common.getServerStatus();
	 *                       System.out.println("数据获取成功" +
	 *                       status.getJvmFreeMem());
	 * 
	 *                       dataMap.put("data", status); return dataMap; }
	 */
	@ResponseBody
	@RequestMapping(value = "info")
	public String serverBaseInfo() throws Exception {
		ServerStatus status = Common.getServerStatus();
		JSONObject j = new JSONObject(status);
		// System.out.println(j.toString());
		return j.toString();
	}

	/**
	 * 预警监控信息
	 * 
	 * @return
	 * @throws Exception
	 * @ResponseBody
	 * @RequestMapping("/warnInfo") public Map<String, Object>
	 *                              warnInfo(HttpServletRequest request) throws
	 *                              Exception { ServerStatus status =
	 *                              Common.getServerStatus(); Map<String,
	 *                              Object> dataMap = new HashMap<String,
	 *                              Object>();
	 * 
	 *                              String cpuUsage = status.getCpuUsage(); long
	 *                              FreeMem = status.getFreeMem(); long useMem =
	 *                              status.getUsedMem(); long TotalMem =
	 *                              status.getTotalMem(); String serverUsage =
	 *                              Common.fromUsage(useMem, TotalMem);
	 *                              dataMap.put("cpuUsage", cpuUsage);
	 *                              dataMap.put("FreeMem", FreeMem);
	 *                              dataMap.put("TotalMem", TotalMem);
	 *                              dataMap.put("serverUsage", serverUsage);
	 *                              long JvmFreeMem = status.getJvmFreeMem();
	 *                              long JvmTotalMem = status.getJvmTotalMem();
	 *                              String JvmUsage =
	 *                              Common.fromUsage(JvmTotalMem -
	 *                              JvmFreeMem,JvmTotalMem);
	 *                              dataMap.put("JvmFreeMem", JvmFreeMem);
	 *                              dataMap.put("JvmTotalMem", JvmTotalMem);
	 *                              dataMap.put("JvmUsage", JvmUsage);
	 *                              dataMap.put("cpu",
	 *                              PropertiesUtils.findPropertiesKey("cpu"));
	 *                              dataMap.put("jvm",
	 *                              PropertiesUtils.findPropertiesKey("jvm"));
	 *                              dataMap.put("ram",
	 *                              PropertiesUtils.findPropertiesKey("ram"));
	 *                              dataMap.put("toEmail",
	 *                              PropertiesUtils.findPropertiesKey
	 *                              ("toEmail")); dataMap.put("diskInfos",
	 *                              status.getDiskInfos()); return dataMap; }
	 */
	@ResponseBody
	@RequestMapping("/warnInfo")
	public String warnInfo(HttpServletRequest request) throws Exception {
		ServerStatus status = Common.getServerStatus();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		String cpuUsage = status.getCpuUsage();
		long FreeMem = status.getFreeMem();
		long useMem = status.getUsedMem();
		long TotalMem = status.getTotalMem();
		String serverUsage = Common.fromUsage(useMem, TotalMem);
		dataMap.put("cpuUsage", cpuUsage);
		dataMap.put("FreeMem", FreeMem);
		dataMap.put("TotalMem", TotalMem);
		dataMap.put("serverUsage", serverUsage);
		long JvmFreeMem = status.getJvmFreeMem();
		long JvmTotalMem = status.getJvmTotalMem();
		String JvmUsage = Common.fromUsage(JvmTotalMem - JvmFreeMem,
				JvmTotalMem);
		dataMap.put("JvmFreeMem", JvmFreeMem);
		dataMap.put("JvmTotalMem", JvmTotalMem);
		dataMap.put("JvmUsage", JvmUsage);
		dataMap.put("cpu", PropertiesUtils.findPropertiesKey("cpu"));
		dataMap.put("jvm", PropertiesUtils.findPropertiesKey("jvm"));
		dataMap.put("ram", PropertiesUtils.findPropertiesKey("ram"));
		dataMap.put("toEmail", PropertiesUtils.findPropertiesKey("toEmail"));
		dataMap.put("diskInfos", status.getDiskInfos());

		JSONObject j = new JSONObject(dataMap);
		return j.toString();
	}

	/**
	 * 修改配置　
	 * 
	 * @param request
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/modifySer")
	public Map<String, Object> modifySer(HttpServletRequest request,
			String key, String value) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 从输入流中读取属性列表（键和元素对）
			PropertiesUtils.modifyProperties(key, value);
		} catch (Exception e) {
			dataMap.put("flag", false);
		}
		dataMap.put("flag", true);
		return dataMap;
	}

	public static void main(String[] args) {
		// new ServerInfoController().getServerBaseInfo(new ServerStatus());
	}
}
