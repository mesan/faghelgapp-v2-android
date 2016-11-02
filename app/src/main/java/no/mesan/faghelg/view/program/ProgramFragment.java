package no.mesan.faghelg.view.program;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerProgramFragmentComponent;
import no.mesan.faghelg.model.DailyProgram;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.model.Program;
import no.mesan.faghelg.service.ProgramService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.MainActivity;
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
        distributeProgramToDays();
    }

    private void handleEventsError(Throwable throwable) {
        ((MainActivity)getActivity()).showSnackbar(getString(R.string.error_program));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_program;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerProgramFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }

    private void distributeProgramToDays() {
        List<DailyProgram> programs = new ArrayList<>();
        String[] weekdays = getContext().getResources().getStringArray(R.array.weekdays);

        int weekDay = -1;
        ArrayList<Event> eventForDay = new ArrayList<>();
        int i = 0;
        int currentPage = 0;
        int page = 0;

        boolean newDay;
        boolean lastItem;

        // TODO: Rewrite..! This I am not in any way proud of.. O_o
        for (Event event : program.getEvents()) {
            lastItem = i == program.getEvents().size() - 1;
            DateTime eventDate = event.getStartTime();
            newDay = weekDay > -1 && weekDay != eventDate.getDayOfWeek();

            if (newDay && !lastItem) {
                page++;
                boolean isToday = eventDate.getDayOfYear() == new DateTime().getDayOfYear();
                if (isToday) {
                    currentPage = page;
                }
                programs.add(new DailyProgram(weekdays[weekDay], new ArrayList<>(eventForDay)));
                eventForDay.clear();
            }

            if (lastItem) {
                if (!newDay) {
                    eventForDay.add(event);
                }

                // Add events for now
                programs.add(new DailyProgram(weekdays[weekDay], new ArrayList<>(eventForDay)));
                eventForDay.clear();

                if (newDay) {
                    // Add the last event
                    eventForDay.add(event);
                    programs.add(new DailyProgram(weekdays[eventDate.getDayOfWeek()],
                            new ArrayList<>(eventForDay)));
                }
            }

            i++;
            weekDay = eventDate.getDayOfWeek();
            eventForDay.add(event);
        }

        initPager(programs, currentPage);
    }

    private void initPager(List<DailyProgram> events, int currentPage) {
        viewPager.setAdapter(new DailyProgramAdapter(getActivity().getSupportFragmentManager(), getApplicationContext(), events));

        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getApplicationContext(), R.color.mesanblue));
        slidingTabLayout.setDividerColors(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));
        slidingTabLayout.setViewPager(viewPager);

        viewPager.setCurrentItem(currentPage);
    }
}
