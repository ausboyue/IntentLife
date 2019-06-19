package cn.icheny.intentlife.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentB extends Fragment {

    User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_b_layout, container, false);
        final TextView tv_user_desc = rootView.findViewById(R.id.tv_user_desc);
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
