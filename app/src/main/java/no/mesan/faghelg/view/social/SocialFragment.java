package no.mesan.faghelg.view.social;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerSocialFragmentComponent;
import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelgapps.R;
import timber.log.Timber;

public class SocialFragment extends BaseFragment {

    @Inject
    SocialService socialService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getPeople();

        return view;
    }

    private void getPeople() {
        bindToLifecycle(socialService.getMessages()).subscribe(
                this::handleGetMessagesSuccess,
                this::handleGetMessagesFailure
        );
    }

    private void handleGetMessagesSuccess(List<Message> messages) {
        Timber.d("#meldinger: " + messages.size());
    }

    private void handleGetMessagesFailure(Throwable throwable) {

    }

    /*
    private void postMessage() {
        String token = getActivity().getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.))

        bindToLifecycle(socialService.postMessage(token, message)).subscribe(
                this::handlePostMessageSuccess,
                this::handlePostMessageFailure
        );
    }*/

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_social;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerSocialFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }
}
