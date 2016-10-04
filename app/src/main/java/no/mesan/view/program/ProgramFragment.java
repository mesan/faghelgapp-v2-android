package no.mesan.view.program;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelgapps.R;
import no.mesan.injector.components.AppComponent;
import no.mesan.injector.components.DaggerProgramFragmentComponent;
import no.mesan.model.Event;
import no.mesan.model.Person;
import no.mesan.service.ProgramService;
import no.mesan.view.BaseFragment;
import no.mesan.view.common.DividerItemDecoration;
import timber.log.Timber;

public class ProgramFragment extends BaseFragment {

    @Inject
    ProgramService programService;

    @Bind(R.id.recyclerviewEvents)
    RecyclerView recyclerViewEvents;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getEvents();

        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewEvents.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider), 40));

        return view;
    }

    private void getEvents() {
        programService.getEventsFromApi().subscribe(
                this::handleEventsSuccess,
                this::handleEventsError);
    }

    private void handleEventsSuccess(List<Event> events) {
        Timber.d("Size: " + events.size());

        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), events);

        recyclerViewEvents.setAdapter(eventAdapter);
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
