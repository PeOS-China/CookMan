package com.example.administrator.cookman.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CookSearchHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class TagCookSearchHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<CookSearchHistory> datas;

    public TagCookSearchHistoryAdapter(Context context, List<CookSearchHistory> datas) {
        this.context = context;

        this.datas = new ArrayList<>();
        for(CookSearchHistory item : datas)
            this.datas.add(item);

        if(datas.size() > 0){
            this.datas.add(new CookSearchHistory("清除历史"));
        }

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CookSearchHistory getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tagview_cook_search_history, null);

            holder = new ViewHolder();
            holder.tagBtn = (Button) convertView.findViewById(R.id.tag_btn);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        final int index = position;
        holder.tagBtn.setText(getItem(index).getName());
        return convertView;
    }

    public void setDatas(List<CookSearchHistory> datas){
        this.datas = datas;
    }

    public void clean(){
        this.datas.clear();
    }

    static class ViewHolder {
        Button tagBtn;
    }

}
