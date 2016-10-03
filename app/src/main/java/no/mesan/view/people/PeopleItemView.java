package no.mesan.view.people;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelgapps.R;
import no.mesan.model.Person;

public class PeopleItemView extends LinearLayout {

    @Bind(R.id.textViewPersonFullName)
    TextView textViewPersonFullName;

    @Bind(R.id.textViewPersonShortName)
    TextView textViewPersonShortName;

    @Bind(R.id.imageViewPerson)
    ImageView imageViewPerson;

    public PeopleItemView(Context context) {
        super(context);
    }

    public PeopleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PeopleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PeopleItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void bindTo(Person person) {
        textViewPersonFullName.setText(person.getFullName());
        textViewPersonShortName.setText(getContext().getString(R.string.short_name, person.getShortName()));
        imageViewPerson.setImageDrawable(null);
        Picasso.with(getContext()).load(person.getProfileImageUrl()).into(imageViewPerson);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
