package test.kw.accountapp.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

import test.kw.accountapp.AddRecordActivity;
import test.kw.accountapp.R;
import test.kw.accountapp.RecordBean;
import test.kw.accountapp.adapter.ListViewAdapter;
import test.kw.accountapp.util.DateUtil;
import test.kw.accountapp.util.GlobalUtil;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements AdapterView.OnItemLongClickListener {
    private TextView textView ;
    private ListView listView;
    private String data="";
    private View rootView;

    private LinkedList<RecordBean> linkedList = new LinkedList<>();;

    private ListViewAdapter listViewAdapter;
    @SuppressLint("ValidFragment")
    public MainFragment(String date){
        this.data = date;
        linkedList = GlobalUtil.getInstance().datebaseHelper.readRecord(data);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment,container,false);
        initView();
        return rootView;
    }

    public void initView(){
        textView = rootView.findViewById(R.id.day_text);
        listView = rootView.findViewById(R.id.list_view);

        listViewAdapter = new ListViewAdapter(getContext());
        listView.setAdapter(listViewAdapter);
        listViewAdapter.setData(linkedList);
        if(listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record).setVisibility(View.GONE);
        }
        textView.setText(DateUtil.getDateTitle(data));

        listView.setOnItemLongClickListener(this);
    }

    public void reload(){
        linkedList = GlobalUtil.getInstance().datebaseHelper.readRecord(data);
        if (listViewAdapter == null){
            listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext());
        }
        listViewAdapter.setData(linkedList);
        listView.setAdapter(listViewAdapter);
        if (listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record).setVisibility(View.INVISIBLE);
        }
    }

    public int getTotalCost(){
        double totalCost = 0;
        for (RecordBean recordBean:linkedList){
            if (recordBean.getRecordType()==1){
                totalCost+=recordBean.getAmount();
            }
        }
        return (int)totalCost;

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("AA",position+"");
        showDialog(position);
        return true;
    }

    public void showDialog(final int index) {
        final String [] options ={"Remove","Edit"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final RecordBean recordBean = linkedList.get(index);
        builder.create();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    String uuid = recordBean.getUuid();
                    GlobalUtil.getInstance().datebaseHelper.removeRecord(uuid);
                    reload();
                }else if (which==1){
                    Intent intent = new Intent(getActivity(),AddRecordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("record",recordBean);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }
            }
        });
        builder.setNegativeButton("cancel",null);
        builder.create().show();
    }
}