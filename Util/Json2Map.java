package Util;

import clojure.lang.Obj;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class Json2Map {

    public static List<List<Object>> fieldsList = new ArrayList<List<Object>>();

    public static Map json2Map(String jsonString){
        //第一种方式
//        Map maps = (Map)JSON.parse(jsonString);
        //方式二
        Map map = JSON.parseObject(jsonString,Map.class);
        //方式三
//        Map json = (Map) JSONObject.parse(jsonString);
        //方式四
//        Map mapObj = JSONObject.parseObject(jsonString,Map.class);
        return map;
    }

    public static void map2List(Map<Object,Object> mapObj){
        Set<Map.Entry<Object,Object>> docset = mapObj.entrySet();
        Iterator<Map.Entry<Object,Object>> docentry = docset.iterator();
        //创建字段列表
        List<Object> fields = new ArrayList<Object>();
        while (docentry.hasNext()){
            Map.Entry<Object,Object> entry = docentry.next();
            Object key = entry.getKey();
            Object value = entry.getValue();

            if(key.getClass().isInstance(new JSONObject())){
                String subDoc = ((JSONObject) key).toJSONString();
                Map<Object,Object> subMap = json2Map(subDoc);
                map2List(subMap);
            }
            if(key.getClass().isInstance(new JSONArray())){
                String jsonStr = ((JSONArray)key).toJSONString();
                Map<Object,Object> listMap = json2Map(jsonStr);
                map2List(listMap);
            }

            if(value.getClass().isInstance(new JSONObject())){
                String subDoc = ((JSONObject) value).toJSONString();
                Map<Object,Object> subMap = json2Map(subDoc);
                map2List(subMap);
            }
            if(value.getClass().isInstance(new JSONArray())){
                String jsonStr = ((JSONArray)value).toJSONString();
                Map<Object,Object> listMap = json2Map(jsonStr);
                map2List(listMap);
            }
            if(!value.getClass().isInstance(new JSONObject()) && !value.getClass().isInstance(new JSONArray()) && !(key.getClass().isInstance(new JSONObject())) && !(key.getClass().isInstance(new JSONArray()))){
                if(value.equals("") || value == null){
                    value = "FieldWithoutValue";
                    fields.add(key+"=="+value);
                }else {
                    fields.add(key + "==" + value);
                }
            }
        }
        fieldsList.add(fields);
    }

    public static void main(String[] args){
        String str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";
        String strArr = "{{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}," + "{\"00\":\"zhangsan\",\"11\":\"lisi\",\"22\":\"wangwu\",\"33\":\"maliu\"}}";
        Map m = json2Map(strArr);
        map2List(m);
        for(List l:fieldsList){
            System.out.println(l);
        }
    }
}
