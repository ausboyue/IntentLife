

## IntentLife 

**English | [简体中文](./README.cn.md)**

![License](https://img.shields.io/github/license/ausboyue/IntentLife.svg) ![platform](https://img.shields.io/badge/platform-android-green.svg) ![jitpack](https://jitpack.io/v/ausboyue/IntentLife.svg) ![Latest](https://img.shields.io/badge/Latest-1.0.4-brightgreen.svg) ![RepoSize](https://img.shields.io/badge/RepoSize-167KB-blue.svg) ![CoreSize](https://img.shields.io/badge/CoreSize-3.1KB-blue.svg)


#### Description

An android library that automatically binds data carried by the Intent or Bundle.

## Download from Gradle

Add to your root build.gradle:
```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Add the dependency:
```groovy
    dependencies {
          implementation 'com.github.ausboyue.IntentLife:intentlife:v1.0.4'
          annotationProcessor 'com.github.ausboyue.IntentLife:intentlife_compiler:v1.0.4'
    }
```


## Get Started

**ActivityA jump to ActivityB**

- ActivityA edit code maybe as below:

```java
        User user = new User();
        user.setUserId("9527");
        user.setName("Cheny");
        user.setJob("android developer");

        Intent intent = new Intent(activityA, ActivityB.class);
        intent.putExtra("key_user", user);
        startActivity(intent);
```

- ActivityB edit code as below:

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


### Framework support

#### Type of data 

- [x] Support java eight basic data types and their arrays and collections
- [x] Classes that support the implementation of the Serializable interface
- [x] Support for classes that implement Parcelable interfaces and their arrays and collections
- [x] Support all data types supported by android Bundle

#### Interface scenario

- [x] Support jump between Activities
- [x] Support loading Fragments
- [x] Support for use in any class that needs to use data, such as the Presenter class in MVP design

### Prompt

The target field should not have the `private` modifier, otherwise the data will not be bound with field.

## Bugs Report

If you find any bug when using it, please contact [me](mailto:ausboyue@qq.com). Thanks for helping me making better.

## About Author

Cheny - @[ausboyue on GitHub](https://github.com/ausboyue/), @[www.icheny.cn](http://www.icheny.cn)

## Other

Please give me some time to update the documentation ^_^

