package no.mesan.view.people;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelgapps.R;
import no.mesan.model.Person;

public class PeopleItemView extends FrameLayout {

    @Bind(R.id.textViewPerson)
    TextView textViewPerson;

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
        textViewPerson.setText(person.getFullName());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
