package no.mesan.faghelg.view;

import android.widget.Button;

import butterknife.OnClick;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.view.people.PeopleFragment;
import no.mesan.faghelg.view.program.ProgramFragment;

public class MainFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

    @OnClick(R.id.buttonProgram)
    public void onButtonProgramClick(Button b) {
        // Open programview
        ProgramFragment fragment = new ProgramFragment();
        loadFragment(fragment);
    }

    @OnClick(R.id.buttonPeople)
    public void onButtonPeopleClick(Button b) {
        PeopleFragment fragment = new PeopleFragment();
        loadFragment(fragment);
    }

}
