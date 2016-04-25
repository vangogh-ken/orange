package com.van.core.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Think
 * 对request的参数进行解码
 */
public class ParamsEncodingFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(ParamsEncodingFilter.class);
	public static final String DEFAULT_ENCODING = "UTF-8";
	private String encoding;

	/**
	 * 对请求进行代理
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		if (req.getMethod().toUpperCase().equals("GET")) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding(encoding);
			chain.doFilter(new ParamRequestWrapper(req), response);
		} else {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding(encoding);
			chain.doFilter(request, response);
		}

	}

	/**
	 * do nothing
	 */
	public void destroy() {
	}

	/**
	 * 初始化编码
	 */
	public void init(FilterConfig config) throws ServletException {
		//String encoding = config.getInitParameter(ENCODING);
		if (encoding == null) {
			this.encoding = DEFAULT_ENCODING;
		} 
		
		/*else {
			this.encoding = encoding;
		}*/
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
	class ParamRequestWrapper extends HttpServletRequestWrapper {
		public ParamRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public StringBuffer getRequestURL() {
			return super.getRequestURL();
		}

		@Override
		public String getRequestURI() {
			return super.getRequestURI();
		}

		@Override
		public String getParameter(String name) {
			return convertToUTF8(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);
			if(values == null){
				return null;
			}else{
				for(int i=0, len = values.length; i<len; i++){
					values[i] = convertToUTF8(values[i]);
				}
				return values;
			}
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> map = super.getParameterMap();
			for(String key : map.keySet()){
				String[] values = map.get(key);
				if(values != null){
					for(int i=0, len = values.length; i<len; i++){
						values[i] = convertToUTF8(values[i]);
					}
					map.put(key, values);
				}else{
					map.put(key, null);
				}
			}
			
			return map;
		}
		
		public String convertToUTF8(String text){
			String str = "";
			if(text != null && !text.equals("")){
				try {
					str = new String(text.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			return str;
		}
	}

	/**
	 * 方法处理映射
	 
	private static final Map<String, Integer> methods = new HashMap<String, Integer>();
	static {
		methods.put("getParameter", 0);
		methods.put("getParameterValues", 1);
		methods.put("getParameterMap", 2);
	}
	*/
	/**
	 * 将request 重新包装, 通过包装的过程修改参数中的值
	 
	class ParamRequestWrapper extends HttpServletRequestWrapper {
		private Map<String, String[]> map = null;
		private String uri;
		private StringBuffer url;

		public ParamRequestWrapper(HttpServletRequest request) {
			super(request);
			
			Map<String, String[]> parameterMap = request.getParameterMap();
			Map<String, String[]> parameterMapNew = new HashMap<String, String[]>();
			try {
				for (Entry<String, String[]> set : parameterMap.entrySet()) {
					int k = 0;
					String[] values = set.getValue();
					for (String value : values) {
						values[k++] = new String(value.getBytes("ISO8859-1"), encoding);
					}
					parameterMapNew.put(set.getKey(), values);
				}
				this.map = Collections.unmodifiableMap(parameterMapNew);
				this.uri = request.getRequestURI();
				this.url = request.getRequestURL();
			} catch (UnsupportedEncodingException e) {
				throw new UnsupportedOperationException(e);
			}
		}

		@Override
		public StringBuffer getRequestURL() {
			return url;
		}

		@Override
		public String getRequestURI() {
			return uri;
		}

		@Override
		public String getParameter(String name) {
			String[] value = map.get(name);
			if (value != null) {
				switch (value.length) {
				case 0:
					return "";
				case 1:
					return value[0];
				default:
					StringBuilder rs = new StringBuilder();
					for (String v : value) {
						rs.append(',');
						rs.append(v);
					}
					return rs.substring(1);
				}
			}
			return null;
		}

		@Override
		public String[] getParameterValues(String name) {
			return map.get(name);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return map;
		}
		
		public String convertToUTF8(String text){
			String str = "";
			if(text != null && !text.equals("")){
				try {
					str = new String(text.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			return str;
		}
	}
*/
	
	/**
	public String convertToUTF8(String text){
		logger.debug("value {}", text);
		String str = "";
		if(text != null && !text.equals("")){
			
			try {
				str = new String(text.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				str = URLDecoder.decode(text, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return str;
	}
	
	/**
			Enumeration<String> params = request.getParameterNames();
			while(params.hasMoreElements()){
				String param = params.nextElement();
				String[] val = request.getParameterValues(param);
				if(val != null){
					if(val.length == 1){
						String value = convertToUTF8(val[0]);
						request.setAttribute(param, value);
					}else{
						for(int i=0, len = val.length; i<len; i++){
							val[i] = convertToUTF8(val[i]);
						}
						
						request.setAttribute(param, val);
					}
				}
			}
			**/

}
