package com.yangon.baidumap;

import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

/**
 * Created by Yangon on 2018/8/8
 */

public class WalkingSearchActivity extends RoutePlanSearchBaseActivity{
    @Override
    public void routePlanSearchInit() {
        routePlanSearch.walkingSearch(getSearchParams());
    }

    private WalkingRoutePlanOption getSearchParams() {
        WalkingRoutePlanOption params =new WalkingRoutePlanOption();
        params.from(PlanNode.withLocation(szPos));
        params.to(PlanNode.withLocation(pinanPos));
        return params;
    }

    /** 获取步行搜索结果的回调方法 */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        WalkingRouteOverlay overlay =new WalkingRouteOverlay(baiduMap);
        baiduMap.setOnMarkerClickListener(overlay);
        List<WalkingRouteLine> walkLine=result.getRouteLines();
        overlay.setData(walkLine.get(0));
        overlay.addToMap();
        overlay.zoomToSpan();
    }


    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

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
