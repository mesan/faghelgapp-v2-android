package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.util.MessageTimestampFormatter;
import no.mesan.faghelgapps.R;

public class TextSocialItemView extends SocialItemAuthorInfoView {

    @Bind(R.id.message_text)
    TextView textViewMessageText;

    private int borderColor;
    private int borderSize;

    public TextSocialItemView(Context context) {
        super(context);
        init(context);
    }

    public TextSocialItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextSocialItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextSocialItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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


        textViewAuthorShortname.setText("");
        imageViewAuthor.setImageDrawable(null);
        timestampView.setText("");
        textViewMessageText.setText("");

        if (!TextUtils.isEmpty(message.getSender())) {
            textViewAuthorShortname.setText("@" + message.getSender());
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
                    .borderWidthDp(2)
                    .oval(true)
                    .build();

            Picasso.with(getContext()).load(message.getSenderImageUrl())
                    .transform(transformation2).transform(transformation).into(imageViewAuthor);
        }

        if (!TextUtils.isEmpty(message.getContent())) {
            textViewMessageText.setText(message.getContent());
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
