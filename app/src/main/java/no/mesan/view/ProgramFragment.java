package no.mesan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import no.mesan.faghelgapps.R;
import no.mesan.injector.components.AppComponent;
import no.mesan.injector.components.DaggerProgramFragmentComponent;
import no.mesan.model.Event;
import no.mesan.service.ProgramService;
import timber.log.Timber;

public class ProgramFragment extends BaseFragment {

    @Inject
    ProgramService programService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getEvents();

        return view;
    }

    private void getEvents() {
        programService.getEventsFromApi().subscribe(
                this::handleEventsSuccess,
                this::handleEventsError);
    }

    private void handleEventsSuccess(List<Event> events) {
        Timber.d("Size: " + events.size());
    }

    private void handleEventsError(Throwable throwable) {
        // TODO: Show error toast etc.
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_program;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerProgramFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }


}
