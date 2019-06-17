package cn.icheny.intentlife.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityA extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jump(View view) {
        User user = new User();
        user.setUserId("9527");
        user.setName("Cheny");
        user.setJob("android developer");

        Intent intent = new Intent(this, ActivityB.class);
        intent.putExtra("key_user", user);
        startActivity(intent);
    }
}
