package com.example.administrator.game_mission;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class HttpUtil {
    private String string1 = "http://39.108.107.134:8089/interface/truthAndAdv/";
    private String string2 = "http://39.108.107.134:8089/interface/user/";
    private Context context;
    private RequestQueue requestQueue;
    private ImageRequest imageRequest;

    public HttpUtil(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void httpPost(String url, JSONObject jsonObject, final Handler handler, final Class clazz) {
        String strurl = string1 + url;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,strurl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                if (jsonObject.optString("RESULT").equals("S")){
                Message message = new Message();
                message.obj = new Gson().fromJson(jsonObject.toString(), clazz);
                handler.sendMessage(message);
                Log.i("asd", "onResponse: 成功" );

//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("as", "onErrorResponse:失败 " + volleyError);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void httpPost(String url, final Handler handler, final Class clazz) {
        String strurl = string2 + url;
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,strurl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                if (jsonObject.optString("RESULT").equals("S")){
                Message message = new Message();
                message.obj = new Gson().fromJson(jsonObject.toString(), clazz);
                handler.sendMessage(message);
                Log.i("asd", "onResponse: 成功" );

//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("as", "onErrorResponse:失败 " + volleyError);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void httpImg(String url, final ImageView imageView) {
        String strurl = string2 + url;
        imageRequest = new ImageRequest(strurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 500, 500, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(imageRequest);
    }
}
