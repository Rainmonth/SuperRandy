# SuperRandy
一个自己业余时间开发的App，内容包括:

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
