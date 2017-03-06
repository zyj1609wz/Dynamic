package dynamicapp.ii;

import android.content.Context;

/**
 * Created by ${zhaoyanjun} on 2017/3/6.
 */

public interface IDynamic {
    public abstract void methodWithCallBack(YKCallBack paramYKCallBack);
    public abstract void showPluginWindow(Context paramContext);
    public abstract void startPluginActivity(Context context,Class<?> cls);
    public abstract String getStringForResId(Context context);
}
