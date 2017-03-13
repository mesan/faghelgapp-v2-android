package no.mesan.faghelg.view.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerMessageFragmentComponent;
import no.mesan.faghelg.model.MessageOutput;
import no.mesan.faghelg.service.ImageService;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.view.BaseFragment;
import no.mesan.faghelg.view.common.FullScreenImageActivity;
import no.mesan.faghelgapps.R;

import static android.app.Activity.RESULT_OK;

public class MessageFragment extends BaseFragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Uri photoUri;
    private String imageEncodedBase64;

    @Inject
    SocialService socialService;

    @Bind(R.id.editContent)
    EditText editContent;

    @Bind(R.id.btnCamera)
    ImageButton btnCamera;

    @Bind(R.id.cameraImage)
    ImageView imageView;

    @Bind(R.id.discardCameraImage)
    ImageView discardCameraImageImgView;

    @Bind(R.id.btnSend)
    Button sendButton;

    @Bind(R.id.textCharacterCount)
    TextView characterCountText;
//
//    @Bind(R.id.buttonReset)
//    ImageButton resetButton;

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

    @OnClick(R.id.discardCameraImage)
    public void discardCameraImage() {
        imageView.setVisibility(View.GONE);
        discardCameraImageImgView.setVisibility(View.GONE);
        imageEncodedBase64 = null;
        checkIfDisableSendButton(editContent.getText().toString());
    }

    private void checkIfDisableSendButton(String text) {
        if (text.trim().length() == 0) {
            setEmptyCount();
        }
        if (text.trim().length() == 0 && imageEncodedBase64 == null) {
            sendButton.setEnabled(false);
        } else {
            sendButton.setEnabled(true);
//            resetButton.setVisibility(View.VISIBLE);
            int count = text.length();
            characterCountText.setText(getString(R.string.character_count, count));
        }
    }

    @OnTextChanged(R.id.editContent)
    public void textChanged(CharSequence s) {
        checkIfDisableSendButton(s.toString());
    }

    private void setEmptyCount() {
//        resetButton.setVisibility(View.INVISIBLE);
        characterCountText.setText(getString(R.string.character_count, 0));
    }

//    @OnClick(R.id.cameraImage)
//    public void imageClicked() {
//        Intent intent = new Intent(getContext(), FullScreenImageActivity.class);
//        intent.putExtra(FullScreenImageActivity.PHOTO_URI, photoUri);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.buttonReset)
//    public void resetMessage() {
//        editContent.setText("");
//        setEmptyCount();
//    }

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

    @OnClick(R.id.btnCamera)
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = createTempImageWithUri();
        if (photoUri != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageService imgService = new ImageService(getContext());
            Bitmap imageBitmap = imgService.getScaledBitmapFromUri(photoUri);
            imageView.setImageBitmap(imageBitmap);
            imageView.setVisibility(View.VISIBLE);
            discardCameraImageImgView.setVisibility(View.VISIBLE);
            imageEncodedBase64 = imgService.encodeBitmapToString(imageBitmap);
            checkIfDisableSendButton(editContent.getText().toString());
        }
    }

    private Uri createTempImageWithUri() {
        try {
            File outputDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(
                    "tempFileCamera",
                    ".jpg",
                    outputDir
            );
            return FileProvider.getUriForFile(getActivity(),
                    "no.mesan.faghelgapp.fileprovider",
                    imageFile);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        return null;
    }

    @NonNull
    private MessageOutput createMessage() {
        MessageOutput message = new MessageOutput();
        message.setTitle("");
        message.setImageUrl("");

        String content = editContent.getText().toString();
        message.setContent(content);
        if (!TextUtils.isEmpty(imageEncodedBase64)) {
            message.setImage(imageEncodedBase64);
            Log.d("base64img", message.getImage());
        } else {
            message.setImage("");
            Log.d("isnull", "den var null, folkens! :(");
        }
        return message;
    }

    private void handlePostMessageSuccess(Void aVoid) {
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }

    private void handlePostMessageFailure(Throwable throwable) {
        sendButton.setEnabled(true);
        ((MessageActivity) getActivity()).showSnackbar(getString(R.string.error_message));
    }


    @Override
    protected void inject(AppComponent appComponent) {
        DaggerMessageFragmentComponent.builder().appComponent(appComponent).build().inject(this);
    }
}
