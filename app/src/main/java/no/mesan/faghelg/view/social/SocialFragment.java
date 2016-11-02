package no.mesan.faghelg.view.social;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerSocialFragmentComponent;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.MainActivity;
import no.mesan.faghelg.view.message.MessageActivity;
import no.mesan.faghelgapps.R;

public class SocialFragment extends BaseFragment {

    @Bind(R.id.social_progress_bar)
    ProgressBar progressBarView;

    @Bind(R.id.social_progress_bar_text)
    TextView progressBarTextView;

    @Bind(R.id.recycler_view_social)
    RecyclerView recyclerViewSocial;

    @Bind(R.id.swipeRefreshFeed)
    SwipeRefreshLayout swipeRefreshLayout;

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessages();
            }
        });

        setupRecyclerView();
        getMessages();

        return view;
    }

    private void setupRecyclerView() {
        socialAdapter = new SocialAdapter();

        showProgressBarIfAdapterEmpty();

        recyclerViewSocial.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewSocial.setAdapter(socialAdapter);
    }

    private void showProgressBarIfAdapterEmpty() {
        if (socialAdapter.getItemCount() == 0) {
            progressBarView.setVisibility(View.VISIBLE);
            progressBarTextView.setVisibility(View.VISIBLE);
        }
    }

    private void getMessages() {
        bindToLifecycle(socialService.getMessages()).subscribe(
                this::handleGetMessagesSuccess,
                this::handleGetMessagesFailure
        );
    }

    private void handleGetMessagesSuccess(List<Message> messages) {
        progressBarView.setVisibility(View.GONE);
        progressBarTextView.setVisibility(View.GONE);
        socialAdapter.setMessages(messages);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void handleGetMessagesFailure(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        ((MainActivity)getActivity()).showSnackbar(getString(R.string.error_feed));
    }

    @Override
    public void onResume() {
        super.onResume();
        getMessages();
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
                Intent i = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(i);
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

    public void reload() {
        getMessages();
    }
}
