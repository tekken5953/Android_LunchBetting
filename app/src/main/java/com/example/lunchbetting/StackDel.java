package com.example.lunchbetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class StackDel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String	name = intent.getStringExtra("name");
        DBHelper	dbManager	=	new DBHelper(this);
        boolean	result	=	dbManager.deleteData(name);

        if(result){
            Intent	intent1	=	new	Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent1);
            finish();
            Toast.makeText(getApplicationContext(),"결과가 모두 삭제되었습니다",	Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"삭제에 실패했습니다",	Toast.LENGTH_LONG).show();
        }
    }
}
