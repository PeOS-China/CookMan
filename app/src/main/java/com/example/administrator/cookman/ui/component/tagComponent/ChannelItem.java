package com.example.administrator.cookman.ui.component.tagComponent;

import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;

import java.io.Serializable;

/** 
 * ITEM�Ķ�Ӧ���򻯶�������
 *  */
public class ChannelItem implements Serializable {

	private static final long serialVersionUID = -6465237897027410019L;

	public String id;

	public String name;

	public Integer orderId;

	public Integer selected;

	public ChannelItem() {
	}

	public ChannelItem(TB_CustomCategory data, int orderId, int selected){
		this.id = data.getCtgId();
		this.name = data.getName();
		this.orderId = orderId;
		this.selected = selected;
	}

	public ChannelItem(String id, String name, int orderId,int selected) {
		this.id = id;
		this.name = name;
		this.orderId = Integer.valueOf(orderId);
		this.selected = Integer.valueOf(selected);
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getOrderId() {
		return this.orderId.intValue();
	}

	public Integer getSelected() {
		return this.selected;
	}

	public void setId(String paramInt) {
		this.id = paramInt;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public void setOrderId(int paramInt) {
		this.orderId = Integer.valueOf(paramInt);
	}

	public void setSelected(Integer paramInteger) {
		this.selected = paramInteger;
	}

	public String toString() {
		return "ChannelItem [id=" + this.id + ", name=" + this.name
				+ ", selected=" + this.selected + "]";
	}
}