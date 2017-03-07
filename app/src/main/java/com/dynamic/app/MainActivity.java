package com.dynamic.app;

import android.opengl.GLSurfaceView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import dalvik.system.DexClassLoader;
import com.dynamic.lib.Dynamic;
import com.dynamic.lib.FragmentI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载某一个类
        loadDexClass();

        //加载一个fragment
        Fragment fragment = loadFragment() ;
        if ( fragment != null  ){
            FragmentManager fragmentManager = getSupportFragmentManager() ;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
            fragmentTransaction.add( R.id.fragmentrle ,fragment  ) ;
            fragmentTransaction.commit() ;
        }
    }

    /**
     * 加载dex文件中的class，并调用其中的sayHello方法
     */
    private void loadDexClass() {
        //下面开始加载dex class
        DexClassLoader dexClassLoader = FileUtils.getDexClassLoader( this ) ;
        try {
            Class libClazz = dexClassLoader.loadClass("com.dynamic.lib.DynamicImpl");
            Dynamic dynamic = (Dynamic) libClazz.newInstance();
            if (dynamic != null)
                Toast.makeText(this, dynamic.sayHello() , Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Fragment loadFragment() {
        //下面开始加载dex class
        DexClassLoader dexClassLoader = FileUtils.getDexClassLoader( this ) ;
        try {
            Class libClass = dexClassLoader.loadClass("com.dynamic.lib.SetFragment");
            FragmentI fragmentI = (FragmentI) libClass.newInstance();
            if ( fragmentI != null ){
                return fragmentI.getFragment() ;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null ;
    }

}
