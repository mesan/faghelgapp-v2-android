package no.mesan.faghelg.view.program;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import no.mesan.faghelg.view.slidingtabs.SlidingTabLayout;
import no.mesan.faghelgapps.R;

public class ProgramFragment extends BaseFragment {

    @Inject
    ProgramService programService;

    @Bind(R.id.pager)
    ViewPager viewPager;

    @Bind(R.id.slidingTabLayoutPrograms)
    SlidingTabLayout slidingTabLayout;

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

        return view;
    }

    private void getEvents() {
        programService.getProgram().subscribe(
                this::handleEventsSuccess,
                this::handleEventsError);
    }

    private void handleEventsSuccess(Program program) {
        this.program = program;
        //EventAdapter eventAdapter = new EventAdapter(this);
        eventsForSelectedDay = program.getEventsForDay(4);
        //eventAdapter.setEvents(eventsForSelectedDay);

        viewPager.setAdapter(new DaysAdapter(getActivity().getSupportFragmentManager(), getApplicationContext(), program));

        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getApplicationContext(), R.color.mesanblue));
        slidingTabLayout.setDividerColors(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        slidingTabLayout.setViewPager(viewPager);
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

}
