


# AutoScreen
屏幕适配的简单另类方式
/**
 * -------------------------------------------------------------------------------------------------------
 * 按屏幕宽度等比例适配】
 * dp模式下按正常情况使用
 * dp模式下系统空间某些drawable会受影响，显示偏差，如RatingBar 解决办法是设置maxheight=minheight=某个值
 *
 *---------------------------------------------------------------------------------------------------------
 * pt模式
 * 先生成pt xml
 * 使用dp的地方用生成的dimens
 * 使用麻烦，但不会影响系统控件，
 * 适配：控件尽量使用match_parent wrap_content margin和padding 用pt格式
 * --------------------------------------------------------------------------------------------------------
 * 
 *把设计稿的尺寸方向和density配置进来
 * 在application中调用Adjustutil.adjust
 */
 
 
[思路来源于布隆简书]( http://www.jianshu.com/p/b6b9bd1fba4d)

##111AdjustUtil中的设计尺寸改为你的设计稿的宽度
```
 private static Orention type = Orention.PORT;
    private static Unit unit = Unit.PT;
    private static int DESIGN_WIDTHs = 1080;
    private static int DESIGN_HEIGHTs = 1920;
    private static float DESIGN_SCALEs = 3.0f;
```

##222在Application中设置
```
  @Override
    protected void onCreate() {
        super.onCreate();
        AdjustUtil.adjust(this);
    }




##333
xml中使用pt单位
