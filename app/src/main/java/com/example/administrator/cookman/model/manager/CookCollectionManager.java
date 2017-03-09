package com.example.administrator.cookman.model.manager;

import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.model.entity.CookEntity.CookRecipe;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CookDetail;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class CookCollectionManager {

    private static CookCollectionManager Instance = null;

    public static CookCollectionManager getInstance(){
        if(Instance == null)
            Instance = new CookCollectionManager();

        return Instance;
    }

    private CookCollectionManager(){

    }

    public List<TB_CookDetail> get(){
        List<TB_CookDetail> tbDatas = DataSupport.findAll(TB_CookDetail.class);
        return tbDatas;
    }

    public void add(CookDetail data){

        int count = DataSupport.where("menuId = ?", data.getMenuId()).count(TB_CookDetail.class);

        if(0 == count){
            TB_CookDetail cook = cookDetail2TB(data);
            cook.save();
        }

    }

    public void delete(CookDetail data){
        List<TB_CookDetail> tbDatas = DataSupport.where("menuId = ?", data.getMenuId()).find(TB_CookDetail.class);

        if(tbDatas != null && tbDatas.size() > 0){
            tbDatas.get(0).delete();
        }
    }

    public CookDetail tb2CookDetail(TB_CookDetail cook){
        CookDetail data = new CookDetail();

        data.setCtgTitles(cook.getCtgTitles());
        data.setMenuId(cook.getMenuId());
        data.setName(cook.getName());
        data.setThumbnail(cook.getThumbnail());

        CookRecipe cookRecipe = new CookRecipe();

        cookRecipe.setImg(cook.getImg());
        cookRecipe.setMethod(cook.getMethod());
        cookRecipe.setIngredients(cook.getIngredients());
        cookRecipe.setSumary(cook.getSumary());
        cookRecipe.setTitle(cook.getTitle());

        data.setRecipe(cookRecipe);

        return data;
    }

    private TB_CookDetail cookDetail2TB(CookDetail cook){
        TB_CookDetail data = new TB_CookDetail();

        data.setCtgTitles(cook.getCtgTitles());
        data.setMenuId(cook.getMenuId());
        data.setName(cook.getName());
        data.setThumbnail(cook.getThumbnail());

        data.setImg(cook.getRecipe().getImg());
        data.setMethod(cook.getRecipe().getMethod());
        data.setIngredients(cook.getRecipe().getIngredients());
        data.setSumary(cook.getRecipe().getSumary());
        data.setTitle(cook.getRecipe().getTitle());

        return data;
    }

}
