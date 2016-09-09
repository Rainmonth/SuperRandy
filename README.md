# SuperRandy
一个自己业余时间开发的App，内容包括:

- 基本的五个模块的设计 (todo)
- MaterialDesign以及常用工具类的整理; (under working)
- MVP模式的运用 (done)
- 简单的Php服务端（项目地址及说明见下面地址）(todo)
- 修复AppExploreActivity 页面再次进入崩溃的bug（done）


##界面设计
- BaseActivity (todo)
- MainActivity (done)
- SplashActivity (done)
- RenFragment (todo)
- RanFragment (todo)
- ZhuiFragment (todo)
- XunFragment (已完成基本框架搭建)
- YouFragment (todo)

##后台设计

##Widgets

- NavigationTabBar (used)
- FancyButton (to be used)
- CircularProgressButton (to be userd)
- ToggleButton (to be used)
- RoundedImageView (to be used)
- ListView
    - PullToRefreshListView.java (to be used)
- CalendarView (to be used)

##Utils

- DensityUtils.java ——尺寸处理工具类(added)
- CacheUtils.java ——缓存处理工具类(added)
- DateUtils.java ——日期处理工具类(added)
- FileUtils (to be added)
- LogUtils (to be added)
- NetworkUtils (to be added)
- PreferencesUtils (to be added)
- OkHttpUtils (to be added)
- ToastUtils.java (to be added)
- JsonUtils.java (to be added)
- CrashLog处理 (to be added)

##补充说明
	
- 本项目采用的API版本为21，添加了最新的android-support-v7.jar库，因为material design很多地方会用到该库里面定义的属性
- 注意，在FancyButton中使用到了IconFont，其具体用法如下：
    1. 将iconfont字体拷贝到android项目的asserts文件夹下；
    2. 通过第三方工具，查看iconfont中某个icon对应Unicode编码；
    3. 在values\strings.xml文件中为该icon进行声明，格式为<string name="icon_like">&#+该icon对应的Unicode编码这样就可以使用icon font中定义好的图标了
