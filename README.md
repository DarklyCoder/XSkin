# XSkin

侵入式换肤框架，干涉view的创建过程。目前只支持以插件形式加载的皮肤。

## **XSkin 导入**

```
implementation 'com.darklycoder.lib:XSkin:1.0.4'
```

## **XSkin的使用方法**

### **初始化**

首先在 `Application` 的 `onCreate` 中进行初始化：

```
SkinManager.getInstance().init(this);
```

### **修改BaseActivity和BaseFragment**

参考 `SkinActivity` 和 `SkinFragment`

### **XML换肤**

xml布局中的View需要换肤的，只需要在布局文件中相关View标签下添加 `skin:enable="true"` 即可,例如：

``` xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:skin="http://schemas.android.com/darklycoder/xskin"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/status_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/color_theme"
        skin:enable="true"/>
<RelativeLayout/>
``` 

### **xml中指定换肤属性**

xml中假如出现了多个可换肤属性，但只需要换其中部分属性，而不是全部属性，比如：

``` xml
<ImageView
    android:id="@+id/iv_dog"
    android:layout_width="180dp"
    android:layout_height="40dp"
    android:background="@drawable/bg_theme"
    android:src="@drawable/ic_dog" 
    skin:enable="true"/>
```

这个布局中，包含两个换肤属性：`background` ，`src`，假如只想换 `src` ,那该怎么办？
此处，增加一个属性 `attrs` ，在此属性中声明需要换肤的属性。
具体到上面的例子，只需要增加这样一行代码 `skin:attrs="src"` 就行：

```xml
<ImageView
    android:id="@+id/iv_dog"
    android:layout_width="180dp"
    android:layout_height="40dp"
    android:background="@drawable/bg_theme"
    android:src="@drawable/ic_dog" 
    skin:attrs="src"
    skin:enable="true"/>
```

如果支持多个属性，使用 `|` 分割就行：

``` 
skin:attrs="src|background"
```

### **新增换肤属性**

框架内置了部分属性，在实际项目中可能需要新增换肤属性，例如：
想要实现 `gif` 换肤，首先定义 `GifViewAttr` 继承 `SkinAttr`,实现换肤逻辑，最后添加到 `AttrFactory`；

```
 AttrFactory.addExtAttr("gif", new GifViewAttr());
```

## **TODO**

1. 完善 Wiki；
2. 降低侵入性；
3. 适配 Android X；
4. 添加内置换肤支持；

## **Thanks**

1. [XSkinLoader](https://github.com/WindySha/XSkinLoader)
2. [QSkinLoader](https://github.com/qqliu10u/QSkinLoader)
