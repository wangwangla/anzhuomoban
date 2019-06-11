package test.kw.mobileplayer.base;

import android.content.Context;
import android.view.View;

/**
 * 基类
 */
public abstract class BasePager {
    //构造方法将试图创建  孩子强制继承
    //初始化子页面的数据
    protected Context context;
    public boolean isInitDate = false;
    public View rootview;
    public BasePager(Context context){
        this.context = context;
        rootview = initView();
    }

    /**
     * 强制实现
     * @return
     */
    public abstract View initView();

    /**
     * 绑定数据的时候，重写方法
     */
    public void initData(){}

}
