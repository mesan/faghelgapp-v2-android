package no.mesan.faghelg.view.message;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
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

    @Bind(R.id.btnSend)
    Button sendButton;

    @Bind(R.id.textCharacterCount)
    TextView characterCountText;

    @Bind(R.id.buttonReset)
    ImageButton resetButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        sendButton.setEnabled(false);

        return view;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_message;
    }

    @OnTextChanged(R.id.editContent)
    public void textChanged(CharSequence s) {
        if(s.toString().trim().length()==0){
            sendButton.setEnabled(false);
            setEmptyCount();
        } else {
            sendButton.setEnabled(true);
            resetButton.setVisibility(View.VISIBLE);
            int count = s.toString().length();
            characterCountText.setText(getString(R.string.character_count, count));
        }
    }

    private void setEmptyCount() {
        resetButton.setVisibility(View.INVISIBLE);
        characterCountText.setText(getString(R.string.character_count, 0));
    }

    @OnClick(R.id.buttonReset)
    public void resetMessage() {
        editContent.setText("");
        setEmptyCount();
    }

    @OnClick(R.id.btnSend)
    public void sendMessage() {
        sendButton.setEnabled(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = preferences.getString(getString(R.string.apptoken), "");

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
        getActivity().finish();
    }

    private void handlePostMessageFailure(Throwable throwable) {
        sendButton.setEnabled(true);
        ((MessageActivity)getActivity()).showSnackbar(getString(R.string.error_message));
    }

    @Override
    protected void inject(AppComponent appComponent) {
        DaggerMessageFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }
}
