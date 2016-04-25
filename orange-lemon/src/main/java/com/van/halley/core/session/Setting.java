package com.van.halley.core.session;

import com.van.halley.core.page.PageView;

/**
 * use to set custom properties
 * @author anxinxx
 *
 */
public class Setting {
	/**
	 * 默认分页数据
	 */
	private int customPageSize = PageView.DEFAULT_PAGE_SIZE;

	public int getCustomPageSize() {
		return customPageSize;
	}

	public void setCustomPageSize(int customPageSize) {
		this.customPageSize = customPageSize;
	}
	
}
