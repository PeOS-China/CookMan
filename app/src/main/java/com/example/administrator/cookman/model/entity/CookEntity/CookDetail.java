package com.example.administrator.cookman.model.entity.CookEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/19.
 */

public class CookDetail implements Parcelable {

    private ArrayList<String> ctgIds;//分类ID
    private String ctgTitles;
    private String menuId;
    private String name;
    private CookRecipe recipe;
    private String thumbnail;

    public CookDetail(){

    }

    public ArrayList<String> getCtgIds() {
        return ctgIds;
    }

    public void setCtgIds(ArrayList<String> ctgIds) {
        this.ctgIds = ctgIds;
    }

    public String getCtgTitles() {
        return ctgTitles;
    }

    public void setCtgTitles(String ctgTitles) {
        this.ctgTitles = ctgTitles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public CookRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(CookRecipe recipe) {
        this.recipe = recipe;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.ctgIds);
        dest.writeString(this.ctgTitles);
        dest.writeString(this.menuId);
        dest.writeString(this.name);
        dest.writeParcelable(this.recipe, flags);
        dest.writeString(this.thumbnail);
    }

    protected CookDetail(Parcel in) {
        this.ctgIds = in.createStringArrayList();
        this.ctgTitles = in.readString();
        this.menuId = in.readString();
        this.name = in.readString();
        this.recipe = in.readParcelable(CookRecipe.class.getClassLoader());
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<CookDetail> CREATOR = new Parcelable.Creator<CookDetail>() {
        @Override
        public CookDetail createFromParcel(Parcel source) {
            return new CookDetail(source);
        }

        @Override
        public CookDetail[] newArray(int size) {
            return new CookDetail[size];
        }
    };
}
