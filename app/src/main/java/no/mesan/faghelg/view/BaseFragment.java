package no.mesan.faghelg.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import no.mesan.faghelg.FaghelgApplication;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.injector.components.AppComponent;
import rx.Observable;
import timber.log.Timber;

public abstract class BaseFragment extends RxFragment {

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FaghelgApplication application = (FaghelgApplication) getActivity().getApplication();
        inject(application.getAppComponent());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    protected void loadFragment(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /***
     * To handle fragment lifecycle.
     * (Don't call onNext when fragment is dead.)
     */
    protected <T> Observable<T> bindToLifecycle(Observable<T> observable) {
        observable = catchResponseError(observable);
        return observable.compose(bindUntilEvent(FragmentEvent.DESTROY));
    }

    /*
     * Forces update when client stubs are outdated (Error code: 2)
     */
    @NonNull
    private <T> Observable<T> catchResponseError(Observable<T> observable) {
        return observable.onErrorResumeNext(throwable -> {
            Timber.e(throwable, "Error");
            return Observable.error(throwable);
        });
    }

    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    protected abstract int getContentViewId();

    protected abstract void inject(AppComponent appComponent);
}
