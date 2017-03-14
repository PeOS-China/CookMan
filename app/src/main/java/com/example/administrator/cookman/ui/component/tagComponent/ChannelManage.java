package com.example.administrator.cookman.ui.component.tagComponent;

import android.database.SQLException;


import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;
import com.example.administrator.cookman.model.manager.CustomCategoryManager;

import java.util.ArrayList;
import java.util.List;


public class ChannelManage {
	public static ChannelManage channelManage;
	public static List<ChannelItem> defaultUserChannels;
	public static List<ChannelItem> defaultOtherChannels;

	private boolean userExist = false;


	private ChannelManage() throws SQLException {
	}


	public static ChannelManage getManage()throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage();
		return channelManage;
	}

	public List<ChannelItem> getUserChannel() {
		defaultUserChannels = new ArrayList<>();

		int index = 1;
		for(TB_CustomCategory item : CustomCategoryManager.getInstance().getDatas()){
			defaultUserChannels.add(new ChannelItem(item, index, 1));
			index++;
		}

		return defaultUserChannels;
	}
	

	public List<ChannelItem> getOtherChannel() {
		defaultOtherChannels = new ArrayList<>();
		int index = 1;
		for(TB_CustomCategory item : CustomCategoryManager.getInstance().getOtherDatas()){
			defaultOtherChannels.add(new ChannelItem(item, index, 0));
			index++;
		}

		return defaultOtherChannels;
	}

	public static List<ChannelItem> getDefaultUserChannels() {
		return defaultUserChannels;
	}

	public static List<ChannelItem> getDefaultOtherChannels() {
		return defaultOtherChannels;
	}
}
