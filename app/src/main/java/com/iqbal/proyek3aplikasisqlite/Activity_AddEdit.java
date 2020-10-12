package com.iqbal.proyek3aplikasisqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iqbal.proyek3aplikasisqlite.Helper.DBHelper;

public class Activity_AddEdit extends AppCompatActivity {
    EditText et_id, et_nama, et_alamat;
    Button btn_submit, btn_cancel;
    DBHelper SQLite = new DBHelper(this);
    String id, nama, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_id = findViewById(R.id.et_id);
        et_nama = findViewById(R.id.et_nama);
        et_alamat = findViewById(R.id.et_alamat);
        btn_submit = findViewById(R.id.btn_submit);
        btn_cancel = findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        nama = getIntent().getStringExtra(MainActivity.TAG_NAMA);
        alamat = getIntent().getStringExtra(MainActivity.TAG_ALAMAT);

        if(id == null || id == ""){
            setTitle("Add Data");
        }else{
            setTitle("Edit Data");
            et_id.setText(id);
            et_nama.setText(nama);
            et_alamat.setText(alamat);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(et_id.getText().toString().equals("")){
                        save();
                    }else{
                        edit();
                    }
                } catch (Exception e){
                    Log.e("Submit", e.toString());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //kosongkan semua editteks
    private void blank(){
        et_nama.requestFocus();
        et_id.setText(null);
        et_nama.setText(null);
        et_alamat.setText(null);
    }

    // menyimpan data ke sqlite
    private void save(){
        if(String.valueOf(et_nama.getText()).equals(null) || String.valueOf(et_nama.getText()).equals("")){
            Toast.makeText(getApplicationContext(),"Harap Masukkan Nama atau Alamat", Toast.LENGTH_SHORT).show();
        }else{
            SQLite.insert(et_nama.getText().toString().trim(),et_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }

    // update data ke sqlite
    private void edit(){
        if(String.valueOf(et_nama.getText()).equals(null) || String.valueOf(et_nama.getText()).equals("")){
            Toast.makeText(getApplicationContext(),"Harap Masukkan Nama atau Alamat", Toast.LENGTH_SHORT).show();
        }else{
            SQLite.update(Integer.parseInt(et_id.getText().toString().trim()),et_nama.getText().toString().trim(),et_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }
}
