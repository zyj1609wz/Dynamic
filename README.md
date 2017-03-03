# Dynamic Android 动态加载实践
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






## 参考资料

- [Android动态加载Dex机制解析](http://blog.csdn.net/wy353208214/article/details/50859422)