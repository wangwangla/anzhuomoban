package test.kw.mobileplayer.frament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.kw.mobileplayer.base.BasePager;

public class MyFragment extends Fragment {
    public MyFragment(){

    }
    private BasePager currPager;

    @SuppressLint("ValidFragment")
    public MyFragment(BasePager pager) {
        this.currPager=pager;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return currPager.rootview;
    }

}
