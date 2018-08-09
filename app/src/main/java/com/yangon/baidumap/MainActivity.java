package com.yangon.baidumap;


import android.view.KeyEvent;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;

public  class MainActivity extends BaseActivity implements MKOfflineMapListener {
    MKOfflineMap offline;

    @Override
    public void init() {

        //创造一个圆形覆盖物的参数
        CircleOptions options=new CircleOptions();
        options.center(szPos)  //设置圆心
               .radius(1000)   //半径
               .stroke(new Stroke(20, 0x55FF0000))  //线条宽度 颜色
               .fillColor(0x55FF0000) ; //圆的填充物
        baiduMap.addOverlay(options);


        offline=new MKOfflineMap();
        //传入接口事件 离线地图更新会触发回调
        offline.init(this);
        //打开室内图，默认关闭模式
        baiduMap.setIndoorEnable(true);
        //设置监听事件来监听进入和移出室内图
        baiduMap.setOnBaseIndoorMapListener(new BaiduMap.OnBaseIndoorMapListener() {
            @Override
            public void onBaseIndoorMapMode(boolean b, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
                if (b){
                    // 通过获取回调参数 mapBaseIndoorMapInfo 便可获取室内图信息，包含楼层信息，室内ID等
                }else {
                    //移除室内图
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_1:
                //显示普通地图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                baiduMap.setTrafficEnabled(false);
                break;
            case KeyEvent.KEYCODE_2:
                //显示卫星地图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                baiduMap.setTrafficEnabled(false);
                break;
            case KeyEvent.KEYCODE_3:
                //显示交通地图
                baiduMap.setTrafficEnabled(true);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type){
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE :{
                MKOLUpdateElement update= offline.getUpdateInfo(state);
                //下载进度提示
                if (update !=null){
                   if (update.ratio ==100){

                   }
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        offline.destroy();
        super.onDestroy();
    }


}
