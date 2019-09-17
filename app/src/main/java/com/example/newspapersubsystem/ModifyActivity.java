package com.example.newspapersubsystem;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ModifyActivity extends AppCompatActivity {
    EditText mads;
    EditText mpas;
    Button mbtnad;
    String ok="修改成功";
    private String TAG="ModifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        mads=findViewById(R.id.madre);
        mpas=findViewById(R.id.tps);
        mbtnad=findViewById(R.id.moadbtn);

        mads.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    int len=mads.getText().toString().length();
                    if(len==0){
                        mads.setError(getString(R.string.infom));
                    }
                }
            }
        });

        mpas.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    int len=mpas.getText().toString().length();
                    if(len==0){
                        mpas.setError(getString(R.string.infom1));
                    }else if(!URL.pas.equals(mpas.getText().toString())){
                        mpas.setError(getString(R.string.infom2));
                    }
                }
            }
        });
        mbtnad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int len=mpas.getText().toString().length();
                int len1=mads.getText().toString().length();
                if(len1==0){
                    mads.setError(getString(R.string.infom));
                }else if(len==0){
                    mpas.setError(getString(R.string.infom1));
                }else if(!URL.pas.equals(mpas.getText().toString())){
                    mpas.setError(getString(R.string.infom2));
                }else {
                    String url = "MdadsServlet";
                    url = URL.URL + url + "?adress=" + mads.getText().toString();
                    final RequestQueue mQueue = Volley.newRequestQueue(ModifyActivity.this);
                    StringRequest srq = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            finish();
                            Toast.makeText(ModifyActivity.this, s + "", Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d(TAG, volleyError.getMessage());
                        }
                    });
                    mQueue.add(srq);
                }
            }
        });
    }
}
