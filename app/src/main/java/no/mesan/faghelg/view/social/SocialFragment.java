package no.mesan.faghelg.view.social;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerSocialFragmentComponent;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.message.MessageFragment;
import no.mesan.faghelgapps.R;

public class SocialFragment extends BaseFragment {

    @Bind(R.id.recycler_view_social)
    RecyclerView recyclerViewSocial;

    @Inject
    SocialService socialService;

    private SocialAdapter socialAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        setupRecyclerView();
        getMessages();

        return view;
    }

    private void setupRecyclerView() {
        socialAdapter = new SocialAdapter();

        recyclerViewSocial.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSocial.setAdapter(socialAdapter);
    }

    private void getMessages() {
        bindToLifecycle(socialService.getMessages()).subscribe(
                this::handleGetMessagesSuccess,
                this::handleGetMessagesFailure
        );
    }

    private void handleGetMessagesSuccess(List<Message> messages) {
        socialAdapter.setMessages(messages);
    }

    private void handleGetMessagesFailure(Throwable throwable) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_social, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send:
                loadFragment(new MessageFragment());
                break;
        }
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_social;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerSocialFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }
}
