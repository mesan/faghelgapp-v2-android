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

import butterknife.BindView;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerSocialFragmentComponent;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.MainActivity;
import no.mesan.faghelg.view.common.DividerItemDecoration;
import no.mesan.faghelg.view.common.FullScreenImageActivity;
import no.mesan.faghelg.view.message.MessageActivity;
import no.mesan.faghelgapps.R;

import static android.app.Activity.RESULT_OK;

public class SocialFragment extends BaseFragment implements SocialAdapter.ImageListener {

    @BindView(R.id.social_progress_bar)
    ProgressBar progressBarView;

    @BindView(R.id.social_progress_bar_text)
    TextView progressBarTextView;

    @BindView(R.id.recycler_view_social)
    RecyclerView recyclerViewSocial;

    @BindView(R.id.swipeRefreshFeed)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    SocialService socialService;

    private SocialAdapter socialAdapter;

    private int MESSAGE_FRAGMENT_REQUEST = 123;

    private boolean scrollToTopAfterGettingMessages = false;

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
        socialAdapter = new SocialAdapter(this);

        showProgressBarIfAdapterEmpty();

        recyclerViewSocial.setLayoutManager(new LinearLayoutManager(getContext()));

        Drawable dividerDrawable = getResources().getDrawable(R.drawable.feed_divider);
        int dividerPaddingSides = getResources().getDimensionPixelSize(R.dimen.social_padding);
        recyclerViewSocial.addItemDecoration(new DividerItemDecoration(dividerDrawable, dividerPaddingSides));

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
        if (scrollToTopAfterGettingMessages) {
            recyclerViewSocial.scrollToPosition(0);
            scrollToTopAfterGettingMessages = false;
        }
    }

    private void handleGetMessagesFailure(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        ((MainActivity) getActivity()).showSnackbar(getString(R.string.error_feed));
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
                startActivityForResult(i, MESSAGE_FRAGMENT_REQUEST);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MESSAGE_FRAGMENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                scrollToTopAfterGettingMessages = true;
            }
        }
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

    @Override
    public void imageWasClicked(String url) {

        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
        intent.putExtra(FullScreenImageActivity.PHOTO_URI, url);
        startActivity(intent);
    }
}
