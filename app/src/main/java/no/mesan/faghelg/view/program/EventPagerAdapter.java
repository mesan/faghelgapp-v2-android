package no.mesan.faghelg.view.program;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import no.mesan.faghelg.model.Event;

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Event> events;
    public static final String ARGS_EVENT = "ARGS_EVENT";

    public EventPagerAdapter(FragmentManager fm, Context context, List<Event> events) {
        super(fm);
        this.context = context;
        this.events = events;
    }

    @Override
    public Fragment getItem(int position) {
        Event event = events.get(position);
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGS_EVENT, event);
        return Fragment.instantiate(context, EventOverlayFragment.class.getName(), arguments);
    }

    @Override
    public int getCount() {
        return events.size();
    }

}
