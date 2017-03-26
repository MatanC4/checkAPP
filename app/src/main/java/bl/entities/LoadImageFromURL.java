package bl.entities;

import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import android.util.Log;


/**
 * Created by matka on 25/03/17.
 */
class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;

    public LoadImageFromURL(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            return downloadBitmap(params[0]);
        } catch (Exception e) {
            // log error
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                   // Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher);
                   // imageView.setImageDrawable(placeholder);
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != 200) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}