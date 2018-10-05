package com.rainmonth.image.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.di.module.CollectionDetailMoudle;
import com.rainmonth.image.di.module.CollectionHomeModule;
import com.rainmonth.image.mvp.ui.collection.CollectionDetailActivity;
import com.rainmonth.image.mvp.ui.collection.CollectionHomeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {CollectionDetailMoudle.class}, dependencies = AppComponent.class)
public interface CollectionDetailComponent {
    void inject(CollectionDetailActivity activity);
}