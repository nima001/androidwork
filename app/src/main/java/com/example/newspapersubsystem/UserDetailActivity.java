package com.example.newspapersubsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailActivity extends AppCompatActivity {
    TextView usercontent;
    CollapsingToolbarLayout lay;
    FloatingActionButton userfab;
    String id="",name="",IDcard="",username1="",password="",phone="",adress="",result="";
    private String TAG="UserDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        usercontent=findViewById(R.id.usercontent);
        lay=findViewById(R.id.toolbar_layout);
        userfab=findViewById(R.id.userfab);
        final RequestQueue mQueue= Volley.newRequestQueue(UserDetailActivity.this);
        userfab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String URL2=URL.URL+"DelUserServlet?id="+id;
                StringRequest srq = new StringRequest(URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(UserDetailActivity.this,s+"",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, volleyError.getMessage());
                    }
                });
                mQueue.add(srq);
                return true;
            }
        });

        userfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(UserDetailActivity.this,AMUserActivity.class);
                it.putExtra("id",id);
                it.putExtra("name",name);
                it.putExtra("IDcard",IDcard);
                it.putExtra("username1",username1);
                it.putExtra("password",password);
                it.putExtra("phone",phone);
                it.putExtra("adress",adress);
                startActivity(it);
                finish();
            }
        });

        Intent it=getIntent();
        String URL1=URL.URL+"UserlistbyidServlet?id="+it.getStringExtra("id");


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL1, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        id=temp.getString("id");
                        name=temp.getString("name");
                        IDcard=temp.getString("IDcard");
                        username1=temp.getString("username");
                        password=temp.getString("password");
                        phone=temp.getString("phone");
                        adress=temp.getString("adress");
                        usercontent.setText("姓名:\n\n"+temp.getString("name")+"\n\n身份证号："+temp.getString("IDcard")+
                                "\n\n密码："+temp.getString("password")+"\n\n电话:"+temp.getString("phone")+
                                "\n\n地址："+temp.getString("adress"));
                        lay.setTitle(temp.getString("username"));
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

    }
}
