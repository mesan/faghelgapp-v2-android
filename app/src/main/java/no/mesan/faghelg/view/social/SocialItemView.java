package no.mesan.faghelg.view.social;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelgapps.R;

/**
 * Created by toskje9 on 25.10.2016.
 */

public class SocialItemView extends RelativeLayout {
    @Bind(R.id.textViewPersonFullName)
    TextView textViewPersonFullName;

    @Bind(R.id.imageViewPerson)
    ImageView imageViewPerson;

    private int borderColor;
    private int borderSize;

    public SocialItemView(Context context) {
        super(context);
        init(context);
    }

    public SocialItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SocialItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SocialItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
/*
        if (!TextUtils.isEmpty(message.getAuthor().getFullName())) {
            textViewPersonFullName.setText(message.getAuthor().getFullName());
        }

        imageViewPerson.setImageDrawable(null);

        if (!TextUtils.isEmpty(message.getAuthor().getProfileImageUrl())) {

            Transformation transformation2 = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(4)
                    .oval(true)
                    .build();

            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(borderColor)
                    .borderWidthDp(2)
                    .oval(true)
                    .build();

            Picasso.with(getContext()).load(message.getAuthor().getProfileImageUrl())
                    .transform(transformation2).transform(transformation).into(imageViewPerson);
        }*/
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
