package test.kw.accountapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

import test.kw.accountapp.CategoryBean;
import test.kw.accountapp.R;
import test.kw.accountapp.RecordBean;
import test.kw.accountapp.util.GlobalUtil;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryView>{
    private LayoutInflater layoutInflater;
    private Context context;
    private LinkedList<CategoryBean> linkedList = GlobalUtil.getInstance().costRes;
    //判断哪一个被选中
    private String selected = "";
    //处理点击事件
    private  OnCategoryClickListener onCategoryClickListener;

    public CategoryAdapter(Context context){
        this.context = context;
        layoutInflater =LayoutInflater.from(context);
        selected = linkedList.get(0).title;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    @NonNull
    @Override
    public CategoryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =layoutInflater.inflate(R.layout.cell_category,viewGroup,false);
        CategoryView categoryView = new CategoryView(view);
        return categoryView;
    }

    public String getSelected() {
        return selected;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryView categoryView, int i) {
        final CategoryBean res = linkedList.get(i);
        categoryView.imageView.setImageResource(res.resBlack);
        categoryView.textView.setText(res.title);
        categoryView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = res.title;
                notifyDataSetChanged();
                if(onCategoryClickListener!=null){
                    onCategoryClickListener.onClick(res.title);
                }
            }
        });
        if (categoryView.textView.toString().equals(selected)){
            categoryView.background.setBackgroundResource(R.drawable.bg_edit_text);
        }else {
            categoryView.background.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }

    //点击

    public interface OnCategoryClickListener{
        void onClick(String category);
    }

    /**
     * 点击切换按钮会发生改变
     */
    public void changeType(RecordBean.RecordType type){
        if(type == RecordBean.RecordType.RECODE_TYPR_EXPENSE){
            linkedList = GlobalUtil.getInstance().costRes;
        }else {
            linkedList = GlobalUtil.getInstance().earnRes;
        }
        selected = linkedList.get(0).title;
        notifyDataSetChanged();
    }
}

class CategoryView extends RecyclerView.ViewHolder{
    RelativeLayout background;
    ImageView imageView;
    TextView textView;
    public CategoryView(@NonNull View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.cell_background);
        imageView = itemView.findViewById(R.id.imageView_category);
        textView = itemView.findViewById(R.id.textView_category);
    }
}