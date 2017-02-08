package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.service.ImageService;
import no.mesan.faghelg.util.MessageTimestampFormatter;
import no.mesan.faghelgapps.R;

public class ImageSocialItemView extends SocialItemAuthorInfoView {

    @Bind(R.id.message_image)
    ImageView imageViewMessageImage;

    @BindDimen(R.dimen.social_image_max_height)
    int socialImageMaxHeight;

    private Target loadtarget;

    private int borderColor;
    private int borderSize;

    public ImageSocialItemView(Context context) {
        super(context);
        init(context);
    }

    public ImageSocialItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageSocialItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageSocialItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        borderColor = context.getResources().getColor(R.color.mesanblue);
        borderSize = context.getResources().getDimensionPixelSize(R.dimen.person_image_border_size);
    }

    public void bindTo(Message message) {
        if (message == null) {
            return;
        }

        if (!TextUtils.isEmpty(message.getSender())) {
            textViewAuthorShortname.setText(message.getSender());
        }

        imageViewAuthor.setImageDrawable(null);

        if (!TextUtils.isEmpty(message.getSenderImageUrl())) {

            Transformation transformation2 = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(4)
                    .oval(true)
                    .build();

            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1)
                    .oval(true)
                    .build();

            Picasso.with(getContext()).load(message.getSenderImageUrl())
                    .transform(transformation2).transform(transformation).into(imageViewAuthor);
        }

        if (!TextUtils.isEmpty(message.getImageUrl())) {

            if (loadtarget == null) loadtarget = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if(height > width) {
                        imageViewMessageImage.setMaxHeight(socialImageMaxHeight);
                    }
                    imageViewMessageImage.setImageBitmap(bitmap);
                    imageViewMessageImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.with(getContext()).load(message.getImageUrl())
                    .into(loadtarget);
        }

        if (!TextUtils.isEmpty(message.getTimestamp().toString())) {
            String formattedTimestamp = MessageTimestampFormatter.formatTimestamp(message);
            timestampView.setText(formattedTimestamp);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
