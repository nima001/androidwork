package com.example.newspapersubsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookByuserActivity extends AppCompatActivity {
    TextView con;
    CollapsingToolbarLayout lay;
    FloatingActionButton fab;
    String URL2=URL.URL;
    String bookid;
    String cancelOK="",result="";
    Button cancelbtn,addelbook,adupbook;
    private String TAG="BookByuserActivity";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbyuser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addelbook=findViewById(R.id.addelbook);
        adupbook=findViewById(R.id.adupbook);
        cancelbtn=findViewById(R.id.cancelbtn);
        con=findViewById(R.id.content);
        lay=findViewById(R.id.toolbar_layout);
        fab=findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        if(URL.username.equals("admin")){
            cancelbtn.setVisibility(View.GONE);
            addelbook.setVisibility(View.VISIBLE);
            adupbook.setVisibility(View.VISIBLE);
        }

        Intent intent=getIntent();
        URL2=URL2+"BookByidServlet?id="+intent.getStringExtra("id");
        final RequestQueue mQueue= Volley.newRequestQueue(BookByuserActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL2, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        bookid=temp.getString("bookid");
                        con.setText("用户名:"+temp.getString("username")+"\n\n报刊名:"+temp.getString("newsname")+"\n\n订单号："+temp.getString("bookid")+"\n\n订阅数："+temp.getString("booknum")+"\n\n总价:"+temp.getString("pricenum")+"\n\n收货地址："+temp.getString("adress"));
                        lay.setTitle("订阅人"+temp.getString("name"));
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL3="";
                URL3 = URL.URL + "CancelServlet?bookid="+bookid;

                StringRequest srq = new StringRequest(URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("取消成功")) {
                            cancelOK = "取消失败";
                        } else {
                            cancelOK = "取消成功";
                        }
                        Toast.makeText(BookByuserActivity.this,cancelOK+"",Toast.LENGTH_LONG).show();
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

        adupbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(BookByuserActivity.this,AdupbookActivity.class);
                it.putExtra("bookid",bookid);
                startActivity(it);
            }
        });

        addelbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL3=URL.URL+"AdDelbookServlet?bookid="+bookid;
                StringRequest srq = new StringRequest(URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("删除成功")) {
                            result = "删除成功";
                        } else {
                            result = "删除失败";
                        }
                        Toast.makeText(BookByuserActivity.this,result+"",Toast.LENGTH_LONG).show();
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
