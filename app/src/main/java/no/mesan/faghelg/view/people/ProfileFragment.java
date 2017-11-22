package no.mesan.faghelg.view.people;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelgapps.R;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.textViewProfileFullName)
    TextView textViewProfileFullName;

    @BindView(R.id.imageViewPerson)
    ImageView imageViewPerson;

    private Person person;

    @Override
    protected int getContentViewId() {
        return R.layout.profile_fragment_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //TODO use shared transition views instead of setting like this
        person = getArguments().getParcelable("person");
        setProfileImage();
        textViewProfileFullName.setText(person.getFullName());

        return view;
    }

    private void setProfileImage() {
        if (!TextUtils.isEmpty(person.getProfileImageUrl())) {
            int borderColor = getContext().getResources().getColor(R.color.mesanblue);
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

            Picasso.with(getContext()).load(person.getProfileImageUrl()).transform(transformation2).transform(transformation).into(imageViewPerson);
        }
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }
}
