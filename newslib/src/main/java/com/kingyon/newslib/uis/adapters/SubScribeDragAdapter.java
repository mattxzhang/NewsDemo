package com.kingyon.newslib.uis.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.ColumnEntity;

import java.util.List;


public class SubScribeDragAdapter extends BaseAdapter {

	private final static String TAG = "SubScribeDragAdapter";
	private boolean isItemShow = false;
	private Context context;
	private int holdPosition;
	private boolean isChanged = false;
	private boolean isListChanged = false;
	boolean isVisible = true;
	public List<ColumnEntity> channelList;
	private TextView item_text;
	public int remove_position = -1;
//	private ImageView img_vip;

	public SubScribeDragAdapter(Context context, List<ColumnEntity> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ColumnEntity getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.nl_item_column, null);
		item_text = (TextView) convertView.findViewById(R.id.text_item);
//		img_vip = (ImageView) convertView.findViewById(R.id.img_vip);
		ColumnEntity channel = getItem(position);
		item_text.setText(channel.getName());
		// 第一个不能修改
		if (position == 0) {
			item_text.setEnabled(false);
		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if (remove_position == position) {
			item_text.setText("");
		}

//        if (channel.getIsPay()) {
//            img_vip.setVisibility(View.VISIBLE);
//        } else {
//            img_vip.setVisibility(View.GONE);
//        }
		return convertView;
	}

	public void addItem(ColumnEntity channel) {
		channelList.add(channel);
		isListChanged = true;
		notifyDataSetChanged();
	}

	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		ColumnEntity dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition="
				+ dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		isListChanged = true;
		notifyDataSetChanged();
	}

	public List<ColumnEntity> getChannelLst() {
		return channelList;
	}

	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		isListChanged = true;
		notifyDataSetChanged();
	}

	public void setListDate(List<ColumnEntity> list) {
		channelList = list;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public boolean isListChanged() {
		return isListChanged;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}