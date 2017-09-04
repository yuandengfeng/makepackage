package rest;

import rest.example.User;
import rest.example.XML2BeanUtilsTest;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class Maintest {

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value !=null && value !="")
                        map.put(key, value);
                }

            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return map;

    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        XML2BeanUtilsTest xml2BeanUtilsTest = new XML2BeanUtilsTest();
        User user =xml2BeanUtilsTest.createUser();
        Map<String, Object> map =transBean2Map(user);
        for(String key:map.keySet()){
            System.out.println(key);
            if(key.equals("hobbyList"))
                System.out.println(map.get(key).getClass());
        }
//        System.out.println(URLEncoder.encode("开户许可证照片","GBK").replace("%",""));

    }

}
