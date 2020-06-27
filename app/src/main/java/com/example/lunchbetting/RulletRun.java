package com.example.lunchbetting;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;

public class RulletRun extends AppCompatActivity {
    Button btn_start,btn_end;
    PieChart pieChart;
    ImageView arrowimg;
    TextView returntv;
    TextView price;
    ArrayList<TextView> a_party,a_price = new ArrayList<TextView>();
    float a=0;
    int remain;
    float turn;
    String player = new String();
    String rtplayer = new String();
    int num = 0;
    boolean cheat;
    int chch;



    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    long mNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rullet_run);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }



        btn_start = (Button)findViewById(R.id.startbtn);
        btn_end = (Button)findViewById(R.id.endbtn);
        pieChart = (PieChart)findViewById(R.id.wheel);

        arrowimg = (ImageView)findViewById(R.id.arrow);
        returntv = (TextView)findViewById(R.id.returntv);
        price = (TextView)findViewById(R.id.price);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.setExtraOffsets(7,7,7,7);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(1f);
        pieChart.setEntryLabelColor(Color.BLACK);

        final EditText et_price = new EditText(this);
        final EditText et_party = new EditText(this);


        for( int z = 0;z<getIntent ().getExtras().getInt("last"); z++)
        {
           // player.add(z,getIntent().getExtras().getString(""+z));

           player = (getIntent().getExtras().getString(""+z)+ " ");
           rtplayer += player;
        }

        arrowimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                if ( num > getIntent().getExtras().getInt("last") )
                {
                    Toast.makeText(getApplicationContext(),"그만",Toast.LENGTH_SHORT);
                    num--;
                }
                Toast.makeText(getApplicationContext(),"무조건 " + num + "번 이 걸립니다.",Toast.LENGTH_SHORT).show();

            }
        });

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        for(int z=0;z<getIntent().getExtras().getInt("last");z++)
        {
            yValues.add(new PieEntry(1f, getIntent().getExtras().getString(""+z)));
        }

        PieDataSet dataSet = new PieDataSet(yValues,null);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(0f);
        pieChart.setData(data);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();

                    turn = random.nextInt(10000 - 8000 + 1) + 8000;
                    chch = random.nextInt((360 / getIntent().getExtras().getInt("last")-10)-10+1)+10;

                  if (num != 0) {
                              turn = (360 * 30) - (360 / getIntent().getExtras().getInt("last")) * (num - 1) - chch;
                  }

                    remain = (int) ((turn % 360) / (360 / getIntent().getExtras().getInt("last")));

                    Animation anim = new RotateAnimation(a, turn, pieChart.getWidth() / 2, pieChart.getHeight() / 2);
                    anim.setDuration(7000);
                    anim.setFillAfter(true);
                    anim.setInterpolator(new DecelerateInterpolator());
                    pieChart.startAnimation(anim);

                    a = turn;
                    turn += turn;

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            returntv.setText(null);
                            price.setText(null);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            returntv.setText(getIntent().getExtras().getString("" + (getIntent().getExtras().getInt("last") - remain - 1)) + "! 니가쏴라!");
                            price.setText("가격은 " + getIntent().getExtras().getString("price") + "원 까지만....");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(RulletRun.this);
                                    Context context = alert.getContext();
                                    LinearLayout layout = new LinearLayout(context);
                                    layout.setOrientation(LinearLayout.VERTICAL);
                                    alert.setTitle("룰렛 결과 저장");
                                    alert.setMessage("결과를 저장하려면 아래를 입력해주세요"); //저장 여부 표시
                                    layout.addView(et_party); // 그룹명 Edit 생성
                                    layout.addView(et_price); // 결제금액 Edit 생성

                                    et_party.setHint("그룹 명");
                                    et_party.setScaleX((float) 0.8);
                                    et_price.setHint("총 결제금액");
                                    et_price.setScaleX((float) 0.8);

                                    alert.setView(layout);
                                    alert.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(getApplicationContext(), StackResult.class);
                                            intent.putExtra("result", "날짜 :  " + getTime() + "\n그룹 명 :  " + et_party.getText().toString() +
                                                    "\n참여 인원 :  " + rtplayer +
                                                    "\n총 결제금액 :  " + et_price.getText().toString() +
                                                    "\n걸린사람 :  " + getIntent().getExtras().getString("" + (getIntent().getExtras().getInt("last") - remain - 1))
                                                    + "\n\n");//저장 할 내용 intent
                                            Toast.makeText(getApplicationContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show(); //저장 완료메세지 출력
                                            startActivity(intent);
                                            finish();

                                        }
                                    });
                                    alert.setNegativeButton("저장안함", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Toast.makeText(getApplicationContext(), "저장에 실패하셨습니다\n룰렛을 종료합니다", Toast.LENGTH_SHORT).show(); //저장 실패메세지 출력
                                            dialogInterface.dismiss(); //다이얼로그 종료
                                            Intent intent_out = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent_out); //메인메뉴로 이동
                                            finish();
                                        }
                                    });
                                    alert.show();
                                }
                            }, 1000);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

            }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RulletRun.this,MainActivity.class);
                startActivity(intent1);
                finish(); }});
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}