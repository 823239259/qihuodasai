package com.tzdr.web.support.filter;

import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.blogspot.radialmind.html.HTMLParser;
import com.blogspot.radialmind.xss.XSSFilter;


/**
 * 
 * <P>title:@XssHttpWrapper.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月23日
 * @version 1.0
 */
public class XssHttpWrapper extends HttpServletRequestWrapper
{
	
	public static final Logger logger = LoggerFactory.getLogger(XssHttpWrapper.class);

    HttpServletRequest orgRequest = null;

    public XssHttpWrapper(HttpServletRequest request)
    {
        super(request);
        orgRequest = request;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。&lt;br/&gt;
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取&lt;br/&gt;
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name)
    {
        String value = super.getParameter(xssEncode(name));
        if (value != null)
        {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。&lt;br/&gt;
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取&lt;br/&gt; getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name)
    {

        String value = super.getHeader(xssEncode(name));
        if (value != null)
        {
            value = xssEncode(value);
        }
        return value;
    }
    @Override  
    public String[] getParameterValues(String name) {   
       String[] values = super.getParameterValues(name);   
      if(values != null) {   
          int length = values.length;   
           String[] escapseValues = new String[length];   
          for(int i = 0; i < length; i++){   
               escapseValues[i] = xssEncode(values[i]);   
           }   
           return escapseValues;   
        }   
        return super.getParameterValues(name);   
    }
    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * 
     * @param s
     * @return
     */
    private static String xssEncode(String s)
    {
    	  if (s == null || s.isEmpty())
          {
              return s;
          }
    	StringBuilder sb = new StringBuilder(s.length() + 16);  
    	        for (int i = 0; i < s.length(); i++) {  
    	            char c = s.charAt(i);  
    	           switch (c) {  
    	          case '>':  
    	                sb.append('＞');//全角大于号   
    	              break;  
    	          case '<':  
    	               sb.append('＜');//全角小于号   
    	                break;  
    	           case '\'':  
    	                sb.append('‘');//全角单引号   
    	                break;  
    	          case '\"':  
    	               sb.append('“');//全角双引号   
    	                break;  
    	          case '&':  
    	               sb.append('＆');//全角   
    	                break;  
    	           case '\\':  
    	              sb.append('＼');//全角斜线   
    	                break;  
    	           case '#':  
    	              sb.append('＃');//全角井号   
    	                break;  
    	           default:  
    	               sb.append(c);  
    	               break;  
    	           }  
    	       }  
    	s= sb.toString();  
    	s=StringEscapeUtils.escapeHtml4(s);
    	s=HtmlUtils.htmlEscape(s);
        StringReader reader = new StringReader( s );
        StringWriter writer = new StringWriter();
        try {
            HTMLParser.process( reader, writer, new XSSFilter(), true );
            
            return writer.toString();
        } 
        catch (NullPointerException e) {
            return s;
        }
        catch(Exception ex)
        {
        	logger.error(ex.getMessage());
        }
        
        return null;
        
    }

    /**
     * 获取最原始的request
     * 
     * @return
     */
    public HttpServletRequest getOrgRequest()
    {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     * 
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req)
    {
        if (req instanceof XssHttpWrapper)
        {
            return ((XssHttpWrapper) req).getOrgRequest();
        }

        return req;
    }
    
}