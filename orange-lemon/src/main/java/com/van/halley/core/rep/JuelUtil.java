package com.van.halley.core.rep;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.van.orange.apple.juel.ExpressionFactoryImpl;
import com.van.orange.apple.juel.javax.el.ExpressionFactory;
import com.van.orange.apple.juel.javax.el.ValueExpression;
import com.van.orange.apple.juel.util.SimpleContext;

public class JuelUtil {
	/**
	 * 解析简单文本字符串
	 * @param text
	 * @param varaible
	 * @return
	 */
	public static String parseSimpleText(String text, Map<String, String> varaible){
		ExpressionFactory factory = new ExpressionFactoryImpl(System.getProperties());
        SimpleContext simpleContext = new SimpleContext();
        
        for(Entry<String, String> entry : varaible.entrySet()){
       	 simpleContext.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue(), String.class));
        }
        
		ValueExpression expr = factory.createValueExpression(simpleContext, text, String.class);
		return expr.getValue(simpleContext).toString();
	}
	
	public static void main(String[] args) {
		Map<String, String> varaible = new HashMap<String, String>();
		varaible.put("A", "a");
		varaible.put("B", "b");
		String text = "h:As a ${B}ox ='${A}'";
		System.out.println(parseSimpleText(text, varaible));
	}
}
