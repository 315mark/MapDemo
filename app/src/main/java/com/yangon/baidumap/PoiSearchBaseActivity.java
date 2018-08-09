package com.yangon.baidumap;

import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by Yangon on 2018/8/9
 */

public abstract class PoiSearchBaseActivity extends BaseActivity implements OnGetPoiSearchResultListener {

    protected PoiSearch poiSearch;
    protected PoiOverlay poiOverlay;

    @Override
    public final void init() {
        //因为其他搜索也需要这个实例 所以放在父类初始,这样的话就不需要在实例化
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

        poiOverlay = new PoiOverlay(baiduMap) {
            @Override
            public boolean onPoiClick(int index) {
                return PoiSearchBaseActivity.this.onPoiClick(index);
            }
        };

        baiduMap.setOnMarkerClickListener(poiOverlay);
        poiSearchinit();
    }

    public abstract void poiSearchinit();


    // 生成这个方法 是为了让子类覆盖
    public boolean onPoiClick(int index) {
        PoiInfo poiInfo= poiOverlay.getPoiResult().getAllPoi().get(index);
        showToast(poiInfo.name + ","+poiInfo.address);
        return true;
    }

    /**
     * 因为其他搜索的结果处理都相同 所以放在父类
     * 获取兴趣点信息
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult ==null || poiResult.error != SearchResult.ERRORNO.NO_ERROR){
            showToast("没有搜索到结果");
            return;
        }
        //把数据设置给覆盖物
        poiOverlay.setData(poiResult);
        poiOverlay.addToMap(); // 把所有的数据的变成覆盖添加到BaiduMap
        poiOverlay.zoomToSpan();  // 把所有的搜索结果在一个屏幕内显示出来
    }
}
