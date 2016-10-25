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

public class DailyProgramAdapter extends FragmentPagerAdapter {

    private List<DailyProgram> dailyPrograms;
    private Context context;

    public DailyProgramAdapter(FragmentManager fm, Context context, List<DailyProgram> dailyPrograms) {
        super(fm);
        this.dailyPrograms = dailyPrograms;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        List<Event> eventsForDay = dailyPrograms.get(position).getEvents();

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ProgramFragment.ARGS_EVENTS, (ArrayList)eventsForDay);

        return Fragment.instantiate(context, DailyProgramFragment.class.getName(), arguments);
    }

    @Override
    public int getCount() {
        return dailyPrograms.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dailyPrograms.get(position).getDay();
    }
}
