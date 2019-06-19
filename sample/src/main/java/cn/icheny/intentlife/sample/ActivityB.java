package cn.icheny.intentlife.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.icheny.intentlife.IntentLife;
import cn.icheny.intentlife.annotation.BindIntentKey;

public class ActivityB extends AppCompatActivity {
    @BindIntentKey("key_user")
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        //  IntentLife inject
        IntentLife.bind(this);

        TextView tv_user_desc = findViewById(R.id.tv_user_desc);
        tv_user_desc.setText(
                "Hello , I am " + mUser.getName()
                        + ".\nMy job is " + mUser.getJob() + ".");

        // if use fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_frag_b_container, FragmentB.newInstance(mUser)).commit();
    }
}
