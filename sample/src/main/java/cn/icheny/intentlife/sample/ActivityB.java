package cn.icheny.intentlife.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.icheny.intentlife.IntentLife;
import cn.icheny.intentlife.annotation.BindIntentKey;

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
