package com.github.reflectapi;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ReflectApi {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ReflectApi.class, args);
    }
    
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
	@RequestMapping("/getapis")
	@ResponseBody
    public Object getApis() {
		ArrayList<HashMap<String, Object>> apis = new ArrayList<HashMap<String, Object>>(10);
    	HashMap<String, Object> apiDetails = new HashMap<String, Object>();
    	
    	Method methods [] = this.getClass().getMethods();
    	
    	for (int i = 0; i < methods.length; i++) {
    		apiDetails = new HashMap<String, Object>();
    		RequestMapping at = methods[i].getAnnotation(RequestMapping.class); 
    		if (at != null) {
    			if (at.value().length == 0) {
    				apiDetails.put("endpoint", "/" + methods[i].getName());
    			} else { 
    				apiDetails.put("endpoint", at.value()[0]);
    			}
    			
    			Parameter [] params = methods[i].getParameters();
    			if (params.length > 0) {
    				String[] paramNames = new String[params.length];
    				for (int j = 0; j < params.length; j++) {
    					RequestParam param = params[j].getAnnotation(RequestParam.class);
    					if (param != null) {
    						paramNames[j] = param.name();
    					}
    				}
    				apiDetails.put("params", paramNames);
    				
    			}
    			
    			RequestMethod [] rm = at.method();
    			if (rm.length == 0) {
    				apiDetails.put("method", "GET");
    			} else {
	    			apiDetails.put("method", rm);
    			}
    			apis.add(apiDetails);
    		}
    	}
    	return apis;
    }
	
	@RequestMapping(value="/method1")
	public String method1() {
		return "I'm a GET method";
	}
	
	@RequestMapping(value="/method2", method={RequestMethod.POST})
	public String method2() {
		return "I'm a POST method";
	}
	
	// Should not be found
	public String method3() {
		return "This does nothing";
	}
	
	@RequestMapping(value="/method4")
	public String method4() {
		return "I'm a get method4";
	}
	
	@RequestMapping(value="/method5", method={RequestMethod.POST})
	public String method5post() {
		return "I'm a post method5";
	}
	
	@RequestMapping(value="/method5", method={RequestMethod.GET})
	public String method5get() {
		return "I'm a get method5";
	}
	
	@RequestMapping(method={RequestMethod.GET,RequestMethod.POST})
	public String method6() {
		return "I'm a post/get method6";
	}
	
	@RequestMapping(value="/methodWithParams", method={RequestMethod.GET})
	public String methodWithParams(@RequestParam(name="param1") String param1, @RequestParam(name="param2") String param2) {
		return "I'm a get methodWIthParams";
	}
}
