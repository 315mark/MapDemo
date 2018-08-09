package com.yangon.baidumap;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;

/**
 * Created by Yangon on 2018/8/9
 */

public class SearchInNearbyActivity extends PoiSearchBaseActivity{


    @Override
    public void poiSearchinit() {
        poiSearch.searchNearby(getSearchParams());

    }

    private PoiNearbySearchOption getSearchParams() {
        PoiNearbySearchOption option=new PoiNearbySearchOption();
        option.location(szPos);
        option.radius(1000);
        option.keyword("平安银行");
        return option;
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


}
