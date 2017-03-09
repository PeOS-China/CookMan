package com.example.administrator.cookman.model.entity.CookEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/19.
 */

public class CookRecipe implements Parcelable {

    private String img;
    private String ingredients;
    private String method;
    private String sumary;
    private String title;

    public CookRecipe(){

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img);
        dest.writeString(this.ingredients);
        dest.writeString(this.method);
        dest.writeString(this.sumary);
        dest.writeString(this.title);
    }

    protected CookRecipe(Parcel in) {
        this.img = in.readString();
        this.ingredients = in.readString();
        this.method = in.readString();
        this.sumary = in.readString();
        this.title = in.readString();
    }

    public static final Creator<CookRecipe> CREATOR = new Creator<CookRecipe>() {
        @Override
        public CookRecipe createFromParcel(Parcel source) {
            return new CookRecipe(source);
        }

        @Override
        public CookRecipe[] newArray(int size) {
            return new CookRecipe[size];
        }
    };
}
