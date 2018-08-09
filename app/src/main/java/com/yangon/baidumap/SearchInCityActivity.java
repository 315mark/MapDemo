package com.yangon.baidumap;

import android.view.KeyEvent;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;

/**
 * Created by Yangon on 2018/8/9
 */

public class SearchInCityActivity extends PoiSearchBaseActivity{
    private  int pageNum;

    @Override
    public void poiSearchinit() {
        poiSearch.searchInCity(getSearchParams());
    }

    @Override
    public boolean onPoiClick(int index) {
        // 当点击一个搜索出来的兴趣点的时候，再去搜索更详细的内容
        PoiInfo poiInfo= poiOverlay.getPoiResult().getAllPoi().get(index);
        poiSearch.searchPoiDetail(getSearchDetailParams(poiInfo.uid));
        return super.onPoiClick(index);
    }

    private PoiDetailSearchOption getSearchDetailParams(String uid) {
        PoiDetailSearchOption option =new PoiDetailSearchOption();
        option.poiUid(uid);
        return option;
    }

    private PoiCitySearchOption getSearchParams() {
        PoiCitySearchOption option = new PoiCitySearchOption();
        option.city("深圳");  //指定在哪个城市进行搜索
        option.keyword("超市");     //指定搜索是什么内容
        option.pageCapacity(10);  //设置一页获取10条数据
        option.pageNum(pageNum);  //指定获取那一页
        return option;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_1){
            //获取下一页数据
            pageNum ++;
            poiSearch.searchInCity(getSearchParams());
        }
        return super.onKeyDown(keyCode, event);
    }

    /** 获取到详情信息的回调方法 */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result ==null || result.error != SearchResult.ERRORNO.NO_ERROR){
            showToast("没有搜索到详情信息");
        }
        showToast(result.getShopHours() + ", " + result.getTelephone());
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


}
