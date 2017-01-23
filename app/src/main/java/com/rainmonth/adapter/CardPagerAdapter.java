package com.rainmonth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rainmonth.bean.CardBean;
import com.rainmonth.fragment.CardFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
    private List<CardBean> mPostList;
    private List<Fragment> mFragments = new ArrayList();

    public CardPagerAdapter(FragmentManager paramFragmentManager, List<CardBean> paramList) {
        super(paramFragmentManager);
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            CardBean localAppModel = (CardBean) localIterator.next();
            this.mFragments.add(CardFragment.getInstance(localAppModel));
        }
        this.mPostList = paramList;
    }

    public void addCardList(List<CardBean> cardBeanList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardBeanList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((CardBean) localIterator.next()));
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(localArrayList);
        this.mPostList.addAll(cardBeanList);
    }

    public List<CardBean> getCardList() {
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

    public void setCardList(List<CardBean> cardBeanList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardBeanList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((CardBean) localIterator.next()));
        this.mFragments = localArrayList;
        this.mPostList = cardBeanList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }
}