package no.mesan.faghelg.view.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerMessageFragmentComponent;
import no.mesan.faghelg.model.MessageOutput;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelgapps.R;

public class MessageFragment extends BaseFragment {

    @Inject
    SocialService socialService;

    @Bind(R.id.editContent)
    EditText editContent;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_message;
    }

    @OnClick(R.id.btnSend)
    public void sendMessage() {
        String token = getActivity().getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.apptoken), "");

        MessageOutput message = createMessage();

        bindToLifecycle(socialService.postMessage(token, message)).subscribe(
                this::handlePostMessageSuccess,
                this::handlePostMessageFailure
        );
    }

    @NonNull
    private MessageOutput createMessage() {
        MessageOutput message = new MessageOutput();
        message.setTitle("");
        message.setImageUrl("");

        String content = editContent.getText().toString();
        message.setContent(content);
        return message;
    }

    private void handlePostMessageSuccess(Void aVoid) {
    }

    private void handlePostMessageFailure(Throwable throwable) {
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerMessageFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }
}
