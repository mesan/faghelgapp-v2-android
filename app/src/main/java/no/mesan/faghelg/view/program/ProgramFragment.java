package no.mesan.faghelg.view.program;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerProgramFragmentComponent;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.model.Program;
import no.mesan.faghelg.service.ProgramService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.common.DividerItemDecoration;
import no.mesan.faghelgapps.R;

public class ProgramFragment extends BaseFragment implements EventAdapter.ItemClickListener {

    @Inject
    ProgramService programService;

    @Bind(R.id.recyclerviewEvents)
    RecyclerView recyclerViewEvents;

    private Program program;
    private List<Event> eventsForSelectedDay;
    private int selectedDay;

    public static final String ARGS_EVENTS = "ARGS_EVENTS";
    public static final String ARGS_EVENT_POSITION = "ARGS_EVENT_POSITION";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getEvents();

        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Drawable dividerDrawable = getResources().getDrawable(R.drawable.divider);
        int dividerPadding = getResources().getDimensionPixelSize(R.dimen.program_divider_padding);
        recyclerViewEvents.addItemDecoration(new DividerItemDecoration(dividerDrawable, dividerPadding));
    }

    private void getEvents() {
        programService.getProgram().subscribe(
                this::handleEventsSuccess,
                this::handleEventsError);
    }

    private void handleEventsSuccess(Program program) {
        this.program = program;
        EventAdapter eventAdapter = new EventAdapter(this);
        eventsForSelectedDay = program.getEventsForDay(4);
        eventAdapter.setEvents(eventsForSelectedDay);

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

    // TODO: OnDayChanged

    @Override
    public void itemClick(int position) {
        Intent i = new Intent(getApplicationContext(), EventPagerActivity.class);
        i.putParcelableArrayListExtra(ARGS_EVENTS, (ArrayList) eventsForSelectedDay);
        i.putExtra(ARGS_EVENT_POSITION, position);
        startActivity(i);
    }

}
