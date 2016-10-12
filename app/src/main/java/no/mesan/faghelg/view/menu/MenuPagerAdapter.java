package no.mesan.faghelg.view.menu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import no.mesan.faghelg.view.people.PeopleFragment;
import no.mesan.faghelg.view.program.ProgramFragment;
import no.mesan.faghelg.view.social.SocialFragment;

public class MenuPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments;

    public MenuPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(Fragment.instantiate(context, ProgramFragment.class.getName()));
        fragments.add(Fragment.instantiate(context, SocialFragment.class.getName()));
        fragments.add(Fragment.instantiate(context, PeopleFragment.class.getName()));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
