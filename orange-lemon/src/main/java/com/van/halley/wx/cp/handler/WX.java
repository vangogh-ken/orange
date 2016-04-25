package com.van.halley.wx.cp.handler;

/**
 * 人机交互原则: 命令 - 文本 - 文本 ... 命令 - 文本...
 * @author anxinxx
 *
 */
public interface WX {
	
	public static class Type{
		public static final String TEXT = "text";
		public static final String IMAGE = "image";
		public static final String VOICE = "voice";
		public static final String VIDEO = "video";
		public static final String SHORTVIDEO = "shortvideo";
		public static final String LOCATION = "location";
		public static final String LINK = "link";
		public static final String NEWS = "news";
		public static final String EVENT = "event";
		
		public static final String FILE = "FILE";
		public static final String MPNEWS = "mpnews";
	}
	
	public static class Event{
		//成员关注
		public static final String SUBSCRIBE = "subscribe";
		
		//取消关注事件
		public static final String UNSUBSCRIBE = "unsubscribe";
		
		//上报地理位置事件
		public static final String LOCATION = "LOCATION";
		
		//点击菜单拉取消息的事件推送
		public static final String CLICK = "click";
		
		//点击菜单跳转链接的事件推送
		public static final String VIEW = "VIEW";
		
		//扫码推事件的事件推送
		public static final String SCANCODE_PUSH = "scancode_push";
		
		//扫码推事件且弹出“消息接收中”提示框的事件推送
		public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
		
		//弹出系统拍照发图的事件推送
		public static final String PIC_SYSPHOTO = "pic_sysphoto";
		
		//弹出拍照或者相册发图的事件推送
		public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
		
		//弹出微信相册发图器的事件推送
		public static final String PIC_WEIXIN = "pic_weixin";
		
		//弹出地理位置选择器的事件推送
		public static final String LOCATION_SELECT = "location_select";
		
		//成员进入应用的事件推送
		public static final String ENTER_AGENT = "enter_agent";
		
		//异步任务完成事件推送
		public static final String BATCH_JOB_RESULT = "batch_job_result";
		
		public static class KEY{
			public static final String HANDLE_TASKS = "HANDLE_TASKS";
			public static final String CLAIM_TASKS = "CLAIM_TASKS";
			public static final String FINISHED_TASKS = "FINISHED_TASKS";
			public static final String CUSTOMER_VISIT = "CUSTOMER_VISIT";
			public static final String CUSTOMER_QUERY = "CUSTOMER_QUERY";
			public static final String PROC_ATTENDANCE = "PROC_ATTENDANCE";
			public static final String PROC_VACATION = "PROC_VACATION";
			
			public static boolean isExpect(String eventKey){
				switch (eventKey) {
				case HANDLE_TASKS: 
					return true;
				case CLAIM_TASKS: 
					return true;
				case FINISHED_TASKS: 
					return true;
				case CUSTOMER_VISIT: 
					return true;
				case CUSTOMER_QUERY: 
					return true;
				case PROC_ATTENDANCE: 
					return true;
				case PROC_VACATION: 
					return true;
				default:
					break;
				}
				
				return false;
			}
		}
	}
	
}
