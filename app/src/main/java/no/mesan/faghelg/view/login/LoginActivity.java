package no.mesan.faghelg.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;

import no.mesan.faghelg.model.Constants;
import no.mesan.faghelg.view.MainActivity;
import no.mesan.faghelgapps.R;

public class LoginActivity extends AppCompatActivity {

    private AuthenticationContext authenticationContext;

    private ProgressDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = getPreferences(Context.MODE_PRIVATE).getString(getString(R.string.apptoken), "");
        if (TextUtils.isEmpty(token)) {
            showLogin();
        } else {
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showLogin() {
        try {
            authenticationContext = new AuthenticationContext(this, Constants.AUTHORITY_URL, false);
        } catch (Exception e) {
            // TODO: Error handling
        }

        authenticationContext.acquireToken(
                this,
                Constants.RESOURCE_ID,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URL,
                "",
                getCallback());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        authenticationContext.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (loginProgressDialog != null) {
            // to test session cookie behavior
            loginProgressDialog.dismiss();
            loginProgressDialog = null;
        }
    }

    private AuthenticationCallback<AuthenticationResult> getCallback() {
        return new AuthenticationCallback<AuthenticationResult>() {

            @Override
            public void onError(Exception exc) {
                // TODO: Error handling
            }

            @Override
            public void onSuccess(AuthenticationResult result) {
                saveTokenToPrefs(result.getAccessToken());
                goToMainActivity();
            }
        };
    }

    private void saveTokenToPrefs(String token) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.apptoken), token);
        editor.apply();
    }
}