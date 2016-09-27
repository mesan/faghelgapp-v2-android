package no.mesan.view;

import no.mesan.faghelgapps.R;
import no.mesan.injector.components.AppComponent;

public class ProgramFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_program;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

}
