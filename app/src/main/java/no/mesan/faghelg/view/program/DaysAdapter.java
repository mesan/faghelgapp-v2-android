package no.mesan.faghelg.view.program;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelg.model.DailyProgram;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.model.Program;

public class DaysAdapter extends FragmentPagerAdapter {

    private List<DailyProgram> program;
    private Context context;

    public DaysAdapter(FragmentManager fm, Context context, List<DailyProgram> program) {
        super(fm);
        this.program = program;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        List<Event> eventsForDay = program.get(position).getEvents();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ProgramFragment.ARGS_EVENTS, (ArrayList)eventsForDay);

        return Fragment.instantiate(context, ProgramDayFragment.class.getName(), arguments);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return program.get(position).getDay();
    }
}
