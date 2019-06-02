package test.kw.accountapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import test.kw.accountapp.R;

public class MainFragment extends Fragment {
    private TextView textView ;
    private ListView listView;
    private String data="";
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment,container,false);
        return rootView;
    }

    public void initView(){
        textView = rootView.findViewById(R.id.day_text);
        listView = rootView.findViewById(R.id.list_view);
        textView.setText(data);
    }
}
