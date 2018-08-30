package com.sfwl.sfplugstockmanager;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by 不听话的好孩子 on 2018/6/6.
 */

public class AutoScreenUtils {

    public static void AdjustDensity(final Application application, final int dpWidth,final boolean ignoreOtherPackage) {
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        final DisplayMetrics sysDisplayMetrics = Resources.getSystem().getDisplayMetrics();
        application.registerActivityLifecycleCallbacks(new CreatLifecycle() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if(ignoreOtherPackage&&!activity.getClass().getName().contains(activity.getPackageName())){
                    return;
                }
                int width=dpWidth;
                if(activity instanceof IFixedScreen){
                    int fixedDP = ((IFixedScreen) activity).fixedDP();
                    if(fixedDP<=0){
                        return;
                    }
                    width=fixedDP;
                }
                float targetDensity = (float)displayMetrics.widthPixels / width;
                float targetScaledDensity = targetDensity * (sysDisplayMetrics.scaledDensity / sysDisplayMetrics.density);
                int targetDensityDpi = (int) (160 * targetDensity);

                displayMetrics.density = targetDensity;
                displayMetrics.scaledDensity = targetScaledDensity;
                displayMetrics.densityDpi = targetDensityDpi;

                DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
                activityDisplayMetrics.density = targetDensity;
                activityDisplayMetrics.scaledDensity = targetScaledDensity;
                activityDisplayMetrics.densityDpi = targetDensityDpi;
            }
        });

    }
    public static void AdjustDensity(final Application application) {
       AdjustDensity(application,360,false);
    }

    private static abstract class CreatLifecycle implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    public static interface IFixedScreen{
        int fixedDP();
    }
}
