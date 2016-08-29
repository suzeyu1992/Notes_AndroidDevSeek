##《android开发艺术探索》读书练习

> 目录下每一个`Note_AndroidDevSeek_xx`对应xx章的部分代码练习



### 相应Blog地址

* [第1章:Activity的生命周期和模式](http://szysky.com/2016/08/10/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B04-View%E7%9A%84%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86/)
* [第2章:IPC机制](http://szysky.com/2016/08/02/Android%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2-%E7%AC%94%E8%AE%B002-IPC%E6%9C%BA%E5%88%B6/)
* [第3章:View的事件机制](http://szysky.com/2016/08/08/Android%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2-%E7%AC%94%E8%AE%B003-View%E7%9A%84%E4%BA%8B%E4%BB%B6%E4%BD%93%E7%B3%BB/)
* [第4章:View的工作原理](http://szysky.com/2016/08/10/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B04-View%E7%9A%84%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86/)
* [第5章:理解RemoteViews](http://szysky.com/2016/08/11/%E3%80%8AAndroid%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-05-%E7%90%86%E8%A7%A3RemoteViews/)
* [第6章:AndroidDrawable](http://szysky.com/2016/08/12/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-06-Android%E7%9A%84Drawable/)
* [第7章:Android动画深入分析](http://szysky.com/2016/08/13/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-07-Andriod%E5%8A%A8%E7%94%BB%E6%B7%B1%E5%85%A5%E5%88%86%E6%9E%90/)
* [第8章:理解Window和WindowManager](http://szysky.com/2016/08/15/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-08-%E7%90%86%E8%A7%A3Window%E5%92%8CWindowManager/)
* [第9章:四大组件的工作过程](http://szysky.com/2016/08/16/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-09-%E5%9B%9B%E5%A4%A7%E7%BB%84%E4%BB%B6%E7%9A%84%E5%B7%A5%E4%BD%9C%E8%BF%87%E7%A8%8B/)
* [第10章:Android的消息机制](http://szysky.com/2016/08/22/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-10-Android%E7%9A%84%E6%B6%88%E6%81%AF%E6%9C%BA%E5%88%B6/)
* [第11章:Android的线程和线程池](http://szysky.com/2016/08/22/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-11-Android%E7%9A%84%E7%BA%BF%E7%A8%8B%E5%92%8C%E7%BA%BF%E7%A8%8B%E6%B1%A0/)
* [第12章:Bitmap的加载和Cache](http://szysky.com/2016/08/23/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-12-Bitmap%E7%9A%84%E5%8A%A0%E8%BD%BD%E5%92%8CCache/)
* [第13章:综合技术](http://szysky.com/2016/08/25/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-13-%E7%BB%BC%E5%90%88%E6%8A%80%E6%9C%AF/)
* [第14章:JNI和NDK编程](http://szysky.com/2016/08/26/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-14-JNI%E5%92%8CNDK%E7%BC%96%E7%A8%8B/)
* [第15章:Android性能优化](http://szysky.com/2016/08/27/%E3%80%8AAndroid-%E5%BC%80%E5%8F%91%E8%89%BA%E6%9C%AF%E6%8E%A2%E7%B4%A2%E3%80%8B-15-Android%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96/)


