package com.otb.photosearch.common;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Mohit Rajput on 4/7/17.
 * Glide image loading utility methods
 */
public class GlideUtils {

    public static void loadImage(Activity activity, String url,
                                 ImageView imageView, final ProgressBar progressBar) {
        if (activity != null && !activity.isFinishing()) {
            Glide.with(activity).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    public static void loadImageAsBitmap(Activity activity, String url,
                                         ImageView imageView, int placeholder) {
        if (activity != null && !activity.isFinishing()) {
            Glide.with(activity).load(url).asBitmap().placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public static void loadImage(Context context, String url,
                                 ImageView imageView, int placeholder) {
        if (context != null) {
            Glide.with(context).load(url).placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }
}
