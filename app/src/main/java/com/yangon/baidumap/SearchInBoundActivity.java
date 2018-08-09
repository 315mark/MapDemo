package com.yangon.baidumap;


import android.util.Log;
import android.view.KeyEvent;

import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * Created by Yangon on 2018/8/7
 */

public class SearchInBoundActivity extends PoiSearchBaseActivity{
    private static final String TAG = "SearchInBoundActivity";

    @Override
    public void poiSearchinit() {
        poiSearch.searchInBound(getSearchParams());

    }

    public PoiBoundSearchOption getSearchParams() {
        PoiBoundSearchOption option =new PoiBoundSearchOption();
        // 指定搜索范围，由一个西南点和一个东北点组成的范围 22.5406916566  113.9809863496
        LatLngBounds bounds=new LatLngBounds.Builder().include(new LatLng(22.540659,113.980686))
                .include(new LatLng(22.540629,113.980486)).build();
        option.bound(bounds);	// 指定搜索范围
        option.keyword("加油站");	// 指定搜索什么内容
        return option;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 5.	更新地图状态
        MapStatusUpdate mapStatusUpdate = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                // 		1)	缩小
                mapStatusUpdate = MapStatusUpdateFactory.zoomOut();
                break;
            case KeyEvent.KEYCODE_2:
                // 		2)	放大
                mapStatusUpdate = MapStatusUpdateFactory.zoomIn();
                break;
            case KeyEvent.KEYCODE_3:
                // 		3)	旋转（0 ~ 360），每次在原来的基础上再旋转30度
                MapStatus currentMapStatus = baiduMap.getMapStatus();	// 获取地图当前的状态
                float rotate = currentMapStatus.rotate + 30;
                Log.i(TAG, "rotate = " + rotate);
                MapStatus mapStatus = new MapStatus.Builder().rotate(rotate).build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

                break;
            case KeyEvent.KEYCODE_4:
                // 		4)	俯仰（0 ~ -45），每次在原来的基础上再俯仰-5度
                currentMapStatus = baiduMap.getMapStatus();	// 获取地图当前的状态
                float overlook = currentMapStatus.overlook - 5;
                Log.i(TAG, "overlook = " + overlook);
                mapStatus = new MapStatus.Builder().overlook(overlook).build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

                break;
            case KeyEvent.KEYCODE_5:
                // 		5)	移动
                mapStatusUpdate = MapStatusUpdateFactory.newLatLng(szPos);
                baiduMap.animateMapStatus(mapStatusUpdate, 2000);
                return super.onKeyDown(keyCode, event);
        }
        baiduMap.setMapStatus(mapStatusUpdate);
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    /** 获取兴趣点详情信息 */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
