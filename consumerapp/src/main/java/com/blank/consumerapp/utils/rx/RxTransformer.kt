package com.blank.consumerapp.utils.rx

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.singleIo(): Single<T> = compose {
    it.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
