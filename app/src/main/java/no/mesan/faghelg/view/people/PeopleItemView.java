package no.mesan.faghelg.view.people;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelgapps.R;

public class PeopleItemView extends LinearLayout {

    @BindView(R.id.textViewPersonFullName)
    TextView textViewPersonFullName;

    @BindView(R.id.textViewPersonShortName)
    TextView textViewPersonShortName;

    @BindView(R.id.imageViewPerson)
    ImageView imageViewPerson;

    private int borderColor;
    private int borderSize;

    public PeopleItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.people_item, this, true);
        ButterKnife.bind(this);
        init(context);
    }

    public PeopleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PeopleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PeopleItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        borderColor = context.getResources().getColor(R.color.mesanblue);
        borderSize = context.getResources().getDimensionPixelSize(R.dimen.person_image_border_size);
    }

    public void bindTo(Person person) {
        if (person == null) {
            return;
        }

        if (!TextUtils.isEmpty(person.getFullName())) {
            textViewPersonFullName.setText(person.getFullName());
        }

        if (!TextUtils.isEmpty(person.getShortName())) {
            textViewPersonShortName.setText(getContext().getString(R.string.short_name, person.getShortName()));
        }

        imageViewPerson.setImageDrawable(null);

        if (!TextUtils.isEmpty(person.getProfileImageUrl())) {

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

            Picasso.with(getContext()).load(person.getProfileImageUrl()).placeholder(R.drawable.circle).transform(transformation2).transform(transformation).into(imageViewPerson);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
