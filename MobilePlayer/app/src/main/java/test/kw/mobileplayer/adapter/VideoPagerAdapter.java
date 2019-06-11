package test.kw.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import test.kw.mobileplayer.R;
import test.kw.mobileplayer.domain.MediaItem;
import test.kw.mobileplayer.pager.VideoPager;
import test.kw.mobileplayer.utils.Utils;

public class VideoPagerAdapter extends BaseAdapter {
    private Utils utils;
    private Context context;
    private ArrayList<MediaItem> arrayList;
    public VideoPagerAdapter(Context context,ArrayList<MediaItem> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        utils = new Utils();
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView==null){
            convertView = View.inflate(context,R.layout.item_video_pager,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size = convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //得到数据
        MediaItem mediaItem = arrayList.get(position);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,mediaItem.getSize()));
        viewHolder.tv_time.setText(utils.stringForTime((int)mediaItem.getDuration()));

        return convertView;
    }
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}