package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.util.MessageTimestampFormatter;
import no.mesan.faghelgapps.R;

public class SocialMessageItemView extends SocialItemAuthorInfoView {

    @Bind(R.id.message_text)
    TextView textViewMessageText;

    @Bind(R.id.message_image)
    ImageView imageViewMessageImage;

    @Bind(R.id.message_image_blurred)
    ImageView imageViewMessageImageBlurred;

    @BindDimen(R.dimen.social_image_max_height)
    int socialImageMaxHeight;

    private Target loadtarget;

    private int borderColor;
    private int borderSize;

    public SocialMessageItemView(Context context) {
        super(context);
        init(context);
    }

    public SocialMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SocialMessageItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SocialMessageItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        borderColor = context.getResources().getColor(R.color.mesanblue);
        borderSize = context.getResources().getDimensionPixelSize(R.dimen.person_image_border_size);
    }

    public void bindTo(Message message, SocialAdapter.ImageListener imageListener) {
        if (message == null) {
            return;
        }

        textViewAuthorShortname.setText("");
        imageViewAuthor.setImageDrawable(null);
        timestampView.setText("");
        textViewMessageText.setText("");
        imageViewMessageImage.setImageBitmap(null);
        imageViewMessageImage.setVisibility(View.GONE);
        imageViewMessageImageBlurred.setImageBitmap(null);
        imageViewMessageImageBlurred.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(message.getSender())) {
            textViewAuthorShortname.setText("@" + message.getSender());
        }

        imageViewAuthor.setImageDrawable(null);

        imageViewMessageImage.setOnClickListener(view -> {
            imageListener.imageWasClicked(message.getImageUrl());
        });

        if (!TextUtils.isEmpty(message.getSenderImageUrl())) {

            Transformation transformation2 = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(4)
                    .oval(true)
                    .build();

            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(2)
                    .oval(true)
                    .build();

            Picasso.with(getContext()).load(message.getSenderImageUrl())
                    .transform(transformation2).transform(transformation).into(imageViewAuthor);
        }

        if (!TextUtils.isEmpty(message.getTimestamp().toString())) {
            String formattedTimestamp = MessageTimestampFormatter.formatTimestamp(message);
            timestampView.setText(formattedTimestamp);
        }

        if (!TextUtils.isEmpty(message.getContent())) {
            textViewMessageText.setText(message.getContent());
        }

        if (!TextUtils.isEmpty(message.getImageUrl())) {

            if (loadtarget == null) {
                loadtarget = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        if (height > width) {
                            imageViewMessageImage.setMaxHeight(socialImageMaxHeight);
                            imageViewMessageImageBlurred.setMaxHeight(socialImageMaxHeight);
                            imageViewMessageImageBlurred.setVisibility(VISIBLE);
                            imageViewMessageImageBlurred.setImageBitmap(bitmap);
//                            Blurry.with(getContext()).radius(50).async().onto(viewBlur);
                            Blurry.with(getContext()).radius(50).async().from(bitmap).into(imageViewMessageImageBlurred);
                        } else {
                            imageViewMessageImage.setMaxHeight(123456);
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
            }

            Picasso.with(getContext()).load(message.getImageUrl())
                    .into(loadtarget);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
