package com.rainmonth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rainmonth.bean.CardInfo;
import com.rainmonth.fragment.CardFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
    private List<CardInfo> mPostList;
    private List<Fragment> mFragments = new ArrayList();

    public CardPagerAdapter(FragmentManager paramFragmentManager, List<CardInfo> paramList) {
        super(paramFragmentManager);
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            CardInfo localAppModel = (CardInfo) localIterator.next();
            this.mFragments.add(CardFragment.getInstance(localAppModel));
        }
        this.mPostList = paramList;
    }

    public void addCardList(List<CardInfo> cardList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((CardInfo) localIterator.next()));
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(localArrayList);
        this.mPostList.addAll(cardList);
    }

    public List<CardInfo> getCardList() {
        return this.mPostList;
    }

    public int getCount() {
        return this.mFragments.size();
    }

    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    public Fragment getItem(int paramInt) {
        return this.mFragments.get(paramInt);
    }

    public void setCardList(List<CardInfo> cardList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((CardInfo) localIterator.next()));
        this.mFragments = localArrayList;
        this.mPostList = cardList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }
}