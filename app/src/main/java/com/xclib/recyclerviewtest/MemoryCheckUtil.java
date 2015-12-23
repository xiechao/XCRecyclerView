package com.xclib.recyclerviewtest;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class MemoryCheckUtil {
    @SuppressWarnings("unused")
    private static final String TAG = "MemoryCheckUtil";

    private static final List<WeakReference<Object>> activityRefList = Collections.synchronizedList(new ArrayList<WeakReference<Object>>());

    public static void addObject(Object object) {
        WeakReference<Object> objectWeakReference = new WeakReference<>(object);

        activityRefList.add(objectWeakReference);
    }

    private static void printf() {
        ArrayList<String> currentObjectArrayList = new ArrayList<>();

        ArrayList<WeakReference<Object>> objectList = new ArrayList<>(activityRefList);
        for (WeakReference<Object> objectRef : objectList) {
            Object object = objectRef.get();
            if (object != null) {
                currentObjectArrayList.add(object.toString());
            }
        }

        Collections.sort(currentObjectArrayList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {

                return lhs.compareTo(rhs);
            }
        });

        String str = "";

        for (String node : currentObjectArrayList) {
            str += (node + "\n");
        }

        Log.w(TAG, str);
    }

    public static void printfDelay() {
        rx.Observable.create(
                new rx.Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(final Subscriber<? super Boolean> subscriber) {
                        System.gc();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        rx.Observable.create(
                                new rx.Observable.OnSubscribe<Boolean>() {
                                    @Override
                                    public void call(final Subscriber<? super Boolean> subscriber) {
                                        printf();
                                    }
                                })
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe();

                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }
}
