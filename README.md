# Dynamic Android 动态加载实践

## 这个工程可以实现那些功能

- 动态加载普通的类

- 动态加载Fragment

## Android 动态加载步骤

1、把需要动态加载的类打成`Jar`包。比如：`dynamic.jar`

2、把打成的`Jar`包打成`dex`包，注意最后打成的dex包也是以`.jar`结尾的。比如：`dynamic.jar` -> `dynamic_dex.jar`

3、把`dex`包放到可以实现动态加载的工程的`assets`目录下。

## 如何把工程打成Jar包

![](/png/pic.png)

- 在工程的`build.gradle`里面添加

```
task getJar(type: Copy) {
    delete 'build/libs/dynamic.jar'
    from('build/intermediates/bundles/release/')
    into('build/libs/')
    include('classes.jar')
    rename('classes.jar', 'dynamic.jar')
}

//终端输入 gradlew :DynamicLib:getJar 生成jar
getJar.dependsOn(build)
```

- 在命令行终端输入 `gradlew :DynamicLib:getJar`就会看到在`module`的`build/libs`看到`dynamic.jar`

## 如何把Jar包打成dex

- 找到Android SDK的`build-tools\24.0.0` 目录，如图所示：
![](/png/pic_dex.png)


- 将`dynamic.jar` 拷贝到上面的目录，然后打开`CMD` 输入命令：

 `dx --dex --output=dynamic_dex.jar dynamic.jar`

  最后在这个目录生成的`dynamic_dex.jar`  就是我们需要的dex文件。

## Dex包里面有什么？

我们就以`dynamic_dex.jar` dex包为例，把它解压，可以看到两个文件
![](/png/dex_unzip.png)


## 动态加载需要注意的事项

#### 1、善于使用接口编程
由于我们最终动态加载的是`Dex`文件,但是`Dex`包中的类都不能直接调用。那么怎么调用我们需要的类呢？通常有两种方法，如下
- 用Java反射 ----- [Java 反射 使用总结](http://www.cnblogs.com/zhaoyanjun/p/6074887.html)
- 面向接口编程 ---- 本工程就是使用的这个方法

可以看到在例子中，我们用了两个接口`Dynamic`,`FragmentI`。`DynamicImpl`实现`Dynamic`接口；`SetFragment`实现`FragmentI`接口。
不过这里需要注意的是：`DynamicLib`中的接口，需要在`app`工程里面重新写一份，并且包名要相同，都是`com.dyncmic.lib` 。


### 2、使用Android中的反射初始化控件
操作方法详见：[Android 反射-换一种方式编程](http://www.cnblogs.com/zhaoyanjun/p/6484767.html)

具体到本工程的代码如下：
```
package com.dynamic.lib;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${zhaoyanjun} on 2017/3/3.
 */

public class SetFragment extends Fragment implements FragmentI {

    @Override
    public Fragment getFragment() {
        return new SetFragment() ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //通过反射获取
        int layoutID = getId( getContext() , "layout" , "fragment_main") ;
        View view = inflater.inflate( layoutID , container , false ) ;
        return view ;
    }

    public static int getId(Context context , String className , String name ){
        return context.getResources().getIdentifier( name , className , context.getPackageName() ) ;
    }
}

```

## 相关知识详解

- ClassLoader
在`java`中，有个概念叫做`"类加载器"`（`ClassLoader`），它的作用就是动态的装载`Class`文件。标准的`java sdk`中有一个`ClassLoader`类，借助这个类可以装载想要的`Class`文件，每个`ClassLoader`对象在初始化时必须制定`Class`文件的路径。

- DexClassLoader
`dex`文件是一种经过`android`打包工具优化后的`Class`文件，因此加载这样特殊的`Class`文件就需要特殊的类装载器，所以android中提供了`DexClassLoader`类。这个类加载器用来从`.jar`和`.apk`类型的文件内部加载`classes.dex`文件。通过这种方式可以用来执行非安装的程序代码，作为程序的一部分进行运行。

DexClassLoader 构造函数

DexClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent)

首先我们在

## 延伸
动态加载本身是插件开发里面的知识，本项目只是动态加载`DEX`文件。动态加载也是可以加载`APK`文件的，这里不做说明，感兴趣的自己可以搜索资料学习一下。

#### 更高级的插件开发可以参考已经成熟的插件框架。
 - [360手机助手插件框架DroidPlugin](https://github.com/DroidPluginTeam/DroidPlugin)
 - [携程插件框架DynamicAPK](https://github.com/CtripMobile/DynamicAPK)

## 参考资料

- [Android动态加载Dex机制解析](http://blog.csdn.net/wy353208214/article/details/50859422)
- [DL动态加载框架技术文档](http://blog.csdn.net/singwhatiwanna/article/details/40283117)
- [Android博客周刊专题之＃插件化开发＃](http://www.androidblog.cn/index.php/Index/detail/id/16#)