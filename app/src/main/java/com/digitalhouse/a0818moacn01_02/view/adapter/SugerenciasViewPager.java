package com.digitalhouse.a0818moacn01_02.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.digitalhouse.a0818moacn01_02.model.Track;
import com.digitalhouse.a0818moacn01_02.view.ReproductorFragment;

import java.util.ArrayList;
import java.util.List;

public class SugerenciasViewPager extends FragmentPagerAdapter {
    List<Track> trackList;
    List<ReproductorFragment> reproductorFragments = new ArrayList<>();

    public SugerenciasViewPager(FragmentManager fm, List<Track> trackList) {
        super(fm);
        this.trackList = trackList;

        for (Track track : trackList) {
            reproductorFragments.add(ReproductorFragment.factory(track));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return reproductorFragments.get(position);
    }

    @Override
    public int getCount() {
        return trackList.size();
    }

    public interface siguienteTrack {
        void siguiente(Integer posicion);
    }
}
