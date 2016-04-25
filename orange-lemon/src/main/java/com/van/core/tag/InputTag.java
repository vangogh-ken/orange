package com.van.core.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.util.StringUtil;
import com.van.service.AttributeTagService;

public class InputTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private AttributeTagService attributeTagService = 
			ApplicationContextHelper.getBean("attributeTagService");
	private String id;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 样式class
	 */
	private String styleClass;
	/**
	 * 样式
	 */
	private String styleCss;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 *业务ID 
	 */
	private String basisSubstanceId;
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			if(StringUtil.isNullOrEmpty(id) || StringUtil.isNullOrEmpty(taskId) || StringUtil.isNullOrEmpty(basisSubstanceId)){
				out.write(this.value);
			}else{
				Map<String, String> map = attributeTagService.getAttributeProperty(this.basisSubstanceId, this.id, this.taskId);
				if("TEXT".equals(map.get("attributeType"))){
					if("F".equals(map.get("readonly"))){
						out.write("<textarea id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" class=\"" + this.styleClass + "\" style=\""+ this.styleCss +"\">" + this.value +"</textarea>");
					}else{
						out.write(this.value);
					}
				}else {
					if("F".equals(map.get("readonly"))){
						if("T".equals(map.get("canSelect"))){
							out.write("<input id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" value=\"" 
									+ this.value +"\" type=\"text\" class=\"" + this.styleClass + " dictionary\" style=\""+ this.styleCss +"\" vAttrId=\"" 
									+ map.get("vAttrId") + "\" vClsId=\"" + map.get("vClsId") + "\" vColumn=\""
									+ map.get("vColumn") +"\" vFilterId=\"" + map.get("vFilterId") + "\">");
						}else{
							out.write("<input id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" value=\"" 
									+ this.value +"\" type=\"text\" class=\"" + this.styleClass + "\" style=\""+ this.styleCss +"\">");
						}
					}else{
						out.write(this.value);
					}
					
				} 
				
				/*if("VARCHAR".equals(map.get("attributeType")) || "TIMESTAMP".equals(map.get("attributeType"))
						|| "DATETIME".equals(map.get("attributeType")) || "DOUBLE".equals(map.get("attributeType"))
						|| "INT".equals(map.get("attributeType"))){
					if("F".equals(map.get("readonly"))){
						if("T".equals(map.get("canSelect"))){
							out.write("<input id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" value=\"" 
									+ this.value +"\" type=\"text\" class=\"" + this.styleClass + " dictionary\" style=\""+ this.styleCss +"\" vAttrId=\"" 
									+ map.get("vAttrId") + "\" vClsId=\"" + map.get("vClsId") + "\" vColumn=\""
									+ map.get("vColumn") +"\" vFilterId=\"" + map.get("vFilterId") + "\">");
						}else{
							out.write("<input id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" value=\"" 
									+ this.value +"\" type=\"text\" class=\"" + this.styleClass + "\" style=\""+ this.styleCss +"\">");
						}
					}else{
						out.write(this.value);
					}
					
				}else if("TEXT".equals(map.get("attributeType"))){
					if("F".equals(map.get("readonly"))){
						out.write("<textarea id=\"" + this.id + "\" name=\""+ map.get("valueId") +"\" class=\"" + this.styleClass + "\" style=\""+ this.styleCss +"\">" + this.value +"</textarea>");
					}else{
						out.write(this.value);//针对textarea特殊处理，避免撑开表格
					}
				}*/
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStyleClass() {
		return styleClass;
	}


	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBasisSubstanceId() {
		return basisSubstanceId;
	}

	public void setBasisSubstanceId(String basisSubstanceId) {
		this.basisSubstanceId = basisSubstanceId;
	}

	public String getStyleCss() {
		return styleCss;
	}

	public void setStyleCss(String styleCss) {
		this.styleCss = styleCss;
	}
	
	

}
