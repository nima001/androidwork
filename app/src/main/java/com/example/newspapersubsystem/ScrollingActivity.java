package com.example.newspapersubsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

public class ScrollingActivity extends AppCompatActivity {
    TextView con;
    CollapsingToolbarLayout lay;
    String newsname,date,content,publish,newsnum;
    String price;
    String URL3=URL.URL;
    String ok="";
    String  id;
    Button minbtn,addbtn,mupbtn,mdelbtn;
    EditText bnum;
    FloatingActionButton fbtn;
    String name="";
    private String TAG="ScrollingActivity";
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        mupbtn=findViewById(R.id.adupnews);
        mdelbtn=findViewById(R.id.addelnews);
        minbtn=findViewById(R.id.minbtn);
        addbtn=findViewById(R.id.addbtn);
        bnum=findViewById(R.id.bonum);
        fbtn=findViewById(R.id.fab);
        if(URL.username.equals("admin")){
            addbtn.setVisibility(View.GONE);
            minbtn.setVisibility(View.GONE);
            bnum.setVisibility(View.GONE);
            fbtn.setVisibility(View.GONE);
            mupbtn.setVisibility(View.VISIBLE);
            mdelbtn.setVisibility(View.VISIBLE);
        }


        minbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(bnum.getText().toString())>1) {
                    bnum.setText(Integer.parseInt(bnum.getText().toString()) - 1 + "");
                }
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnum.setText(Integer.parseInt(bnum.getText().toString())+1+"");
            }
        });

        con=findViewById(R.id.content);
        lay=findViewById(R.id.toolbar_layout);
        Intent it=getIntent();
        String URL2=URL.URL;

        URL2=URL2+"NewsByidServlet?id="+ Integer.parseInt(it.getStringExtra("id"));

        final RequestQueue mQueue= Volley.newRequestQueue(ScrollingActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL2, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Map<String,Object>> mList=new ArrayList<Map<String,Object>>();
                    for(int i=0;i<response.length();i++) {
                        Map<String ,Object> map=new HashMap<String,Object>();
                        JSONObject temp= (JSONObject) response.get(i);
                        id=temp.getString("id");
                        newsnum=temp.getString("newsnum");
                        newsname=temp.getString("newsname");
                        price=temp.getString("price");
                        date=temp.getString("date");
                        content=temp.getString("content");
                        publish=temp.getString("publish");
                        con.setText("报刊简介:\n\n"+temp.getString("content")+"\n\n出版日期："+temp.getString("date")+"\n\n出版社："+temp.getString("publish"));
                        lay.setTitle(temp.getString("newsname")+temp.getString("price")+"￥");
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                    URL3 = URL3 + "SubscribeServlet?newsname='" + newsname + "'&username=" + URL.username + "&booknum=" + bnum.getText().toString();
                    StringRequest srq = new StringRequest(URL3, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                            Snackbar.make(view, s + "", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

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

        mupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ScrollingActivity.this,UpNewsActicity.class);
                it.putExtra("id",id);
                it.putExtra("newsnum",newsnum);
                it.putExtra("newsname",newsname);
                it.putExtra("price",price);
                it.putExtra("date",date);
                it.putExtra("content",content);
                it.putExtra("publish",publish);
                startActivity(it);
                finish();
            }
        });
        mdelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=URL.URL+"DelnewsServlet?id="+id;
                StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (s.equals("删除失败")) {
                            ok = "删除失败";
                        } else {
                            ok = "删除成功";
                        }
                        Toast.makeText(ScrollingActivity.this,ok+"",Toast.LENGTH_LONG).show();
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
