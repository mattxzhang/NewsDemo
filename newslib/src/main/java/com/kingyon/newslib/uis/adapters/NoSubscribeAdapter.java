package com.kingyon.newslib.uis.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingyon.newslib.R;
import com.kingyon.newslib.greendao.entities.ColumnEntity;

import java.util.List;

public class NoSubscribeAdapter extends BaseAdapter {
    private Context context;
    public List<ColumnEntity> channelList;
    boolean isVisible = true;
    public int remove_position = -1;

    public NoSubscribeAdapter(Context context, List<ColumnEntity> channelList) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.nl_item_column, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(position);
        return convertView;
    }

    class ViewHolder {
        TextView itemText;
//        @Bind(R.id.img_vip)
//        ImageView imgVip;

        ViewHolder(View view) {
            view.setTag(this);
            itemText = (TextView) view.findViewById(R.id.text_item);
        }

        public void setData(int position) {
            ColumnEntity channel = getItem(position);
            itemText.setText(channel.getName());
            if (!isVisible && (position == -1 + channelList.size())) {
                itemText.setText("");
            }
            if (remove_position == position) {
                itemText.setText("");
            }

//            if (channel.getIsPay()) {
//                imgVip.setVisibility(View.VISIBLE);
//            } else {
//                imgVip.setVisibility(View.GONE);
//            }
        }
    }

    public List<ColumnEntity> getChannelLst() {
        return channelList;
    }

    public void addItem(ColumnEntity channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
        // notifyDataSetChanged();
    }

    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }

    public void setListDate(List<ColumnEntity> list) {
        channelList = list;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}