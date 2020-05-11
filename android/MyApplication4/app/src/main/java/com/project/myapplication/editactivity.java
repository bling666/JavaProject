package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.util.Log.d;

public class editactivity extends AppCompatActivity {
    private Button mbtnlogin;
    private EditText myusername;
    private TextView mtv;
    String getinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivity);
        mtv=(TextView)findViewById(R.id.tv1);
        mbtnlogin=(Button)findViewById(R.id.btn_login);
        mbtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getinfo=myusername.getText().toString();
                mtv.setText(getinfo);
                Toast.makeText(editactivity.this,"输入成功",Toast.LENGTH_LONG).show();
            }
        });
        myusername=(EditText)findViewById(R.id.et_1);
        myusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                android.util.Log.d("edittext",s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
