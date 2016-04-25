package com.zen.junit.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.van.halley.wx.cp.handler.ProxyEventHandler;
import com.van.halley.wx.cp.handler.WX;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpUser;

public class WXTest {
	public void t(){
		System.out.println("".equals(WX.Event.CLICK));
	}
	
	@Test
	public void init(){
		WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
		config.setCorpId("wx4fc55dd7fe73d007");
		config.setCorpSecret("oNKP7uDCdhM-vSrMyBRIG7sjrkK2IzeN9GmWqyGGQSXUQ_xetARZMK7yYPXjcKzd");
		config.setToken("VANBPMRPTEAM");
		config.setAesKey("dL75VumRBysIoSpwvAGkroEOSUp9JgT1geGdrXJjfnQ");
		
		WxCpService wxCpService = new WxCpServiceImpl();
		wxCpService.setWxCpConfigStorage(config);
		WxCpMessageRouter wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
		
		
		//try {
			/*WxMenu wxMenu = new WxMenu();
			
			WxMenu.WxMenuButton button = new WxMenu.WxMenuButton();
			button.setName("菜单一");
			button.setKey("M1");
			List<WxMenu.WxMenuButton> buttons = new ArrayList<WxMenu.WxMenuButton>();
			buttons.add(button);
			wxMenu.setButtons(buttons);
			
			wxCpService.menuCreate(wxMenu);*/
			
			//WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId("3").toUser("@all").toParty("@all").toTag("@all").content("Welcome to VANBPMRPTEAM，中文消息测试").safe("0").build();
			//WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId("4").toUser("vangogh_ken").content("Welcome to VANBPMRPTEAM").safe("0").build();
			//System.out.println(wxCpMessage.toJson());
			//wxCpService.messageSend(wxCpMessage);
			//WxCpUser user = wxCpService.userGet("Vangogh_ken");
			//System.out.println(user.getUserId());
			//System.out.println(user.getWeiXinId());
			
			//List<WxCpDepart> list = wxCpService.departGet();
			//System.out.println(list.size());
			
			//List<WxCpUser> WxCpUsers = wxCpService.userList(2, true, 0);
			//for(WxCpUser u : WxCpUsers){
				//System.out.println(1);
			//}
			/**
			WxCpUser wxCpUser = new WxCpUser();
			wxCpUser.setName("彭");
			wxCpUser.setUserId("pdxxlingyu");
			wxCpUser.setWeiXinId("pdxxlingyu");
			wxCpUser.setEmail("anxinxx@live.com");
			wxCpUser.setDepartIds(new Integer[]{2});
			wxCpUser.setEnable(1);
			wxCpService.userCreate(wxCpUser);
			**/
			
			//int type = wxCpService.invite("pdxxlingyu", "邀请您关注企业信息号");
			//System.out.println(type);
			
			System.out.println(wxCpService.oauth2buildAuthorizationUrl("http://145122c2y2.iok.la:80/wx/proc_attendance.do", "VANBPMRP"));
		//} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		
	}
}
