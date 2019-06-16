## IntentLife ![IntentLife](https://jitpack.io/v/ausboyue/IntentLife.svg)

#### Description
An android library that automatically binds data carried by the Intent.

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
          implementation 'com.github.ausboyue.IntentLife:intentlife:v1.0.0'
          annotationProcessor 'com.github.ausboyue.IntentLife:intentlife_compiler:v1.0.0'
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

## Bugs Report

If you find any bug when using it, please contact [me](mailto:ausboyue@qq.com). Thanks for helping me making better.

## Author

Cheny - @[ausboyue on GitHub](https://github.com/ausboyue/), @[www.icheny.cn](http://www.icheny.cn)

## Other

Please give me some time to update the documentation.

## Release note

### 1.0.0
 - release first version v1.0.0 
 - nothing now
