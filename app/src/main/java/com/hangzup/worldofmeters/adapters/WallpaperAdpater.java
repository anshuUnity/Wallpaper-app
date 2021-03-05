package com.hangzup.worldofmeters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hangzup.worldofmeters.R;
import com.hangzup.worldofmeters.models.Wallpapers;

import java.util.List;

public class WallpaperAdpater extends RecyclerView.Adapter<WallpaperViewHolder> {

    private Context mContext;
    private List<Wallpapers> mWallpapers;

    public WallpaperAdpater() {
    }

    public WallpaperAdpater(Context mContext, List<Wallpapers> mWallpapers) {
        this.mContext = mContext;
        this.mWallpapers = mWallpapers;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.wallpaper_single_layout, parent, false);
        return new WallpaperViewHolder(view);
    }

    public void setWallpapers(List<Wallpapers> mWallpapers){
        this.mWallpapers = mWallpapers;
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        Wallpapers current = mWallpapers.get(position);

        Glide.with(mContext).load(current.getMedium_url()).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        if(this.mWallpapers != null) {
            return mWallpapers.size();
        }
        return 0;
    }

}

class WallpaperViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    public WallpaperViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
    }
}
