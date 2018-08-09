package com.yangon.baidumap;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;



/**
 * Created by Yangon on 2018/8/8
 */

public class MarkerOverlayActivity extends BaseActivity {
    private static final String TAG = "MarkerOverlayActivity";
    private View pop;
    private TextView tv_title;


    @Override
    public void init() {
        initMarker();
        baiduMap.setOnMarkerClickListener(onMarkerClickListener);
        baiduMap.setOnMarkerDragListener(onMarkerDragListener);
    }
    //初始化标志
    private void initMarker() {
        MarkerOptions options=new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_eat);
        options.position(pinanPos) //位置
        .title("平安")
        .icon(icon)
        .draggable(true); //设置图标可拖动
        baiduMap.addOverlay(options);

        //添加一个向北的标至
        options =new MarkerOptions().icon(icon)
                .title("向北")
                .position(new LatLng(pinanPos.latitude+0.001,pinanPos.longitude))
                .draggable(true);
        baiduMap.addOverlay(options);

        //添加一个向东的标志
        options =new MarkerOptions().icon(icon)
                .title("向东")
                .position(new LatLng(pinanPos.latitude,pinanPos.latitude+0.001))
                .draggable(true);
        baiduMap.addOverlay(options);

        //添加一个向西的标志
        options =new MarkerOptions().icon(icon)
                .title("向西南")
                .position(new LatLng(pinanPos.latitude-0.001,pinanPos.latitude-0.001))
                .draggable(true);
        baiduMap.addOverlay(options);
    }

    /**标志拖动监听器*/
    BaiduMap.OnMarkerDragListener onMarkerDragListener =new BaiduMap.OnMarkerDragListener() {
        //标志正在拖动
        @Override
        public void onMarkerDrag(Marker marker) {
            mMapView.updateViewLayout(pop,createLayoutParams(marker.getPosition()));
            showToast("标志正在拖动");
            Log.i(TAG, "onMarkerDrag: 标志正在拖动  ------>>>>>");
        }
        //标志结束拖动
        @Override
        public void onMarkerDragEnd(Marker marker) {
            mMapView.updateViewLayout(pop,createLayoutParams(marker.getPosition()));
            showToast("标志拖动结束");
            Log.i(TAG, "onMarkerDrag: 标志拖动结束  ------>>>>>");
        }
        //标志开始拖动
        @Override
        public void onMarkerDragStart(Marker marker) {
            mMapView.updateViewLayout(pop,createLayoutParams(marker.getPosition()));
            showToast("标志拖动开始");
            Log.i(TAG, "onMarkerDrag: 标志拖动开始  ------>>>>>");
        }
    };
    BaiduMap.OnMarkerClickListener onMarkerClickListener =new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            //显示一个泡泡
            if (pop == null){
                pop = View.inflate(getApplicationContext(),R.layout.pop,null);
                tv_title = (TextView) pop.findViewById(R.id.tv_title);
                mMapView.addView(pop,createLayoutParams(marker.getPosition()));
            }else{
                mMapView.updateViewLayout(pop,createLayoutParams(marker.getPosition()));
            }
            tv_title.setText(marker.getTitle());
            return false;
        }
    };

    //创建一个参数布局
    private MapViewLayoutParams createLayoutParams(LatLng position) {
        MapViewLayoutParams.Builder builder=new MapViewLayoutParams.Builder();
        //指定坐标类型为经纬度
        builder.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
        //设置标志的位置
        builder.position(position);
        builder.yOffset(-25);  // 设置View往上偏移
        MapViewLayoutParams params = builder.build();
        return params;
    }

}
