package no.mesan.faghelg.view.social;

import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelgapps.R;

public class SocialFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_social;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }
}
