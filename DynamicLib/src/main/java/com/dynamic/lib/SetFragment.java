package com.dynamic.lib;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${zhaoyanjun} on 2017/3/3.
 */

public class SetFragment extends Fragment implements FragmentI {

    @Override
    public Fragment getFragment() {
        return new SetFragment() ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //通过反射获取
        int layoutID = getId( getContext() , "layout" , "fragment_main") ;
        View view = inflater.inflate( layoutID , container , false ) ;
        return view ;
    }

    public static int getId(Context context , String className , String name ){
        return context.getResources().getIdentifier( name , className , context.getPackageName() ) ;
    }
}
