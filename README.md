# SuperRandy
MaterialDesign以及常用工具类的整理

项目说明：
	本项目采用的API版本为21，添加了最新的android-support-v7.jar库，因为material design很多地方会用到该库里面定义的属性
目前涉及到的UI控件包括：
  	FancyButton
  	CircularProgressButton
  	ToggleButton
  	RoundedImageView
  
目前涉及到的Utils包括：
  	FileUtils
  	LogUtils
  	NetworkUtils
  	PreferencesUtils
  	VolleyUtils
  
待添加的Utils
	ToastUtils.java
	JsonUtils.java
	DensityUtils.java
	CrashLog处理

待添加的UI控件
	ListView
		PullToRefreshListView.java
	CalendarView
		

注意，在FancyButton中使用到了IconFont，其具体用法如下：
	1、将iconfont字体拷贝到android项目的asserts文件夹下；
	2、通过第三方工具，查看iconfont中某个icon对应Unicode编码；
	3、在values\strings.xml文件中为该icon进行声明，格式为<string name="icon_like">&#+该icon对应的Unicode编码
	这样就可以使用icon font中定义好的图标了
