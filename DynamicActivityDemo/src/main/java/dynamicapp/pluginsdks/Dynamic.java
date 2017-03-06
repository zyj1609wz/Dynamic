package dynamicapp.pluginsdks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import dynamicapp.ii.IDynamic;
import dynamicapp.ii.YKCallBack;

/**
 * Created by ${zhaoyanjun} on 2017/3/6.
 */

public class Dynamic implements IDynamic {
    /**

     */
    public void methodWithCallBack(YKCallBack callback) {
        Bean bean = new Bean();
        bean.setName("PLUGIN_SDK_USER");
        callback.callback(bean);
    }

    public void showPluginWindow(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("对话框");
        builder.setTitle( "hello word " );
        builder.setNegativeButton("取消", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();//.show();
        dialog.show();
    }

    public void startPluginActivity(Context context,Class<?> cls){
        /**
         *这里要注意几点:
         *1、如果单纯的写一个MainActivity的话，在主工程中也有一个MainActivity，开启的Activity还是主工程中的MainActivity
         *2、如果这里将MainActivity写成全名的话，还是有问题，会报找不到这个Activity的错误
         */
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }

    public String getStringForResId(Context context){
        return "哈哈哈" ;
    }

}