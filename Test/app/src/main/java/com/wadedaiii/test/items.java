package com.wadedaiii.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class items extends AppCompatActivity {
    TextView tv_StoreName;
    Button btn_info,btn_send_order;
    ImageView img_store_scene;
    ListView lv_menu;
    ArrayAdapter<String> mAdapter;
    ArrayList<String> name_price = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        tv_StoreName = findViewById(R.id.tv_store_name);
        img_store_scene =findViewById(R.id.img_store_highlight);
        //img_store_scene.setImageResource(R.drawable.teaparty);
        lv_menu = findViewById(R.id.lv_menu);
        btn_info = findViewById(R.id.btn_store_info);
        tv_StoreName.setText("");
        //抓取商店頁的數據 取得商家item
        Bundle bundle=getIntent().getExtras();
        String store_name = bundle.getString("store_name");
        ArrayList<String>items = bundle.getStringArrayList("item_name");
        ArrayList<Integer>item_price = bundle.getIntegerArrayList("item_price");
        //隱藏標題
        getSupportActionBar().hide();

        //store information
        btn_info.setText("點選以查看營業時間、地址等資訊");
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        name_price.clear();
        for(int i=0;i<items.size();i++)
        {
            name_price.add(items.get(i)+"      $NT"+item_price.get(i));
        }

        tv_StoreName.setText(store_name);

        mAdapter = new ArrayAdapter<>(com.wadedaiii.test.items.this, android.R.layout.simple_list_item_1,name_price);
        lv_menu.setAdapter(mAdapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent = new Intent(com.wadedaiii.test.items.this, item_detail.class);
                bundle.putString("item_name",items.get(pos));
                bundle.putInt("item_price",item_price.get(pos));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

}