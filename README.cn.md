

## IntentLife

**[English](./README.md) | 简体中文**

![License](https://img.shields.io/github/license/ausboyue/IntentLife.svg) ![platform](https://img.shields.io/badge/platform-android-green.svg) ![jitpack](https://jitpack.io/v/ausboyue/IntentLife.svg) ![Latest](https://img.shields.io/badge/Latest-1.0.3-brightgreen.svg) ![RepoSize](https://img.shields.io/badge/RepoSize-156KB-blue.svg) ![CoreSize](https://img.shields.io/badge/CoreSize-3.2KB-blue.svg)


#### 介绍

一个自动绑定Intent或Bundle携带的数据的android库。

## 从Gradle下载

在项目根目录下的build.gradle添加仓库地址：

```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

在需要使用框架的Module下的build.gradle添加依赖：

```groovy
    dependencies {
          implementation 'com.github.ausboyue.IntentLife:intentlife:v1.0.3'
          annotationProcessor 'com.github.ausboyue.IntentLife:intentlife_compiler:v1.0.3'
    }
```


## 开始使用

**ActivityA跳转ActivityB**

- ActivityA跳转代码可能如下：

```java
        User user = new User();
        user.setUserId("9527");
        user.setName("Cheny");
        user.setJob("android developer");

        Intent intent = new Intent(activityA, ActivityB.class);
        intent.putExtra("key_user", user);
        startActivity(intent);
```

- ActivityB使用代码如下：

``` java
public class ActivityB extends AppCompatActivity {
    @BindIntentKey("key_user")
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        //  IntentLife inject
        IntentLife.bind(this);

        TextView tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_name.setText(
                "Hello , I am " + mUser.getName()
                        + ".\nMy job is " + mUser.getJob() + ".");
    }
}
```


### 框架支持

#### 数据类型 

- [x] 支持java八大基本数据类型及其数组和集合
- [x] 支持实现java序列化Serializable接口的类
- [x] 支持实现android序列化Parcelable接口的类及其数组和集合
- [x] 支持android Bundle所支持的所有数据类型

#### 界面场景

- [x] 支持Activity间的跳转
- [x] 支持加载Fragment
- [x] 支持在任何需要使用数据的类中使用，如MVP设计中的Presenter类

### 使用注意

需要的绑定的属性不应有`private`修饰，否则无法正常绑定数据。

## Bugs反馈

如果你在使用期间发现了bug，请联系[我](mailto:ausboyue@qq.com)。帮助我将它做得更好，谢谢。

## 关于作者

Cheny - @[ausboyue on GitHub](https://github.com/ausboyue/), @[乘月网|www.icheny.cn](http://www.icheny.cn)

## 其它

请给我一些时间去完善文档哈^_^

