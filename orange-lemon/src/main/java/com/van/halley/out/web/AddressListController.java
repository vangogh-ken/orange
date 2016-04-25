package com.van.halley.out.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.User;
import com.van.service.UserService;

@Controller
@RequestMapping("/out/")
public class AddressListController {
	@Autowired
	private UserService userService;

    @RequestMapping("addresslist-list")
    public String list(User user, String orgEntityName,
    		@ModelAttribute PageView<User> pageView, Model model) {
    	if (pageView == null) {
			pageView = new PageView<User>(1);
		}
    	if(user == null){
    		user = new User();
    	}
    	
    	user.setStatus("T");
    	
		if(StringUtils.isNotBlank(orgEntityName)){
			String filterText = "POSITION_ID IN(SELECT ID FROM SYS_AUTH_POSITION WHERE ORG_ENTITY_ID IN(SELECT ID FROM SYS_AUTH_ORG_ENTITY WHERE ORG_ENTITY_NAME LIKE '%" + orgEntityName + "%'))";
			if(StringUtils.isBlank(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		userService.query(pageView, user);
		model.addAttribute("pageView", pageView);
        return "/content/out/addresslist-list";
    }

}
