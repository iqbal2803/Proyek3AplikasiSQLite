package com.iqbal.proyek3aplikasisqlite.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iqbal.proyek3aplikasisqlite.Model.Model_Data;
import com.iqbal.proyek3aplikasisqlite.R;

import java.util.List;

public class Adapter_Pegawai extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Model_Data> items;

    public Adapter_Pegawai(Activity activity, List<Model_Data> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int location){
        return items.get(location);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_row,null);

        TextView id = convertView.findViewById(R.id.tv_id);
        TextView nama = convertView.findViewById(R.id.tv_nama);
        TextView alamat = convertView.findViewById(R.id.tv_alamat);

        Model_Data data = items.get(position);
        id.setText(data.getId());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());

        return convertView;
    }




}
