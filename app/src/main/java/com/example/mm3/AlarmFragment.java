package com.example.mm3;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

public class AlarmFragment extends Fragment {


    AlarmManager alarmManager;
    //Context context;
    PendingIntent pendingIntent;
    //MainActivity mainActivity;

    public TimePicker timePicker;
    Button set_save_bt;
    ListViewAdapter adapter;
    ListView listView;

    String hou, minut, amp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        //프래그먼트에서는 findViewID를 그냥 사용하지 못한다. 그래서 ViewGroup을 이용해서 findViewId를 사용할 수 있다.
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);

        //알람매니저 설정
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);


        // Calendar 객체 생성
        Calendar calendar = Calendar.getInstance();

        // 알람리시버 intent 생성
        Intent my_intent = new Intent(getActivity(), AlarmReceiver.class);

        listView = (ListView) rootview.findViewById(R.id.listView);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        timePicker = (TimePicker) rootview.findViewById(R.id.time_picker);
        set_save_bt = (Button) rootview.findViewById(R.id.set_save_bt);

        // 앱시작 서버연결 후 시간데이터 불러옴
        hou = "8";
        minut = "30";
        amp = "오전";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response); // 받은 데이터 json 으로 받기
                    //for문으로 디비데이터 모두 넣기 리스트, 켈린더 입력 등
                    adapter.addItem(jsonObject.getString("hour") + "", jsonObject.getString("minute") + "", amp);
                    adapter.notifyDataSetChanged();

                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jsonObject.getString("hour")));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(jsonObject.getString("minute")));

                    Random random = new Random();
                    int randomV = random.nextInt();


                    pendingIntent = PendingIntent.getBroadcast(getActivity(), randomV, my_intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    // 알람셋팅
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버 송수신 코드
        alarmRequest alarmRequest = new alarmRequest(hou, minut, amp, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(alarmRequest);



        set_save_bt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String ampm;
                switch (v.getId()) {
                    case R.id.set_save_bt: {

                        if (timePicker.getHour() > 11 && timePicker.getHour() < 24) {
                            ampm = "오후";
                        } else ampm = "오전";

                        adapter.addItem(timePicker.getHour() + "", timePicker.getMinute() + "", ampm);
                        adapter.notifyDataSetChanged();//ㄹㅣ스트추가

                        String hour = String.valueOf(timePicker.getHour());
                        String minute = String.valueOf(timePicker.getMinute());

                        // *저장하면서 서버 DB에도 저장*
                        alarmRequest alarmRequest = new alarmRequest(hour, minute, ampm, null);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(alarmRequest);

                        // calendar에 시간 셋팅
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());

                        // 시간 가져옴
                        //int hour = timePicker.getHour();
                        //int minute = timePicker.getMinute();
                        Toast.makeText(getActivity(), "Alarm 예정 " + ampm +" "+ hour + "시 " + minute + "분" , Toast.LENGTH_SHORT).show();

                        Random random = new Random();
                        int randomV = random.nextInt();

                        pendingIntent = PendingIntent.getBroadcast(getActivity(), randomV, my_intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        // 알람셋팅
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                        Log.i("test", "test : 알람 추가 성공");
                        break;
                    }
                }
            }
        });
        return rootview;
    }
}