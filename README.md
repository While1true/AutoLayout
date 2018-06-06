

# AutoScreen
屏幕适配的简单另类方式

效果 


[思路来源于布隆简书]( http://www.jianshu.com/p/b6b9bd1fba4d)

[玩Android文章](https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA)

## 使用
> 在application的oncreat中调用即可 第二个参数是您设计图稿的宽度dp
```
AutoScreenUtils.AdjustDensity(this,dp);
```

## 原理
> 系统的尺寸计算 是通过 Typevalue.applyDimension
> 可看出 尺寸和metrics.density有关
> 而DisplayMetrics的density scaledDensity等这些变量都是public static的
> 我们可以通过修改这个值来适配
 ```
 public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics)
    {
        switch (unit) {
        case COMPLEX_UNIT_PX:
            return value;
        case COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f/72);
        case COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f/25.4f);
        }
        return 0;
    }
 ```
 
 > 每个activity，Application的ResourcesImpl都是新建对象，内部对mMetrics.setToDefaults();
 > 这说明每个DisplayMetrics的值在activty实例创建的时候又重新初始化了
 > 我们只要在每个Activityoncreat中给它设置好需要的值就好了
 ```
  @Override
    public Resources getResources() {
        return mBase.getResources();
    }
    
    //ResourcesImpl
     @Deprecated
    public Resources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        this(null);
        mResourcesImpl = new ResourcesImpl(assets, metrics, config, new DisplayAdjustments());
    }
    
    //
     public ResourcesImpl(@NonNull AssetManager assets, @Nullable DisplayMetrics metrics,
            @Nullable Configuration config, @NonNull DisplayAdjustments displayAdjustments) {
        mAssets = assets;
        mMetrics.setToDefaults();
        mDisplayAdjustments = displayAdjustments;
        mConfiguration.setToDefaults();
        updateConfiguration(config, metrics, displayAdjustments.getCompatibilityInfo());
        mAssets.ensureStringBlocks();
    }

 ```
 


## 实现
```
package com.refresh.autoscreendpi;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by 不听话的好孩子 on 2018/6/6.
 */

public class AutoScreenUtils {
    private static float originalScaledDensity;

    public static void AdjustDensity(final Application application, final int dpWidth) {
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        final float originalDensity = displayMetrics.density;
        originalScaledDensity = displayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    originalScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
        application.registerActivityLifecycleCallbacks(new CreatLifecycle() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                float targetDensity = (float)displayMetrics.widthPixels / dpWidth;
                float targetScaledDensity = targetDensity * (originalScaledDensity / originalDensity);
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
       AdjustDensity(application,360);
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

}

```
