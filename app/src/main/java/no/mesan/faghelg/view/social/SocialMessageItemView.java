package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
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
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.util.MessageTimestampFormatter;
import no.mesan.faghelgapps.R;

public class SocialMessageItemView extends SocialItemAuthorInfoView {

    @Bind(R.id.author_info)
    View viewAuthorInfo;

    @Bind(R.id.message_text)
    TextView textViewMessageText;

    @Bind(R.id.message_image)
    ImageView imageViewMessageImage;

    @Bind(R.id.message_image_blurred)
    ImageView imageViewMessageImageBlurred;

    @BindDimen(R.dimen.social_image_max_height)
    int socialImageMaxHeight;

    private RenderScript rs;
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
        rs = RenderScript.create(context);
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
        textViewMessageText.setVisibility(View.VISIBLE);
        imageViewMessageImage.setImageBitmap(null);
        imageViewMessageImage.setVisibility(View.GONE);
        imageViewMessageImageBlurred.setImageBitmap(null);
        imageViewMessageImageBlurred.setVisibility(View.GONE);
        MarginLayoutParams autoInfoParamsReset =
                (MarginLayoutParams) viewAuthorInfo.getLayoutParams();
        autoInfoParamsReset.topMargin = 0;

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
        } else {
            textViewMessageText.setVisibility(View.GONE);
            MarginLayoutParams authorViewParams = (MarginLayoutParams) viewAuthorInfo.getLayoutParams();
            authorViewParams.topMargin = (int)
                    getResources().getDimension(R.dimen.social_author_image_half_height);
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
//                            createBlurredBitmap(bitmap);
                            new BlurImageAsyncTask().execute(bitmap);
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
        } else {
            MarginLayoutParams textViewMessageParams =
                    (MarginLayoutParams) textViewMessageText.getLayoutParams();
            textViewMessageParams.topMargin = (int)
                    getResources().getDimension(R.dimen.padding_m);
        }
    }

    private class BlurImageAsyncTask extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap blurredBitmap = bitmaps[0].copy(bitmaps[0].getConfig(), true);

            final Allocation input = Allocation.createFromBitmap(rs, blurredBitmap);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(20f);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(blurredBitmap);
            return blurredBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageViewMessageImageBlurred.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
