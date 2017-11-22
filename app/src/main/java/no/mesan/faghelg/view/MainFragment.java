package no.mesan.faghelg.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.view.menu.MenuPagerAdapter;
import no.mesan.faghelg.view.social.SocialFragment;
import no.mesan.faghelgapps.R;

public class MainFragment extends BaseFragment {

    @BindView(R.id.viewPagerMenu)
    ViewPager viewPagerMenu;

    @BindView(R.id.imageViewTabProgram)
    ImageView imageViewTabProgram;

    @BindView(R.id.imageViewTabSocial)
    ImageView imageViewTabSocial;

    @BindView(R.id.imageViewTabPeople)
    ImageView imageViewTabPeople;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    private MenuPagerAdapter menuPagerAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void inject(AppComponent appComponent) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        setViewPager();

        return view;
    }

    public void reloadFromPush() {
        viewPagerMenu.setCurrentItem(1);
        ((SocialFragment)menuPagerAdapter.getItem(1)).reload();
    }

    private void setViewPager() {

        menuPagerAdapter = new MenuPagerAdapter(getActivity().getSupportFragmentManager(), getContext());
        viewPagerMenu.setOffscreenPageLimit(3);
        viewPagerMenu.setAdapter(menuPagerAdapter);
        onTabSocialClick();
        viewPagerMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                setupViews(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @OnClick(R.id.tabProgram)
    public void onTabProgramClick() {
        setupViews(0);
    }

    @OnClick(R.id.tabSocial)
    public void onTabSocialClick() {
        setupViews(1);
    }

    @OnClick(R.id.tabPeople)
    public void onTabPeopleClick() {
        setupViews(2);
    }

    private void setupViews(int page) {
        viewPagerMenu.setCurrentItem(page, true);

        imageViewTabProgram.setImageResource(page == 0 ? R.drawable.ic_program_active : R.drawable.ic_program_passive);
        imageViewTabSocial.setImageResource(page == 1 ? R.drawable.ic_feed_active : R.drawable.ic_feed_passive);
        imageViewTabPeople.setImageResource(page == 2 ? R.drawable.ic_people_active : R.drawable.ic_people_passive);
    }

}