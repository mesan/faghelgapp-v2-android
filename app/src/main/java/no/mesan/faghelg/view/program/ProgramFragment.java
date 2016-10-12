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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerProgramFragmentComponent;
import no.mesan.faghelg.model.Event;
import no.mesan.faghelg.service.ProgramService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.common.DividerItemDecoration;
import timber.log.Timber;

public class ProgramFragment extends BaseFragment implements EventAdapter.ItemClickListener {

    @Inject
    ProgramService programService;

    @Bind(R.id.recyclerviewEvents)
    RecyclerView recyclerViewEvents;

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
        programService.getEventsFromApi().subscribe(
                this::handleEventsSuccess,
                this::handleEventsError);
    }

    private void handleEventsSuccess(List<Event> events) {
        EventAdapter eventAdapter = new EventAdapter(events, this);

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

    @Override
    public void itemClick(Event event) {
        Timber.d("Clicked " + event.getTitle());
        Intent i = new Intent(getApplicationContext(), EventPagerActivity.class);

        startActivity(i);
    }
}
