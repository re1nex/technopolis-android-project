package com.example.technopolis.screens.common.download;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ConcurrentHashMap;

public class ImageStorage {
    private final ConcurrentHashMap<String, ImageTarget> images;

    public ImageStorage() {
        images = new ConcurrentHashMap<>();
    }

    public void setImage(@NonNull Bitmap image, @NonNull String imageUrl) {
        final ImageTarget target = new ImageTarget();
        target.setImage(image);
        images.put(imageUrl, target);
    }

    public void downloadImage(@NonNull String imageUrl, @NonNull ImageView view) {
        if (!images.containsKey(imageUrl)) {
            final ImageTarget target = new ImageTarget();
            target.setView(view);
            Picasso.get().load(imageUrl).networkPolicy(NetworkPolicy.NO_CACHE).into(target);
            images.put(imageUrl, target);
        } else {
            if (getImage(imageUrl) != null)
                view.setImageBitmap(getImage(imageUrl));
            else
                images.get(imageUrl).addInQueue(view);
        }
    }

    @Nullable
    public Bitmap getImage(@NonNull String imageUrl) {
        if (!images.containsKey(imageUrl))
            return null;
        return images.get(imageUrl).getImage();
    }
}