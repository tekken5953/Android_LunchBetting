package com.example.lunchbetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class Setting extends AppCompatActivity {
    EditText edt1;
    EditText edt2;
    EditText edt3;
    ArrayList<EditText> alledt = new ArrayList<EditText>();
    TextView tv;
    Button btn1;
    Button btn2;
    String st1;
    String st2;
    LinearLayout linearLayout;
    Button cancelbtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        linearLayout = findViewById(R.id.list);
        btn1 = findViewById(R.id.createBtn);
        btn2 = findViewById(R.id.cancelBtn);
        edt1 = findViewById(R.id.countEdt);
        edt2 = findViewById(R.id.maxEdt);
        cancelbtn = findViewById(R.id.cancelBtn);

        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                linearLayout.removeAllViewsInLayout();
                alledt.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                st1=edt1.getText().toString();
                if((st1.equals(""))) { st1 = "0"; }
                for(int z=0;z<(Integer.parseInt(st1));z++) {
                    tv = new TextView(linearLayout.getContext());
                    edt3 = new EditText(linearLayout.getContext());
                    edt3.setId(z);
                    alledt.add(edt3);
                    st2 = String.valueOf(z+1);
                    tv.setText(st2+"번째 :");
                    linearLayout.addView(tv);
                    linearLayout.addView(edt3);
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RulletRun.class);
                for (int z = 0; z < Integer.parseInt(edt1.getText().toString()); z++) {
                    intent.putExtra("" + z, alledt.get(z).getText().toString());
                }
                intent.putExtra("price",edt2.getText().toString());
                intent.putExtra("last", alledt.size());
                startActivity(intent);
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
            }
        });
    }
}
