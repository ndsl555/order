package com.wadedaiii.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class stores extends AppCompatActivity {
    ArrayList<String> store_list = new ArrayList<>();
    ArrayList<String> item_name = new ArrayList<>();
    ArrayList<Integer> item_price = new ArrayList<>();
    ListView store_lv;
    ArrayAdapter<String> mAdapter_store;
    JSONObject jsonObject;
    JSONArray storeArray;
    Button delivery_addr_btn,btn_car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        delivery_addr_btn = findViewById(R.id.delivery_addr);
        store_lv = findViewById(R.id.lv_store);
        btn_car=findViewById(R.id.car);
        //隱藏標題
        getSupportActionBar().hide();
        try {
             jsonObject= new JSONObject(getResources().getText(R.string.store_items).toString());
             storeArray= jsonObject.getJSONArray("store");
            for (int i=0;i<storeArray.length();i++) {
                store_list.add((String)storeArray.getJSONObject(i).get("store_name"));
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        mAdapter_store = new ArrayAdapter<>(stores.this, android.R.layout.simple_list_item_1,store_list);
        store_lv.setAdapter(mAdapter_store);
        store_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(stores.this, items.class);
                Bundle bundle = new Bundle();
                try {
                    bundle.putString("store_name",store_list.get(position));
                    JSONArray store_items = (JSONArray)storeArray.getJSONObject(position).get("items");
                    for(int i=0;i<store_items.length();i++)
                    {
                        item_name.add((String) store_items.getJSONObject(i).get("name"));
                        item_price.add((int) store_items.getJSONObject(i).get("price"));
                    }
                    bundle.putStringArrayList("item_name",item_name);
                    bundle.putIntegerArrayList("item_price",item_price);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtras(bundle);
                startActivity(intent);
                //清除前筆商品資訊
                item_name.clear();
                item_price.clear();
            }
        });
        btn_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stores.this,final_.class);
                Bundle bundle4=getIntent().getExtras();
                Bundle bundle5=new Bundle();
                String s=bundle4.getString("ok");

                bundle5.putString("okk",s);
                String money=bundle4.getString("nt");
                bundle5.putString("money2",money);
                intent.putExtras(bundle5);
                startActivity(intent);
            }
        });


    }
}
/*
String store_name,item_name;
int item_price;
JSONArray store_items;
store_items = (JSONArray) jsonArray.getJSONObject(i).get("items");
for(int j=0;j<store_items.length();j++){
    item_name = (String) store_items.getJSONObject(j).get("name");
    item_price = (int) store_items.getJSONObject(j).get("price");
}*/