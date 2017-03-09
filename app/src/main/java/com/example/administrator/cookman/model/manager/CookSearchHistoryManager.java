package com.example.administrator.cookman.model.manager;

import com.example.administrator.cookman.model.entity.tb_cook.TB_CookDetail;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CookSearchHistory;
import com.example.administrator.cookman.utils.Logger.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CookSearchHistoryManager {

    private static CookSearchHistoryManager Instance = null;

    public static CookSearchHistoryManager getInstance(){
        if(Instance == null)
            Instance = new CookSearchHistoryManager();

        return Instance;
    }

    private final static int History_Queue_Max_Size = 10;
    private List<TB_CookSearchHistory> datas;
    private List<TB_CookSearchHistory> buffer;

    private CookSearchHistoryManager(){
        datas = new ArrayList<>();
        buffer = new ArrayList<>();

    }

    public List<TB_CookSearchHistory> getDatas(){
        datas.clear();
        buffer.clear();

        List<TB_CookSearchHistory> listDatas = DataSupport
                .where("type = ?", TB_CookSearchHistory.CookSearchHistory_Type_Content + "")
                .find(TB_CookSearchHistory.class);


        if(listDatas != null && listDatas.size() > 0){
            for(TB_CookSearchHistory item : listDatas)
                datas.add(item);
        }

        List<TB_CookSearchHistory> list = new ArrayList<>();
        for(TB_CookSearchHistory item : datas)
            list.add(item);

        return list;
    }

    public void add2Buffer(TB_CookSearchHistory data){
        for(TB_CookSearchHistory item : datas){
            if(data.getName().equals(item.getName()))
                return ;
        }

        for(TB_CookSearchHistory item : buffer){
            if(data.getName().equals(item.getName()))
                return ;
        }

        buffer.add(data);
    }

    //耗时操作
    public void save(){
        if(buffer.size() < 1)
            return ;

        if(buffer.size() >= History_Queue_Max_Size){
            int end = buffer.size() - History_Queue_Max_Size;
            datas.clear();

            for(int i = buffer.size() - 1; i >= end; i--){
                datas.add(buffer.get(i));
            }

            save2TB();
            return ;
        }

        //队列满
        if(datas.size() == History_Queue_Max_Size){
            int end = datas.size() - buffer.size();
            List<TB_CookSearchHistory> headDatas = new ArrayList<>();
            for(int i = 0; i < end; i++){
                headDatas.add(datas.get(i));
            }

            datas.clear();
            for(int i = buffer.size() - 1; i >= 0; i--){
                datas.add(buffer.get(i));
            }

            for(TB_CookSearchHistory item : headDatas){
                datas.add(item);
            }

            save2TB();
            return ;
        }

        //队列不满
        if(datas.size() + buffer.size() > History_Queue_Max_Size){
            int end = History_Queue_Max_Size - buffer.size();
            List<TB_CookSearchHistory> headDatas = new ArrayList<>();
            for(int i = 0; i < end; i++){
                headDatas.add(datas.get(i));
            }

            datas.clear();
            for(int i = buffer.size() - 1; i >= 0; i--){
                datas.add(buffer.get(i));
            }

            for(TB_CookSearchHistory item : headDatas){
                datas.add(item);
            }

            save2TB();
            return ;
        }

        List<TB_CookSearchHistory> headDatas = new ArrayList<>();
        for(int i = 0; i < datas.size(); i++){
            headDatas.add(datas.get(i));
        }

        datas.clear();
        for(int i = buffer.size() - 1; i >= 0; i--){
            datas.add(buffer.get(i));
        }

        for(TB_CookSearchHistory item : headDatas){
            datas.add(item);
        }

        save2TB();

    }

    private void save2TB(){

        DataSupport.deleteAll(TB_CookSearchHistory.class);
        DataSupport.saveAll(datas);

    }

}
