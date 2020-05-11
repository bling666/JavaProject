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
import android.os.StrictMode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

import java.io.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class search_activity extends AppCompatActivity {

    private Button bsearch;
    private EditText myword;
    private TextView mtv;
    String getstr;
    public String searchword(String s){
        HttpClient client = new DefaultHttpClient();
        BufferedReader reader = null;
        StringBuffer sb = null;
        String result = "";
        String path="http://39.102.62.210//api/getoneword?keyword=";
        path=path+s;
        HttpGet request=new HttpGet(path);
        try{
            HttpResponse response=client.execute(request);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                reader=new BufferedReader((new InputStreamReader((response.getEntity().getContent()))));
                sb=new StringBuffer();
                //String line="";
                result=reader.readLine();
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
            return "error";
        }catch (IOException e){
            e.printStackTrace();
            return "error";
        }
        return result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        mtv=(TextView)findViewById(R.id.res);
        bsearch=(Button)findViewById(R.id.btn_search);
        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getstr=myword.getText().toString();
                String res=searchword(getstr);
                mtv.setText(res);
                Toast.makeText(search_activity.this,"输入成功",Toast.LENGTH_LONG).show();
            }
        });
        myword=(EditText)findViewById(R.id.word);
        myword.addTextChangedListener(new TextWatcher() {
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
