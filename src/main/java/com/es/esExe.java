package com.es;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class esExe implements Runnable {

    private String index;   //索引
    private String newIndex;
    private  String type;   //类型
    private  String filed;  //查询字段
    ReEsService es = new ReEsService();
    public esExe(String index,String type,String filed,String newIndex)
    {
        this.index=index;
        this.type=type;
        this.filed=filed;
        this.newIndex=newIndex;
    }

    @Override
    public void run() {

      int totalNum=   es.searchTotalNum(index,type,filed);
      List<JSONObject> list= es.showResult(index,type,filed, totalNum);
      es.createIndex(newIndex,type,list);

    }
}
