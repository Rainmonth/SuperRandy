package com.rainmonth.image.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.CollectionDetailContract;
import com.rainmonth.image.mvp.model.CollectionDetailModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CollectionDetailMoudle {
    private CollectionDetailContract.View view;
    public CollectionDetailMoudle(CollectionDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CollectionDetailContract.View provideCollectionDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CollectionDetailContract.Model provideCollectionDetailModel(CollectionDetailModel model) {
        return model;
    }

}
