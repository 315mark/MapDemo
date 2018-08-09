package com.yangon.baidumap;

import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;


/**
 * Created by Yangon on 2018/8/8
 */

public abstract class RoutePlanSearchBaseActivity extends BaseActivity  implements OnGetRoutePlanResultListener {
    //路线方案搜索
    protected RoutePlanSearch routePlanSearch;

    @Override
    public void init() {
        routePlanSearch = RoutePlanSearch.newInstance();
        //获得路线结果的监听
        routePlanSearch.setOnGetRoutePlanResultListener(this);
        routePlanSearchInit();
    }


    /**
     * 路径搜索初始化代码
     */
    public abstract void routePlanSearchInit();



}
