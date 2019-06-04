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
import test.kw.accountapp.util.GlobalUtil;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryView>{
    private LayoutInflater layoutInflater;
    private Context context;
    private LinkedList<CategoryBean> linkedList = GlobalUtil.getInstance().costRes;
    //判断哪一个被选中
    private String selected = "";

    public CategoryAdapter(Context context){
        this.context = context;
        layoutInflater =LayoutInflater.from(context);
        selected = linkedList.get(0).title;
    }
    @NonNull
    @Override
    public CategoryView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =layoutInflater.inflate(R.layout.cell_category,viewGroup,false);
        CategoryView categoryView = new CategoryView(view);
        return categoryView;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryView categoryView, int i) {
        final CategoryBean res = linkedList.get(i);
        categoryView.imageView.setImageResource(res.resBlack);
        categoryView.textView.setText(res.title);
        if (categoryView.textView.toString()==selected){
            categoryView.background.setBackgroundResource(R.drawable.bg_edit_text);
        }else {
            categoryView.background.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public int getItemCount() {
        return linkedList.size();
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