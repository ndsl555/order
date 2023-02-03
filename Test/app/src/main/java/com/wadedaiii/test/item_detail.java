package com.wadedaiii.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;
public class item_detail extends AppCompatActivity{

    FloatingActionButton quantity_up,quantity_down,btn_back;
    TextView pd_name,pd_quantity;
    Button btn_send;
    int quantity_pd;
    int total;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        pd_name = findViewById(R.id.tv_pd_name);
        pd_quantity = findViewById(R.id.pd_quantity);
        quantity_down = findViewById(R.id.quantity_down);
        quantity_up = findViewById(R.id.quantity_up);
        btn_send = findViewById(R.id.btn_send_pd);
        quantity_pd = 1;
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference myref= db.getReference("Users");//father node

        final DatabaseReference myref1= myref.child("user01");
        //隱藏標題
        getSupportActionBar().hide();

        Bundle bundle=getIntent().getExtras();
        String item_name=bundle.getString("item_name");
        Integer item_price=bundle.getInt("item_price");


        pd_name.setText("\t\t"+item_name+"\t\t\t\t\t\t\t\t\t\t\tNT"+item_price.toString()+"\t\t");
        btn_send.setText("加入"+quantity_pd+"份至購物車‧$"+item_price);
        //減號disabled,enabled切換
        if(quantity_pd == 1)
            quantity_down.setEnabled(false);
        else
            quantity_down.setEnabled(true);

        quantity_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_pd += 1;
                pd_quantity.setText(String.valueOf(quantity_pd));

                //減號disabled,enabled切換
                if(quantity_pd == 1)
                    quantity_down.setEnabled(false);
                else
                    quantity_down.setEnabled(true);
                total=item_price*quantity_pd;
                btn_send.setText("加入"+quantity_pd+"份至購物車‧$"+total);
            }
        });
        quantity_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity_pd > 1)
                    quantity_pd -= 1;

                //減號disabled,enabled切換
                if(quantity_pd == 1)
                    quantity_down.setEnabled(false);
                else
                    quantity_down.setEnabled(true);
                pd_quantity.setText(String.valueOf(quantity_pd));
                total=item_price*quantity_pd;
                btn_send.setText("加入"+quantity_pd+"份至購物車‧$"+total);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total = quantity_pd * item_price;
                Intent intent = new Intent(item_detail.this,stores.class);
                Bundle bundle1=new Bundle();
                String amount= ""+quantity_pd;
                String toa=""+total;
                String buf=amount+"份"+item_name+":"+toa+"NT";
                myref1.push().child("").setValue(buf);
                myref1.push().child("").setValue(toa);
                myref1.addValueEventListener(new ValueEventListener() {
                    String s="";
                    Integer money =0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for (DataSnapshot child : snapshot.getChildren()) {
                                if (child.getKey()!="storeName"){
                                    String key = child.getKey();
                                    String check= String.valueOf(snapshot.child(key).getValue());
                                    if (isNumeric(check)) {
                                        money+=Integer.valueOf(check);
                                       // System.out.println(money);
                                    }
                                    else {
                                    s+=check;
                                    s+='\n';
                                    }
                                }
                            }
                        }
                        String money2=""+money;
                        //System.out.println(money2);
                        bundle1.putString("ok",s);
                        bundle1.putString("nt",money2);
                        bundle1.putBoolean("car_enable",true);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }
    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();

    }


}
