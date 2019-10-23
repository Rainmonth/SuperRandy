# SuperRandy

## 为什么要做这个项目？

- 让自己时刻保持学习的热情；
- 有一定的代码沉淀；
- 熟悉移动开发常用的技能

一个自己业余时间开发的App，内容包括:
- 项目迁移至androidX(done 2019/07/31)
- 基本的五个模块的设计 (todo)
- MaterialDesign以及常用工具类的整理; (under working)
- MVP模式的运用 (done)
- 简单的Php服务端（项目地址及说明见下面地址）(todo)

##界面设计
- BaseActivity (todo)
- ZhuiFragment (todo)
- XunFragment (已完成基本框架搭建)
- YouFragment (doing)

##后台设计
- 阅读模块可使用豆瓣读书API
- 新闻模块可使用网易API
- 图片模块可使用干货集中营API
- DiyCode Api也可以参考

##Widgets

- NavigationTabBar (used)
- FancyButton (to be used)
- CircularProgressButton (to be used)
- ToggleButton (to be used)
- RoundedImageView (to be used)
- ListView
    - PullToRefreshListView.java (to be used)
- CalendarView (to be used)

##Utils

- DateUtils.java ——日期处理工具类(added)
- FileUtils (to be added)
- LogUtils (to be added)
- NetworkUtils (added)
- PreferencesUtils (to be added)
- ToastUtils.java (added)
- JsonUtils.java (to be added)
- CrashLog处理 (to be added)


##签到功能设计

- todo 进入时做引导，签到后给予签到成功的反馈(如显示今日格言啊，显示今日推荐的文章链接，显示今日推荐的音乐视频等)，注意用户签到或者不签到都要给予操作上的引导

##补充说明

- 本项目采用的API版本为21，添加了最新的android-support-v7.jar库，因为material design很多地方会用到该库里面定义的属性

- 注意，在FancyButton中使用到了IconFont，其具体用法如下：
    1. 将iconfont字体拷贝到android项目的asserts文件夹下；
    2. 通过第三方工具，查看iconfont中某个icon对应Unicode编码；
    3. 在values\strings.xml文件中为该icon进行声明，格式为<string name="icon_like">&#+该icon对应的Unicode编码这样就可以使用icon font中定义好的图标了

    ## How to use banner

    ```java
    String[] images = new String[]{
      	"http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
        "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
        "http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg",
        "http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg",
        "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
        "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"
    };
        //设置图片标题:自动对应
        String[] titles = new String[]{"全场2折起", "十大星级品牌联盟", "嗨购5折不要停", "12趁现在", "嗨购5折不要停，12.12趁现在", "实打实大顶顶顶顶"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            banner = (Banner) findViewById(R.id.banner);
            //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
            //可选样式如下:
            //1. Banner.CIRCLE_INDICATOR	显示圆形指示器
            //2. Banner.NUM_INDICATOR	显示数字指示器
            //3. Banner.NUM_INDICATOR_TITLE	显示数字指示器和标题
            //4. Banner.CIRCLE_INDICATOR_TITLE	显示圆形指示器和标题
            banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);

            //设置轮播样式（没有标题默认为右边,有标题时默认左边）
            //可选样式:
            //Banner.LEFT	指示器居左
            //Banner.CENTER	指示器居中
            //Banner.RIGHT	指示器居右
            banner.setIndicatorGravity(Banner.CENTER);
            //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
            banner.setBannerTitle(titles);
            //设置是否自动轮播（不设置则默认自动）
            banner.isAutoPlay(true);
            //设置轮播图片间隔时间（不设置默认为2000）
            banner.setDelayTime(5000);
            //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
            //所有设置参数方法都放在此方法之前执行
            //banner.setImages(images);
            //自定义图片加载框架
            banner.setImages(images, new Banner.OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    System.out.println("加载中");
                    Glide.with(getApplicationContext()).load(url).into(view);
                    System.out.println("加载完");
                }
            });
            //设置点击事件，下标是从1开始
            banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
                @Override
                public void OnBannerClick(View view, int position) {
                    Toast.makeText(getApplicationContext(), "你点击了：" + position, 							Toast.LENGTH_LONG).show();
                }
            });
        }
    ```

    注意：
    
    Android Studio V3.4.2支持一键迁移至androidX，具体方法如下：
    
    项目更目录，右键，refactor，migrate to androidX即可，迁移过程中遇到的问题：
    
    - 低版本的glide提示`duplicate entry META-INF/services/javax.annotation.processing.Processor`，在packageOptions中添加exclude后仍无效，升级glide版本至4.8就没问题了
    - 一些库的引用必须手动更改
