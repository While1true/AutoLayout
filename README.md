# AutoScreen
屏幕适配的简单另类方式

[思路来源于布隆简书]( http://www.jianshu.com/p/b6b9bd1fba4d)

##111Constance中的设计尺寸改为你的设计稿的宽度
public class Constance {
    //设计稿尺寸
    public static final int DESIGN_WIDTH = 750;

    public static void resetDensity(Activity context) {
        Point size = new Point();
        context.getWindowManager().getDefaultDisplay().getSize(size);
        context.getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }
}

##222在baseActivity中设置
  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constance.resetDensity(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Constance.resetDensity(this);
    }
##22222
xml中使用pt单位
