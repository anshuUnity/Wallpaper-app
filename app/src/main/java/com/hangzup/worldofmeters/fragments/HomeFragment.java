package com.hangzup.worldofmeters.fragments;

import android.database.Observable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hangzup.worldofmeters.R;
import com.hangzup.worldofmeters.adapters.WallpaperAdpater;
import com.hangzup.worldofmeters.models.Wallpapers;
import com.hangzup.worldofmeters.viewmodels.WallpaperViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeFragment extends Fragment{

    RecyclerView recyclerView;
    WallpaperAdpater wallpaperAdpater;
    List<Wallpapers> wallpapers_list;
    ProgressBar progressBar;
    boolean isScrolling = false;
    GridLayoutManager layoutManager;
    WallpaperViewModel wallpaperViewModel;
    int current_items, total_items, scrolled_out_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progress);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        wallpaperAdpater = new WallpaperAdpater(requireContext(), wallpapers_list);
        recyclerView.setAdapter(wallpaperAdpater);


        wallpaperViewModel = ViewModelProviders.of(this).get(WallpaperViewModel.class);
        wallpaperViewModel.getWallpapers(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Wallpapers>>() {
            @Override
            public void onChanged(List<Wallpapers> wallpapers) {
                Log.d("size of the list : ", String.valueOf(wallpapers.size()));
                if(wallpapers.size() > 0){
                    progressBar.setVisibility(View.GONE);
                }
                wallpapers_list = wallpapers;
                wallpaperAdpater.setWallpapers(wallpapers_list);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                current_items = layoutManager.getChildCount();
                total_items = layoutManager.getItemCount();
                scrolled_out_items = layoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (scrolled_out_items + current_items == total_items)){
                    isScrolling = false;

                    wallpaperViewModel = ViewModelProviders.of(requireActivity()).get(WallpaperViewModel.class);
                    wallpaperViewModel.getWallpapers(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Wallpapers>>() {
                        @Override
                        public void onChanged(List<Wallpapers> wallpapers) {
                            wallpaperAdpater.setWallpapers(wallpapers);
//                            wallpaperAdpater.notifyDataSetChanged();
                        }
                    });

                }

            }
        });

        return view;
    }

//    private void setrecycler(final List<Wallpapers> wallpapers){
//        wallpaperAdpater = new WallpaperAdpater(getActivity(), (ArrayList<Wallpapers>) wallpapers);
//        recyclerView.setAdapter(wallpaperAdpater);
//        layoutManager = new GridLayoutManager(getActivity(), 2);
//        recyclerView.setLayoutManager(layoutManager);
//    }

//    public void fetchWallpapers(){
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/curated/?page=1&per_page=20", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try{
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("photos");
//
//                    int n = jsonArray.length();
//
//                    for(int i = 0; i < n; i++){
//                        JSONObject semijsonObject = jsonArray.getJSONObject(i);
//                        int id = semijsonObject.getInt("id");
//
//                        JSONObject image_obj = semijsonObject.getJSONObject("src");
//
//                        String url = image_obj.getString("medium");
//
//                        Wallpapers wallpapers = new Wallpapers();
//                        wallpapers.setMedium_url(url);
//                        wallpapers.setId(id);
//                        m_list.add(wallpapers);
//
//                    }
//
//                    wallpaperAdpater.notifyDataSetChanged();
//
//                }catch(JSONException e){
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", "563492ad6f91700001000001060d8038111944f5ae64b29648b6ab73");
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
//        requestQueue.add(stringRequest);
//    }



}