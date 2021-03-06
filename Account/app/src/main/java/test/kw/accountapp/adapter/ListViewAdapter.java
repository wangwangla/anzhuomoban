package test.kw.accountapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;

import org.w3c.dom.Text;

import java.util.LinkedList;

import test.kw.accountapp.R;
import test.kw.accountapp.RecordBean;
import test.kw.accountapp.util.DateUtil;
import test.kw.accountapp.util.GlobalUtil;

public class ListViewAdapter extends BaseAdapter {
    //每天的好多个账目
    private LinkedList<RecordBean> recordBeanLinkedList = new LinkedList<>();
    //初始化界面的
    private LayoutInflater layoutInflater;
    //上下文
    private Context context;
    public ListViewAdapter(Context context){
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    //设置参数
    public void setData(LinkedList<RecordBean> linkedList){
        this.recordBeanLinkedList = linkedList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordBeanLinkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeanLinkedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.cell_list_view,null);
            RecordBean recordBean = (RecordBean) getItem(position);
            viewHolder = new ViewHolder(convertView,recordBean);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


}

class ViewHolder {
    TextView remarkTV;
    TextView amountTV;
    TextView timeTV;
    ImageView imageView;
    public ViewHolder(View itemView , RecordBean recordBean){
        remarkTV = itemView.findViewById(R.id.textview_remark);
        timeTV = itemView.findViewById(R.id.textview_time);
        imageView = itemView.findViewById(R.id.imageView_category);
        amountTV = itemView.findViewById(R.id.textview_amount);
        remarkTV.setText(recordBean.getRemark());
        if (recordBean.getRecordType() == 1){
            amountTV.setText("-"+recordBean.getAmount());
        }else {
            amountTV.setText("+"+recordBean.getAmount());
        }
        timeTV.setText(DateUtil.getFormattedTime(recordBean.getTimeStamp()));
        imageView.setImageResource(GlobalUtil.getInstance().getResourceIcon(recordBean.getCategory()));
    }
}