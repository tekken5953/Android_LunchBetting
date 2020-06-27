package com.example.lunchbetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;





public class StackResult extends AppCompatActivity {

    LinearLayout stacklayout;
    DBHelper dbHelper;
    Button deletebtn,outbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_result);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }



        deletebtn = (Button)findViewById(R.id.deletestackbtn);
        outbtn = (Button)findViewById(R.id.outstackbtn);

        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.selectAll();

        final Intent it = getIntent();
        final String stack = it.getStringExtra("result");


        boolean result = dbHelper.insertData(stack);

        if (result) {
        } else {
        }

        String name = "";
        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex("name"));
            stacklayout = (LinearLayout) findViewById(R.id.stacklayout);

            final LinearLayout layout = new LinearLayout(this);
            final TextView stackgroup = new TextView(this);


            layout.setOrientation(LinearLayout.VERTICAL);
            stackgroup.setText(name);

            stackgroup.setPadding(40, 30, 0, 15);
            stackgroup.setTextSize(20);

            layout.addView(stackgroup);
            stacklayout.addView(layout);
        }

        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),StackDel.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    }

