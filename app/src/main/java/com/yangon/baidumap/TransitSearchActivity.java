package com.yangon.baidumap;

import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

/**
 * Created by Yangon on 2018/8/8
 */

public class TransitSearchActivity extends RoutePlanSearchBaseActivity{


    @Override
    public void routePlanSearchInit() {
        routePlanSearch.transitSearch(getSearchParams());

    }

    private TransitRoutePlanOption getSearchParams() {
        TransitRoutePlanOption params =new TransitRoutePlanOption();
        params.city("深圳"); //设置在那个城市内搜索
        //设置时间优先
        params.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST);
        //设置起点终点
        params.from(PlanNode.withLocation(szPos));
        params.to(PlanNode.withLocation(pinanPos));
        return params;
    }

    /** 获取换乘（公交、地铁）搜索结果的回调方法 */
    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
        baiduMap.setOnMarkerClickListener(overlay);
        //获取到所有的搜索路线 最优路线将显示在前面
        List<TransitRouteLine> routeLines= result.getRouteLines();
        overlay.setData(routeLines.get(0)); // 把搜索结果设置到覆盖物
        overlay.addToMap();         // 把搜索结果添加到地图
        overlay.zoomToSpan();   // 把搜索结果在一个屏幕内显示完
    }


    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
