package com.android602_4.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android602_4.MainActivity;

/**
 * Created by 1 on 2018/6/30.
 */

public class BaseFragment extends Fragment {

    private Activity activity;

    public Context getContext(){
        if(activity == null){
            return MainActivity.getInstance();
        }
        return activity;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        activity = getActivity();
//    }

}
