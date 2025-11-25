package com.hotking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JspHelper {

    private static final String JSP_PATH = "/WEB-INF/jsp/%s.jsp";
    public static String getPath(String jspName){
        return JSP_PATH.formatted(jspName);
    }
}
