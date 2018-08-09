package com.yangon.baidumap;


import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yangon on 2018/8/8
 */

public class DrivingSearchActivity extends RoutePlanSearchBaseActivity {


    @Override
    public void routePlanSearchInit() {
        routePlanSearch.drivingSearch(getSearchParams());
    }

    private DrivingRoutePlanOption getSearchParams() {
        DrivingRoutePlanOption params =new DrivingRoutePlanOption();
        List<PlanNode> nodes =new ArrayList<>();
        nodes.add(PlanNode.withCityNameAndPlaceName("深圳市","世界之窗"));
        params.from(PlanNode.withLocation(szPos));//设置起点
        params.passBy(nodes); //设置途经点
        params.to(PlanNode.withLocation(pinanPos));
        return params;
    }


    /** 获取驾车搜索结果的回调方法 */
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        DrivingRouteOverlay overlay =new DrivingRouteOverlay(baiduMap);
        baiduMap.setOnMarkerClickListener(overlay);
        //获取到所有的搜所路线
        List<DrivingRouteLine> routeLines = result.getRouteLines();
        overlay.setData(routeLines.get(0));	// 把搜索结果设置到覆盖物
        overlay.addToMap();					// 把搜索结果添加到地图
        overlay.zoomToSpan();				// 把搜索结果在一个屏幕内显示完

    }



    /** 获取步行搜索结果的回调方法 */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }
    /** 获取换乘（公交、地铁）搜索结果的回调方法 */
    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }
    /** 获取公交路线搜索结果的回调方法 */
    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }
    /** 获取室内路线 结果的回调方法 */
    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }
    /** 获取自行车路线 结果的回调方法 */
    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
