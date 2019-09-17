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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpNewsActicity extends AppCompatActivity {
    EditText upnewsnum,upnewsname,upprice,upDate,upcontent,uppublish;
    Button subtn;
    String ok;
    String id;
    private String TAG="UpNewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_news_acticity);
        upnewsnum=findViewById(R.id.upnewsnum);
        upnewsname=findViewById(R.id.upnewsname);
        upprice=findViewById(R.id.upprice);
        upDate=findViewById(R.id.upndate);
        upcontent=findViewById(R.id.upcontent);
        uppublish=findViewById(R.id.uppublish);
        subtn=findViewById(R.id.upnewsub);
        Intent it=getIntent();

        id= it.getStringExtra("id");

        upnewsnum.setText(it.getStringExtra("newsnum"));
        upnewsname.setText( it.getStringExtra("newsname"));
        upprice.setText( it.getStringExtra("price"));
        upDate.setText(it.getStringExtra("date"));
        upcontent.setText( it.getStringExtra("content"));
        uppublish.setText( it.getStringExtra("publish"));

        final RequestQueue mQueue= Volley.newRequestQueue(UpNewsActicity.this);
        subtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=URL.URL+"UpnewsServlet?id="+id+"&newsnum="+upnewsnum.getText().toString()+"&newsname="+upnewsname.getText().toString()+
                        "&price="+upprice.getText().toString()+"&date="+upDate.getText().toString()+"&content="+upcontent.getText().toString()+
                        "&publish="+uppublish.getText().toString();

                StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(UpNewsActicity.this,s+"",Toast.LENGTH_LONG).show();
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
