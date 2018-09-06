package com.weii.admin.web.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author: weics
 * @Date: Created in 22:48 2018/9/6
 * @Description:
 * @Modified By:
 */
public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * 构造函数私有化
     */
    private JsonUtils (){}

    /**
     * 将请求中的Json流转换成Json对象
     * @param httpServletRequest
     * @return
     */
    public static JSONObject requestJson(HttpServletRequest httpServletRequest){
        StringBuffer buffer = new StringBuffer();
        String line = null;
        JSONObject jsonObject = null;
        try {
            BufferedReader reader = httpServletRequest.getReader();
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch(Exception e) {
            LOG.error(e.toString());
        }
        return jsonObject;
    }


    /**
     * 将Map内的参数,转换成Json实体,并写出
     * @param response
     * @param object
     * @throws IOException
     */
    public static void responseJson(HttpServletResponse response,
                                    Object object) throws IOException {


        Object toJSON = JSONObject.toJSON(object);
        try {
            response.getWriter().write(toJSON.toString());
        } catch (IOException e) {
            LOG.error(e.toString());
        }
    }

}
