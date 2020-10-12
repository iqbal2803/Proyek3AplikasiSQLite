package com.iqbal.proyek3aplikasisqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.iqbal.proyek3aplikasisqlite.Adapter.Adapter_Pegawai;
import com.iqbal.proyek3aplikasisqlite.Helper.DBHelper;
import com.iqbal.proyek3aplikasisqlite.Model.Model_Data;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Model_Data> itemList = new ArrayList<Model_Data>();
    Adapter_Pegawai adapter;
    DBHelper SQLite = new DBHelper(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Tambah SQLite
        SQLite = new DBHelper(getApplicationContext());
        listView = findViewById(R.id.rv_pegawai);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tambah Intent untuk pindah ke halaman Add dan Edit
                Intent intent = new Intent (MainActivity.this, Activity_AddEdit.class);
                startActivity(intent);
            }
        });

        //Tambah adapter dan listview
        adapter = new Adapter_Pegawai(MainActivity.this, itemList);
        listView.setAdapter(adapter);

        // tekan lama daftar listview untuk menampilkan edit dan hapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String nama = itemList.get(position).getNama();
                final String alamat = itemList.get(position).getAlamat();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(MainActivity.this, Activity_AddEdit.class);
                                intent.putExtra(TAG_ID,idx);
                                intent.putExtra(TAG_NAMA,nama);
                                intent.putExtra(TAG_ALAMAT,alamat);
                                startActivity(intent);
                                break;
                            case 1:
                            SQLite.delete(Integer.parseInt(idx));
                            itemList.clear();
                            getAllData();
                            break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();
    }

    private void getAllData(){
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();

        for(int i = 0; i< row.size(); i++){
            String id = row.get(i).get(TAG_ID);
            String nama = row.get(i).get(TAG_NAMA);
            String alamat = row.get(i).get(TAG_ALAMAT);

            Model_Data data = new Model_Data();
            data.setId(id);
            data.setNama(nama);
            data.setAlamat(alamat);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        itemList.clear();
        getAllData();
    }
}