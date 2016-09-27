package no.mesan.view.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.mesan.faghelgapps.R;
import no.mesan.injector.components.AppComponent;
import no.mesan.view.BaseFragment;

public class PeopleFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.people_fragment_layout;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
