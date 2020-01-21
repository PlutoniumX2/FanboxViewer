package cn.settile.fanboxviewer.Network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.picasso.PicassoImageLoader;

import cn.settile.fanboxviewer.R;

public class CustomPicassoLoader extends PicassoImageLoader {

    private final String url;
    private Integer thumbnailWidth = 100;
    private Integer thumbnailHeight = 100;
    private OnLoadListener onLoad;

    public CustomPicassoLoader(String url) {
        super(url);
        this.url = url;
    }

    public CustomPicassoLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        super(url, thumbnailWidth, thumbnailHeight);
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public void onLoaded(OnLoadListener r){
        this.onLoad = r;
    }

    @Override
    public void loadMedia(Context context, ImageView imageView, SuccessCallback callback) {
        Picasso.get()
                .load(this.url)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView, new MyImageCallback(callback, imageView));
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
        Picasso.get()
                .load(url)
                .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                        thumbnailHeight == null ? 100 : thumbnailHeight)
                .placeholder(R.drawable.placeholder_image)
                .centerInside()
                .into(thumbnailView, new MyImageCallback(callback));
    }

    public interface OnLoadListener{
        void onLoadCallback(Drawable b);
    }

    private class MyImageCallback implements Callback {
        private final SuccessCallback callback;
        private ImageView imageView = null;

        public MyImageCallback(SuccessCallback callback, ImageView imageView) {
            this.callback = callback;
            this.imageView = imageView;
        }
        public MyImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            if(imageView != null)
                onLoad.onLoadCallback(imageView.getDrawable());
            callback.onSuccess();
        }

        @Override
        public void onError(Exception e) {
        }
    }
}
