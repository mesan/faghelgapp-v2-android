package no.mesan.faghelg.view.common;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import no.mesan.faghelgapps.R;

public class FullScreenImageActivity extends AppCompatActivity {

    public static final String PHOTO_URI = "photo_uri";

    @BindView(R.id.imageViewImage)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_image);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent == null) {
            finish();
            return;
        }

        String photoUri = intent.getStringExtra(PHOTO_URI);

        if (photoUri == null) {
            finish();
            return;
        }

        Picasso.with(this).load(photoUri).into(imageView);
    }

    @OnClick(R.id.imageButtonClose)
    public void buttonClosedClicked() {
        finish();
    }
}
