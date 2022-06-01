package com.example.mm3;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class alarmRequest extends StringRequest {

    // 연결할 주소 및 서버로 보낼 데이터 map 으로 지정하기
    final  static  private  String URL = "http://18.236.96.146:3003/postServer";
    private Map<String, String> map;

    public alarmRequest(String hou, String minut, String amp,  Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>(); //map 생성
        map.put("hou", hou); //서버로 보낼 데이터
        map.put("minut", minut);
        map.put("amp", amp);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}