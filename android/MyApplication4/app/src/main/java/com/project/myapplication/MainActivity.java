package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button MBUTTON;
    private  Button edit;
    private  Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBUTTON=(Button) findViewById(R.id.button);
        MBUTTON.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //跳转到button界面
                android.content.Intent intent = new android.content.Intent(MainActivity.this,buttonActivity.class);
                startActivity(intent);
            }
        });
        edit=(Button) findViewById(R.id.button_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this,editactivity.class);
                startActivity(intent);
            }
        });
        search=(Button) findViewById(R.id.button2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this,search_activity.class);
                startActivity(intent);
            }
        });
    }
}
