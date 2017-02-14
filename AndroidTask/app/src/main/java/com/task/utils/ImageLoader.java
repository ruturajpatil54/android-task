package com.task.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.task.R;
import com.task.err.Logger;

/**
 * Created by ruturaj on 19/12/16.
 */

public class ImageLoader
{
    public static void loadImage(final ImageView imageView, final String imageUrl, final int fallbackImgId, final Context context, final ProgressBar progressBar)
    {
        if(progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);
            if(imageUrl!=null&&!imageUrl.isEmpty())
        {
            Picasso.with(context)
                    .load(imageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.invalidate();
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(imageUrl)
                                    .error(fallbackImgId)
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            imageView.invalidate();
                                            if(progressBar!=null)
                                                progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {
                                            Logger.add(Logger.WARNING,"image_loader",context.getString(R.string.img_load_failed)+imageUrl);
                                            if(progressBar!=null)
                                                progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    });
        }
        else
        {
            Picasso.with(context)
                    .load(fallbackImgId)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.invalidate();
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Logger.add(Logger.WARNING,"image_loader",context.getString(R.string.img_load_failed)+imageUrl);
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }
                    });

        }
        imageView.invalidate();

    }
    public static void loadImage(final ImageView imageView, final Uri imageUrl, final int fallbackImgId, final Context context,final ProgressBar progressBar)
    {
        if(progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);
        if(imageUrl!=null)
        {
            Picasso.with(context)
                    .load(imageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.invalidate();
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(imageUrl)
                                    .error(fallbackImgId)
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            imageView.invalidate();
                                            if(progressBar!=null)
                                                progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {
                                            Logger.add(Logger.WARNING,"image_loader",context.getString(R.string.img_load_failed)+imageUrl);
                                            if(progressBar!=null)
                                                progressBar.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    });
        }
        else
        {
            Picasso.with(context)
                    .load(fallbackImgId)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.invalidate();
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            Logger.add(Logger.WARNING,"image_loader",context.getString(R.string.img_load_failed)+imageUrl);
                            if(progressBar!=null)
                                progressBar.setVisibility(View.GONE);
                        }
                    });

        }


    }
}
