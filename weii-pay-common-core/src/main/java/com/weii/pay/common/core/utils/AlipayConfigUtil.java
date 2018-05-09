package com.weii.pay.common.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: weics
 * @Date: Created in 17:13 2018/5/6
 * @Description: 支付宝属性文件加载工具.
 * @Modified By:
 */
public class AlipayConfigUtil {
    private static final Log LOG = LogFactory.getLog(AlipayConfigUtil.class);

    /**
     * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
     */
    private static Properties properties = new Properties();

    private AlipayConfigUtil() {

    }

    // 通过类装载器装载进来
    static {
        try {
            // 从类路径下读取属性文件
            properties.load(AlipayConfigUtil.class.getClassLoader().getResourceAsStream("alipay_config.properties"));
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    /**
     * 函数功能说明 ：读取配置项 Administrator
     *
     * @参数：
     * @return void
     * @throws
     */
    public static String readConfig(String key) {
        return (String) properties.get(key);
    }
}
