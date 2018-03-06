package org.hothub.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hothub on 2018/02/23.
 * Description: 用ThreadLocal提供一个存储线程内变量的地方.
 *              客户端代码可以用静态方法存储和获取线程内变量
 */
public class ContextManager {

    private static final Logger logger = LoggerFactory.getLogger(ContextManager.class);



    /**
     * 保存变量的ThreadLocal，保持在同一线程中同步数据.
     * */
    private final static ThreadLocal<Map<String, Object>> MANAGER_MAP = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };



    protected ContextManager() {
    }



    /**
     * 获得线程中保存的属性.
     *
     * @param attribute 属性名称
     * @return 属性值
     */
    public static Object get(String attribute) {
        Map<String, Object> map = MANAGER_MAP.get();

        return map != null ? map.get(attribute) : null;
    }



    /**
     * 获得线程中保存的属性，使用指定类型进行转型.
     *
     * @param attribute 属性名称
     * @param clazz 类型
     * @param <T> 自动转型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String attribute, Class<T> clazz) {
        return (T) get(attribute);
    }



    /**
     * 设置制定属性名的值.
     *
     * @param key   属性名称
     * @param value 属性值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = MANAGER_MAP.get();

        map.put(key, value);

        MANAGER_MAP.set(map);
    }



    /**
     * 清除当前线程中保存的值
     * */
    public static void remove() {
        logger.info("THREADLOCAL - REMOVE");

        MANAGER_MAP.remove();
    }

}
