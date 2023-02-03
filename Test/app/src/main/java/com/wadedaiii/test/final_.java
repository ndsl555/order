package com.wadedaiii.test;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.internal.dns.DefaultDnsResolver;
import org.bson.Document;

public class final_ extends AppCompatActivity {
    TextView tv;
    Button btn_send;
    String word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_final);
        tv=findViewById(R.id.textView2);
        btn_send=findViewById(R.id.send);
        Bundle bundle3=getIntent().getExtras();
        String s=bundle3.getString("okk");
        String money=bundle3.getString("money2");
        tv.setText(s+"\n總共:"+money+"元");
        word="";
        //tv.setText(money);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendEmail();
            }
        });

    }
    private void sendEmail() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("user01");
        ref.removeValue();
        String Email="alan282403@gmail.com" ;
        String Subject="陳時中請客" ;
        Bundle bundle7=getIntent().getExtras();
        String s=bundle7.getString("okk");
        String money=bundle7.getString("money2");

        word+="小鳥食物:";
        word+='\n';
        word+=s;
        word+='\n';
        word+="共";
        word+= money;
        word+="元";
        word+='\n';
        word+="這些錢你不用付,但請不要抱怨沒打到第一劑 啾咪!";
        addToDatabase(word);
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,Email,Subject,word);

        javaMailAPI.execute();
    }
    private static void addToDatabase(String username){
        MongoClientURI uri  = new MongoClientURI("mongodb+srv://adam:10217633@cluster0.ajm6o.mongodb.net/?retryWrites=true&w=majority");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        MongoCollection<Document> coll = db.getCollection("newDB");

        Document doc = new Document("username", username);
        coll.insertOne(doc);
        client.close();
    }
}