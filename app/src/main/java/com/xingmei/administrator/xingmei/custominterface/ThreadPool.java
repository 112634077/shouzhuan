package com.xingmei.administrator.xingmei.custominterface;

public interface ThreadPool {
    /**
     * 操作开始前执行 数据刚开始获取
     */
    void onExecute();

    /**
     * 较为费时的操作执行结束  数据解析在这里面操作
     * @param result 返回的json数据集
     */
    void onInBackground(String result);

    /**
     * 操作失败
     */
    void onCliable();
}
