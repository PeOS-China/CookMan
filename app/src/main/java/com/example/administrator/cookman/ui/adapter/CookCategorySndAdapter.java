package com.example.administrator.cookman.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryChildInfo2;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/25.
 */

public class CookCategorySndAdapter extends BaseRecyclerAdapter<CookCategorySndAdapter.CookCategorySndStruct>{

    public CookCategorySndAdapter(OnCookCategorySndListener onCookCategorySndListener){
        this.onCookCategorySndListener = onCookCategorySndListener;
    }

    @Override
    public CommonHolder<CookCategorySndStruct> setViewHolder(ViewGroup parent) {
        return new CookCategoryFirHolder(parent.getContext(), parent);
    }

    class CookCategoryFirHolder extends CommonHolder<CookCategorySndStruct>{

        @Bind(R.id.btn_tag_1)
        public Button btnTag1;
        @Bind(R.id.btn_tag_2)
        public Button btnTag2;
        @Bind(R.id.btn_tag_3)
        public Button btnTag3;

        public CookCategoryFirHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_category_snd);
        }

        @Override
        public void bindData(final CookCategorySndStruct cook){
            if(cook.data1 != null){
                btnTag1.setVisibility(View.VISIBLE);
                btnTag1.setText(cook.data1.getName());
                if(cook.isSelect == 1)
                    btnTag1.setSelected(true);
                else
                    btnTag1.setSelected(false);
                btnTag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onCookCategorySndListener != null){
                            updateSelection(cook, 1);
                            onCookCategorySndListener.onCookCategorySndClick(cook.data1.getCtgId(), cook.data1.getName());
                        }
                    }
                });

                if(cook.data2 == null){
                    btnTag2.setVisibility(View.GONE);
                    btnTag3.setVisibility(View.GONE);
                    return ;
                }

                btnTag2.setVisibility(View.VISIBLE);
                btnTag2.setText(cook.data2.getName());
                if(cook.isSelect == 2)
                    btnTag2.setSelected(true);
                else
                    btnTag2.setSelected(false);
                btnTag2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateSelection(cook, 2);
                        if(onCookCategorySndListener != null){
                            onCookCategorySndListener.onCookCategorySndClick(cook.data2.getCtgId(), cook.data2.getName());
                        }
                    }
                });

                if(cook.data3 == null){
                    btnTag3.setVisibility(View.GONE);
                    return ;
                }

                btnTag3.setVisibility(View.VISIBLE);
                btnTag3.setText(cook.data3.getName());
                if(cook.isSelect == 3)
                    btnTag3.setSelected(true);
                else
                    btnTag3.setSelected(false);
                btnTag3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateSelection(cook, 3);

                        if(onCookCategorySndListener != null){
                            onCookCategorySndListener.onCookCategorySndClick(cook.data3.getCtgId(), cook.data3.getName());
                        }
                    }
                });
            }
        }
    }

    private CookCategorySndStruct oldCook;
    private void updateSelection(CookCategorySndStruct newCook, int position){
        if(oldCook != null)
            oldCook.isSelect = 0;

        newCook.isSelect = position;
        oldCook = newCook;

        notifyDataSetChanged();
    }

    public static class CookCategorySndStruct{
        public CategoryInfo data1;
        public CategoryInfo data2;
        public CategoryInfo data3;

        public int isSelect = 0;

        public CookCategorySndStruct(){

        }

        public CookCategorySndStruct(CategoryInfo data1, CategoryInfo data2, CategoryInfo data3){
            this.data1 = data1;
            this.data2 = data2;
            this.data3 = data3;
            this.isSelect = 0;
        }

        public void add(CategoryInfo data){
            if(null == data1) {
                data1 = data;
                return ;
            }

            if(null == data2) {
                data2 = data;
                return ;
            }

            if(null == data3) {
                data3 = data;
                return ;
            }
        }

    }

    private OnCookCategorySndListener onCookCategorySndListener;
    public interface OnCookCategorySndListener{
        public void onCookCategorySndClick(String ctgId, String name);
    }

    public static List<CookCategorySndStruct> createDatas(ArrayList<CategoryChildInfo2> datas){
        List<CookCategorySndStruct> dstDatas = new ArrayList<>();

        int size = datas.size();
        int shi = size / 3;
        int ge = size % 3;

        if(0 == shi){
            if(0 == ge)
                return dstDatas;

            CookCategorySndStruct item = new CookCategorySndStruct();
            for(int i = 0; i < ge; i++)
                item.add(datas.get(i).getCategoryInfo());

            dstDatas.add(item);
            return dstDatas;
        }

        int index = 0;
        for(int i = 0; i < shi; i++){
            CookCategorySndStruct item = new CookCategorySndStruct(
                    datas.get(index).getCategoryInfo(), datas.get(index + 1).getCategoryInfo(), datas.get(index + 2).getCategoryInfo()
            );

            dstDatas.add(item);
            index += 3;
        }

        if(0 == ge)
            return dstDatas;

        CookCategorySndStruct item = new CookCategorySndStruct();
        for(int i = 0; i < ge; i++)
            item.add(datas.get(index + i).getCategoryInfo());

        dstDatas.add(item);

        return dstDatas;
    }

}
