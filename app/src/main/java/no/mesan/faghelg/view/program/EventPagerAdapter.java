package no.mesan.faghelg.view.program;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import no.mesan.faghelg.model.Event;

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Event> events;
    public static final String ARGS_EVENT = "ARGS_EVENT";
    public static final String ARGS_NUMBER_OF_EVENTS = "ARGS_NUMBER_OF_EVENTS";
    public static final String ARGS_EVENT_DAY_POSITION = "ARGS_EVENT_DAY_POSITION";
    public static final String ARGS_SCROLLED_EVENT_POSITION = "ARGS_SCROLLED_EVENT_POSITION";

    public EventPagerAdapter(FragmentManager fm, Context context, List<Event> events) {
        super(fm);
        this.context = context;
        this.events = events;
    }

    private Fragment mCurrentFragment;

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Event event = events.get(position);
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGS_EVENT, event);
        arguments.putInt(ARGS_NUMBER_OF_EVENTS, events.size());
        arguments.putInt(ARGS_EVENT_DAY_POSITION, position+1);
        return Fragment.instantiate(context, EventOverlayFragment.class.getName(), arguments);
    }

    @Override
    public int getCount() {
        return events.size();
    }

}
