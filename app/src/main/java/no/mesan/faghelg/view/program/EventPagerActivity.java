package no.mesan.faghelg.view.program;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelgapps.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventPagerActivity extends AppCompatActivity {

    @Bind(R.id.eventsViewPager)
    ViewPager viewPager;

    private EventPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        ArrayList<Event> eventList = getIntent().getParcelableArrayListExtra(ProgramFragment.ARGS_EVENTS);
        int position = getIntent().getIntExtra(ProgramFragment.ARGS_EVENT_POSITION, 0);

        setupAdapter(eventList, position);

        showPreviewOfPages();
    }

    private void setupAdapter(ArrayList<Event> eventList, int position) {
        pagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), getApplicationContext(), eventList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
    }

    private void showPreviewOfPages() {
        viewPager.setPageMargin(20);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        EventOverlayFragment currentFragment = (EventOverlayFragment)pagerAdapter.getCurrentFragment();

        currentFragment.onBackPressed();
    }
}
