package pavelnovak.com.omertex.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import pavelnovak.com.omertex.R;
import pavelnovak.com.omertex.view.IMoreInfoView;

public class MoreInfoActivity extends AppCompatActivity implements IMoreInfoView{

    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_BODY = "BODY";
    public static final String EXTRA_URL = "URL";

    private ImageView photo;
    private ProgressBar _loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        TextView title = findViewById(R.id.title);
        TextView body = findViewById(R.id.body);
        photo = findViewById(R.id.photo);

        Intent intent = getIntent();

        title.setText(String.valueOf(intent.getStringExtra(EXTRA_TITLE)));
        body.setText(String.valueOf(intent.getStringExtra(EXTRA_BODY)));

        _loadingIndicator = findViewById(R.id.loading_spinner);
        _loadingIndicator.getIndeterminateDrawable().setColorFilter(ContextCompat
                .getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);

        setPhoto(this, photo, intent);
    }

    @Override
    public void setPhoto(Activity activity, ImageView imageView, Intent intent) {
        if (intent.getStringExtra(EXTRA_URL) != null) {
            Glide.with(activity)
                    .asBitmap()
                    .load(intent.getStringExtra(EXTRA_URL))
                    .listener(new RequestListener<Bitmap>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            onPalette(Palette.from(resource).generate());
                            imageView.setImageBitmap(resource);
                            _loadingIndicator.setVisibility(View.INVISIBLE);

                            return false;
                        }

                        void onPalette(Palette palette) {
                            if (null != palette) {
                                ViewGroup parent = (ViewGroup) photo.getParent().getParent().getParent();
                                parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
                            }
                        }
                    })
                    .into(imageView);
        } else _loadingIndicator.setVisibility(View.INVISIBLE);
    }
}
