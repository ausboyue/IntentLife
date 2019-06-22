package cn.icheny.intentlife.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.icheny.intentlife.IntentLife;
import cn.icheny.intentlife.annotation.BindIntentKey;


public class FragmentB extends Fragment {
    @BindIntentKey("key_user")
    User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  IntentLife inject
        IntentLife.bind(this);

        View rootView = inflater.inflate(R.layout.frag_b_layout, container, false);
        final TextView tv_user_desc = rootView.findViewById(R.id.tv_user_desc);
        tv_user_desc.setText("Hello , I am " + mUser.getName()
                + ".\nMy job is " + mUser.getJob() + ".");
        return rootView;
    }

    public static FragmentB newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable("key_user", user);
        FragmentB fragment = new FragmentB();
        fragment.setArguments(args);
        return fragment;
    }
}
