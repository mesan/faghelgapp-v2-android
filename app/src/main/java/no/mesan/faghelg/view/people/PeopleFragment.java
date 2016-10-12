package no.mesan.faghelg.view.people;

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
import no.mesan.faghelg.view.common.DividerItemDecoration;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.injector.components.DaggerPeopleFragmentComponent;
import no.mesan.faghelg.injector.modules.PeopleModule;
import no.mesan.faghelg.model.Person;
import no.mesan.faghelg.service.PeopleService;
import no.mesan.faghelg.view.BaseFragment;

public class PeopleFragment extends BaseFragment {

    @Bind(R.id.recyclerViewPeople)
    RecyclerView recyclerViewPeople;

    @Inject
    PeopleService peopleService;

    private PeopleAdapter peopleAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.people_fragment_layout;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerPeopleFragmentComponent.builder().appComponent(appComponent).peopleModule(new PeopleModule()).build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setupRecyclerView();
        getPeople();

        return view;
    }

    private void setupRecyclerView() {
        peopleAdapter = new PeopleAdapter();

        recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
        Drawable dividerDrawable = getResources().getDrawable(R.drawable.divider);
        int dividerPadding = getResources().getDimensionPixelSize(R.dimen.person_divider_padding);
        recyclerViewPeople.addItemDecoration(new DividerItemDecoration(dividerDrawable, dividerPadding));
        recyclerViewPeople.setAdapter(peopleAdapter);
    }

    private void getPeople() {
        bindToLifecycle(peopleService.getPersons()).subscribe(
                this::handleGetPeopleSuccess,
                this::handleGetPeopleFailure
        );
    }

    private void handleGetPeopleSuccess(List<Person> people) {
        peopleAdapter.setPeople(people);
    }

    private void handleGetPeopleFailure(Throwable throwable) {

    }


}
