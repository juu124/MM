package com.example.mm3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    AlarmFragment alarmFragment = new AlarmFragment();
    TempTurbFragment tempTurbFragment = new TempTurbFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Alarm 예정 " +response , Toast.LENGTH_LONG).show();

                /*try {
                    JSONObject jsonObject = new JSONObject(response); // 받은 데이터 json 으로 받기

                    //json 데이터 화면에 출력
//                    fish_name.setText(jsonObject.getString("name"));
//                    fish_lifespan.setText(jsonObject.getString("lifespan"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        };

        // 서버 송수신 코드
       // alarmRequest alarmRequest = new alarmRequest(hou, minut, amp, responseListener);
       // RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
       // queue.add(alarmRequest);


        //맨위에 타이틀바 제거하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //주어진 시간에 경고 메세지 발신

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //버튼을 클릭할때
                switch (item.getItemId()){
                    case R.id.menu_home:
                        //화면 교체는 replace를 이용
                        transaction.replace(R.id.menu_frameLayout, homeFragment).commit();
                        break;
                    case R.id.menu_alarm:
                        transaction.replace(R.id.menu_frameLayout, alarmFragment).commit();
                        break;
                    case R.id.menu_tempTurb:
                        transaction.replace(R.id.menu_frameLayout, tempTurbFragment).commit();
                        break;
                }
                return false;
            }
        });
    }

}