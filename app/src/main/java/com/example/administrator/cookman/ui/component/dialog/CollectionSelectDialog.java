package com.example.administrator.cookman.ui.component.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.cookman.R;

/**
 * Created by Administrator on 2017/2/27.
 */

public class CollectionSelectDialog extends AbsCustomDialog implements android.view.View.OnClickListener{

    private TextView textDelete;
    private Button btnCancel;

    public CollectionSelectDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        textDelete = (TextView) findViewById(R.id.text_collection);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        textDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }



    @Override
    public int getWindowAnimationsResId() {
        return R.style.BottomDialogAnim;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.layout_dialog_collection_select;
    }

    @Override
    public int getWidth() {
        return android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getHeight() {
        return android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }

    public void setOnCollectionSelectListener(OnCollectionSelectListener onCollectionSelectListener) {
        this.onCollectionSelectListener = onCollectionSelectListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.text_collection:
                if (onCollectionSelectListener != null) {
                    onCollectionSelectListener.onCollectionSelectDelete();
                }
                dismiss();
                break;

            case R.id.btn_cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    private OnCollectionSelectListener onCollectionSelectListener;
    public interface OnCollectionSelectListener {
        public void onCollectionSelectDelete();
    }

}
