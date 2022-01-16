package com.dtask.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhong on 2021-12-28.
 */
public class RequestUtil {
    /**
     * Refuse the request with the error message, and navigate it to the index.
     * @param response Response
     */
    public static void refuseRequest(HttpServletResponse response){
        try {
            // 向前端返回token过期
            PrintWriter printWriter = response.getWriter();
            printWriter.print("NO_TOKEN_IN_HEADER");
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refuseRequestForNoAuthApi(HttpServletResponse response){
        try {
            // 向前端返回token过期
            PrintWriter printWriter = response.getWriter();
            printWriter.print("No Auth API in token");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
