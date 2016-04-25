package com.van.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.van.core.spring.ApplicationContextHelper;
import com.van.service.AttributeTagService;

public class RadioTag extends TagSupport {
	private AttributeTagService attributeStatusControlService = 
			ApplicationContextHelper.getBean("attributeStatusControlService");
	private String id;
	//字段名 * 必须
	private String name;
	//值 * 必须
	private String value;
	//状态值
	private String status;
	//样式class
	private String styleClass;
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
		try {
			if(status == null ){
				List<String> values = attributeStatusControlService.getTypeAndValue(name, clsId);
				for(String val : values){
					if(val.equals(this.value)){
						out.write(val + "<input value=\"" + val + "\" checked=\"checked\" type=\"radio\" name=\"" + this.name + "\">&nbsp;&nbsp;");
					}else{
						out.write(val + "<input value=\"" + val + "\" type=\"radio\" name=\"" + this.name + "\">");
					}
				}
			}else{
				boolean readonly = attributeStatusControlService.isReadonly(this.status, this.name, this.clsId, this.controllerId, this.taskId);
				if(!readonly){
					List<String> values = attributeStatusControlService.getTypeAndValue(name, clsId);
					for(String val : values){
						if(val.equals(this.value)){
							out.write(val + "<input value=\"" + val + "\" checked=\"checked\" type=\"radio\" name=\"" + this.name + "\">&nbsp;&nbsp;");
						}else{
							out.write(val + "<input value=\"" + val + "\" type=\"radio\" name=\"" + this.name + "\">");
						}
					}
				}else{
					out.write(this.value);
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


	public String getValue() {
		return value;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getClsId() {
		return clsId;
	}


	public void setClsId(String clsId) {
		this.clsId = clsId;
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


	public String getStyleClass() {
		return styleClass;
	}


	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
