这个组件是在研究了大量的开源播放器后写出来的

Android端常用的开源音乐播放器有：

1. VideoPlayerManager
   项目地址：https://github.com/danylovolokh/VideoPlayerManager
2. GSYVideoPlayer
   项目地址：https://github.com/CarGuo/GSYVideoPlayer
3. ijkPlayer
   项目地址：https://github.com/bilibili/ijkplayer

- 研究开源项目


core包结构说明
- bridge        该包主要定义Manager（Player）与View（SurfaceView等）的交互操作）
- cache         该包主要定义各种播放器实现对应的缓存实现
- factory       用来创建Manager的工厂方法
- helper        辅助工具类
- manager       播放器管理
- player        播放器实现
- render        渲染实现（基本实现）
- view          渲染实现（具体实现）
- SuperManger   超管