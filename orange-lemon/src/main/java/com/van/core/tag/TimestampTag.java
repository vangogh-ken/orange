package com.van.core.tag;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.van.core.spring.ApplicationContextHelper;
import com.van.service.AttributeTagService;

public class TimestampTag extends TagSupport {
	private AttributeTagService attributeStatusControlService = 
			ApplicationContextHelper.getBean("attributeStatusControlService");
	private String id;
	//字段名 * 必须
	private String name;
	//值 * 必须
	private Date value;
	//状态值
	private String status;
	//样式class
	private String styleClass;
	//模式* 必须
	private String pattern;
	
	//类型Id * 必须
	private String clsId;
	//控制ID * 必须
	private String controllerId;
	//任务ID
	private String taskId;
	
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		/*JspWriter out = this.pageContext.getOut();
		String val = null;
		if(this.pattern != null && value != null && value instanceof Date){
			val = new SimpleDateFormat(this.pattern).format(value);
		}else{
			val = "";
		}
		
		try {
			if(status == null || name == null || clsId == null){
				out.write("<input id='" + this.id + "' name='" + this.name + "' value='" + val +"' type='text' class='datetimepicker " + this.styleClass +"'>");
			}else{
				boolean readonly = attributeStatusControlService.isReadonly(this.status, this.name, this.clsId, this.controllerId, this.taskId);
				if(!readonly){
					out.write("<input id='" + this.id + "' name='" + this.name + "' value='" +  val +"' type='text' class='datetimepicker " + this.styleClass +"'>");
				}else{
					out.write(val);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return SKIP_BODY;
	}


	public String getName() {
		return name;
	}


	public String getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public Date getValue() {
		return value;
	}


	public void setValue(Date value) {
		this.value = value;
	}


	public String getClsId() {
		return clsId;
	}


	public void setClsId(String clsId) {
		this.clsId = clsId;
	}


	public String getStyleClass() {
		return styleClass;
	}


	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getControllerId() {
		return controllerId;
	}

	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
