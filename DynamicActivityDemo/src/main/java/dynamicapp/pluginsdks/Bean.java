package dynamicapp.pluginsdks;

import dynamicapp.ii.IBean;

/**
 * Created by ${zhaoyanjun} on 2017/3/6.
 */

public class Bean implements IBean {

    /**
     *
     */
    private String name = "这是来自于插件工程中设置的初始化的名字";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
