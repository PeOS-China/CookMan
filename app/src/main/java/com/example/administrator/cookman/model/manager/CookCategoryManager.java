package com.example.administrator.cookman.model.manager;

import android.content.res.AssetManager;

import com.example.administrator.cookman.CookManApplication;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryChildInfo1;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryChildInfo2;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/25.
 */

public class CookCategoryManager {

    private static CookCategoryManager Instance = null;

    public static CookCategoryManager getInstance(){
        if(Instance == null)
            Instance = new CookCategoryManager();

        return Instance;
    }

    private ArrayList<CategoryChildInfo1> categoryAllDatas;
    private ArrayList<CategoryInfo> categoryFirDatas;

    private Gson gson;
    private CookCategoryManager(){
        gson = new Gson();
    }

    public void initDatas(ArrayList<CategoryChildInfo1> categoryAllDatas){
        this.categoryAllDatas = categoryAllDatas;
        initCategoryFirDatas();
    }

    public ArrayList<CategoryChildInfo1> getCategoryDatas(){
        if(categoryAllDatas == null || categoryAllDatas.size() < 1) {
            initDatasFrmJson();
            initCategoryFirDatas();
        }

        return categoryAllDatas;
    }

    public ArrayList<CategoryInfo> getCategoryFirDatas() {
        if(categoryFirDatas == null || categoryFirDatas.size() < 1){
            getCategoryDatas();
        }

        return categoryFirDatas;
    }

    public ArrayList<CategoryChildInfo2> getCategorySndDatas(String cid){
        for(CategoryChildInfo1 item : categoryAllDatas){
            if(item.getCategoryInfo().getCtgId().equals(cid)){
                return item.getChilds();
            }
        }

        return null;
    }

    private void initDatasFrmJson(){
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = CookManApplication
                    .getContext().getAssets().open("default_cook_category_all.json"
                            , AssetManager.ACCESS_STREAMING);

            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();

            JSONArray jo = new JSONArray(buf.toString());
            categoryAllDatas = gson
                    .fromJson(
                            jo.toString()
                            , new TypeToken<ArrayList<CategoryChildInfo1>>() {}.getType());
        }
        catch (Exception e){

        }
    }

    private void initCategoryFirDatas(){
        categoryFirDatas = new ArrayList<>();

        if(categoryAllDatas == null || categoryAllDatas.size() < 1)
            return ;

        for(CategoryChildInfo1 item : categoryAllDatas)
            categoryFirDatas.add(item.getCategoryInfo());
    }

    //抵制南朝鲜部署萨德，剔除“韩国料理” —— 2017.03.06
    public static ArrayList<CategoryChildInfo1> removeBangZi(ArrayList<CategoryChildInfo1> datas){
        for(CategoryChildInfo1 item1 : datas){
            if(item1.getCategoryInfo().getCtgId().equals("0010001004")){
                for(CategoryChildInfo2 item2 : item1.getChilds()){
                    if(item2.getCategoryInfo().getCtgId().equals("0010001045")){
                        item1.getChilds().remove(item2);
                        return datas;
                    }
                }
            }
        }

        return datas;

    }

}
