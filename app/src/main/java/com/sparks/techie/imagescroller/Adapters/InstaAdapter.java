package com.sparks.techie.imagescroller.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sparks.techie.imagescroller.Model.DataModel;
import com.sparks.techie.imagescroller.R;
import com.sparks.techie.imagescroller.Util.VolleyTon;

import java.util.ArrayList;


public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {

    private final ArrayList<DataModel> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.instaImageView);
        }
        // each data item is just a string in this case

    }

    public InstaAdapter(ArrayList<DataModel> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insta_recycler_view, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String url = data.get(position).getImages().getStandard_resolution().getUrl();
        ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
