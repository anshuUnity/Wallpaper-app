package com.hangzup.worldofmeters.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hangzup.worldofmeters.VolleySinglton;
import com.hangzup.worldofmeters.models.Wallpapers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WallpaperViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Wallpapers>> wallpaper_list;
    RequestQueue requestQueue;
    private List<Wallpapers> m_list = new ArrayList<>();
    private int page_number = 1;

    //method to get wallpapers
    public LiveData<List<Wallpapers>> getWallpapers(Context context){
        if(wallpaper_list == null){
            wallpaper_list = new MutableLiveData<List<Wallpapers>>();
            loadWallpapers(context);
        }

        return wallpaper_list;
    }

    private void loadWallpapers(Context context) {
        requestQueue = VolleySinglton.getInstance(context).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/curated/?page="+page_number+"&per_page=20", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                    int n = jsonArray.length();

                    for(int i = 0; i < n; i++){
                        JSONObject semijsonObject = jsonArray.getJSONObject(i);
                        int id = semijsonObject.getInt("id");

                        JSONObject image_obj = semijsonObject.getJSONObject("src");

                        String url = image_obj.getString("medium");
                        Log.d("IMG URL", "onResponse: "+url);

//                        Wallpapers wallpapers = new Wallpapers();
//                        wallpapers.setMedium_url(url);
//                        wallpapers.setId(id);
                        m_list.add(new Wallpapers(id, url));
                        Log.d("OBJECT LIST", "onResponse: "+m_list);

                    }
                    page_number++;
                    Log.d("PAGE NUMBER", "onResponse: "+page_number);
                    wallpaper_list.postValue(m_list);
                    Log.d("FINAL LIST", "onResponse: "+m_list);


                }catch(JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "YOUR API KEY");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
