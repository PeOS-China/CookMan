package com.example.administrator.cookman.model.manager;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.administrator.cookman.CookManApplication;
import com.example.administrator.cookman.model.entity.CookEntity.CookSearchHistory;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
    private List<CookSearchHistory> datas;
    private List<CookSearchHistory> buffer;

    private final static String SharedPreference_Key_Num = "num";
    private final static String SharedPreference_Key_Search_Pre = "search";

    private static String userFilePath = "shareprefer_file_cook_search";
    private SharedPreferences shareUserFile;
    private SharedPreferences.Editor editorUserFile;

    private CookSearchHistoryManager(){
        datas = new ArrayList<>();
        buffer = new ArrayList<>();

        shareUserFile = CookManApplication.getContext().getSharedPreferences(userFilePath, MODE_PRIVATE);
        editorUserFile = shareUserFile.edit();

    }

    private List<CookSearchHistory> getDatasFrmFile(){
        List<CookSearchHistory> datas = new ArrayList<>();
        int num = shareUserFile.getInt(SharedPreference_Key_Num, 0);

        if(0 == num)
            return datas;

        for(int i = 0; i < num; i++){
            datas.add(new CookSearchHistory(shareUserFile.getString(SharedPreference_Key_Search_Pre + i, "")));
        }

        return datas;
    }

    private void saveDatas2File(List<CookSearchHistory> datas){
        int num = datas.size();
        editorUserFile.putInt(SharedPreference_Key_Num, num);

        if(0 == num){
            editorUserFile.commit();
            return ;
        }

        for(int i = 0; i < num; i++){
            editorUserFile.putString(SharedPreference_Key_Search_Pre + i, datas.get(i).getName());
        }

        editorUserFile.commit();
    }

    public List<CookSearchHistory> getDatas(){
        datas.clear();
        buffer.clear();

        datas = getDatasFrmFile();

        return datas;

    }

    public void add2Buffer(CookSearchHistory data){
        for(CookSearchHistory item : datas){
            if(data.getName().equals(item.getName()))
                return ;
        }

        for(CookSearchHistory item : buffer){
            if(data.getName().equals(item.getName()))
                return ;
        }

        buffer.add(data);
    }

    public void clean(){
        this.datas.clear();
        this.buffer.clear();

        editorUserFile.putInt(SharedPreference_Key_Num, 0);
        editorUserFile.commit();
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
            List<CookSearchHistory> headDatas = new ArrayList<>();
            for(int i = 0; i < end; i++){
                headDatas.add(datas.get(i));
            }

            datas.clear();
            for(int i = buffer.size() - 1; i >= 0; i--){
                datas.add(buffer.get(i));
            }

            for(CookSearchHistory item : headDatas){
                datas.add(item);
            }

            save2TB();
            return ;
        }

        //队列不满
        if(datas.size() + buffer.size() > History_Queue_Max_Size){
            int end = History_Queue_Max_Size - buffer.size();
            List<CookSearchHistory> headDatas = new ArrayList<>();
            for(int i = 0; i < end; i++){
                headDatas.add(datas.get(i));
            }

            datas.clear();
            for(int i = buffer.size() - 1; i >= 0; i--){
                datas.add(buffer.get(i));
            }

            for(CookSearchHistory item : headDatas){
                datas.add(item);
            }

            save2TB();
            return ;
        }

        List<CookSearchHistory> headDatas = new ArrayList<>();
        for(int i = 0; i < datas.size(); i++){
            headDatas.add(datas.get(i));
        }

        datas.clear();
        for(int i = buffer.size() - 1; i >= 0; i--){
            datas.add(buffer.get(i));
        }

        for(CookSearchHistory item : headDatas){
            datas.add(item);
        }

        save2TB();

    }

    private void save2TB(){
        saveDatas2File(datas);
    }

}
