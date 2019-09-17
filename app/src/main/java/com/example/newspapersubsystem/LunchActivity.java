package com.example.newspapersubsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LunchActivity extends AppCompatActivity {
    Button mloginBtn;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        mloginBtn=findViewById(R.id.skip);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(pref.getString("flag","").equals("1")){
             URL.username = pref.getString("n1","");
            Toast.makeText(LunchActivity.this,""+URL.username,Toast.LENGTH_LONG).show();
            Intent it=new Intent(LunchActivity.this,IndexActivity.class);
            it.putExtra("URL1",URL.URL);
            startActivity(it);
            finish();
        }
        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LunchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
