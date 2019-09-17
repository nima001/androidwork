package com.example.newspapersubsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdupbookActivity extends AppCompatActivity {
    String URL2="",result="",id="";
    Button upbtn;
    EditText bookid,newsname,username,name,booknum,pricenum,adress;
    private String TAG="AdupbookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adupbook);

        upbtn=findViewById(R.id.adupdatebtn);
        bookid=findViewById(R.id.adupbid);
        newsname=findViewById(R.id.adupnewsname);
        username=findViewById(R.id.adupusername);
        name=findViewById(R.id.adupname);
        booknum=findViewById(R.id.adupbooknum);
        pricenum=findViewById(R.id.aduppricenum);
        adress=findViewById(R.id.adupadress);

        Intent it=getIntent();
        it.getStringExtra("bookid");

        URL2=URL.URL+"AdupbookServlet?bookid="+it.getStringExtra("bookid");
        final RequestQueue mQueue= Volley.newRequestQueue(AdupbookActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL2, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        id=temp.getString("id");
                        bookid.setText(temp.getString("bookid"));
                        newsname.setText(temp.getString("newsname"));
                        username.setText(temp.getString("username"));
                        name.setText(temp.getString("name"));
                        booknum.setText(temp.getString("booknum"));
                        pricenum.setText(temp.getString("pricenum"));
                        adress.setText(temp.getString("adress"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("发生了一个错误！");
                error.printStackTrace();
            }
        });
        mQueue.add(jsonArrayRequest);

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL3=URL.URL+"AdupbookServlet1?id="+id+"&bookid="+bookid.getText().toString()+
                       "&newsname="+newsname.getText().toString()+"&username="+username.getText().toString()+
                        "&name="+name.getText().toString()+"&booknum="+booknum.getText().toString()+"&pricenum="+pricenum.getText().toString()+
                        "&adress="+adress.getText().toString();

                StringRequest srq = new StringRequest(URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("修改成功")) {
                            result = "修改成功";
                        } else {
                            result = "修改失败";
                        }

                        if(s.equals("没有该报刊")){
                            result="没有该报刊";
                        }
                        Toast.makeText(AdupbookActivity.this,result+"",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, volleyError.getMessage());
                    }
                });
                mQueue.add(srq);
            }
        });
    }
}
