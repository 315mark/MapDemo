package com.yangon.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.yangon.baidumap.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yangon on 2018/8/6
 */

public abstract class BaseActivity extends Activity {

    public LocationClient mLocationClient =null;
    public MyLocationListener myListener=new MyLocationListener();
    //1. 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
    MyLocationConfiguration.LocationMode mCurrentMode= MyLocationConfiguration.LocationMode.NORMAL;

    private static final String TAG = "BaseActivity";

    /** 深圳坐标  经纬度倒置*/
    protected LatLng szPos = new LatLng(22.5406916566,113.9809863496);
    /** 天安门坐标 */
    protected LatLng pinanPos = new LatLng(22.5385054566,114.0620441464);

    protected BaiduMap baiduMap;

    @InjectView(R.id.bmapView)
    MapView mMapView = null;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //获取地图的控制器
        baiduMap=mMapView.getMap();
        // NORMAL 普通地图（包含3D地图)  SATELLITE 卫星图  NONE(空白地图)
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        // 1.	隐藏缩放按钮、比例尺
//        mMapView.showScaleControl(false);	// 隐藏比例按钮，默认是显示的
//        mMapView.showZoomControls(false);	// 隐藏缩放按钮，默认是显示的
        // 	获取获取最小（3）、最大缩放级别（20）
        float maxZoomLevel = baiduMap.getMaxZoomLevel(); // 获取地图最大缩放级别
        float minZoomLevel = baiduMap.getMinZoomLevel(); // 获取地图最小缩放级别
        Log.i(TAG, "minZoomLevel  ---->>> " + minZoomLevel + ", maxZoomLevel ---->>>" + maxZoomLevel);
        //2. 开启交通路况
//        baiduMap.setTrafficEnabled(true);
        //3. 开启城市热力图
//        baiduMap.setBaiduHeatMapEnabled(true);

        //4. 自定义定位模式
        Locate();

        //设置地图中心点
        MapStatusUpdate mapStatusUpdate= MapStatusUpdateFactory.newLatLng(szPos);
        baiduMap.setMapStatus(mapStatusUpdate);

        // 自动匹配定位 声明locationClient   注册监听函数
        mLocationClient =new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        // 当不需要定位图层时关闭定位图层
        baiduMap.setMyLocationEnabled(true);
        mLocationClient.start();
        //进行初始化操作 --->>面向接口编程  必须放在最下方 否则会报空指针
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_1:
                // 罗盘态，显示定位方向圈，保持定位图标在地图中心
                mCurrentMode=MyLocationConfiguration.LocationMode.COMPASS;
                Locate();
                break;

            case KeyEvent.KEYCODE_2:
                // 跟随态，保持定位图标在地图中心
                mCurrentMode=MyLocationConfiguration.LocationMode.FOLLOWING;
                Locate();
                break;

            case KeyEvent.KEYCODE_3:
                // 普通态： 更新定位数据时不对地图做任何操作
                mCurrentMode=MyLocationConfiguration.LocationMode.NORMAL;
                Locate();
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode,event);
    }


    private void initLocation() {
        //核心代码 配置定位SDK参数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true); //如需获取定位信息 必须为true
        //可选默认高精度，设定定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911"); //默认gcj02 设置返回固定坐标
        int span = 5000;
        option.setScanSpan(span); //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setOpenGps(true);
        option.setLocationNotify(true);//设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);

    }

    private void Locate() {
//        MyLocationConfiguration  构造方法祥用

        //6. 定位精度圈大小 定位精度圈大小 ，是根据当前定位精度自动控制的，无法手动控制大小。精度圈越小，代表当前定位精度越高；反之圈越大，代表当前定位精度越低。
        //7. 定位指针方向   定位指针朝向，是通过获取手机系统陀螺仪数据，控制定位指针的方向，需要开发者自己实现，并不在地图实现范畴。
        //2. 自定义定位图标
        BitmapDescriptor mCurrentMarker= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        //3. 自定义精度圈填充颜色
        int accuracyCircleFillColor = 0xAAFFFF88;
        //5. 自定义精度圈边框颜色
        int accuracyCircleStrokeColor = 0xAA00FF00;
        Log.i(TAG, "Locate:  mCurrentMode ----->>>"+mCurrentMode);
        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration( mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));

    }

    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //在这个方法里面接收定位结果
            if (bdLocation == null) {
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.accuracy(bdLocation.getRadius()); //设置精度
                builder.direction(bdLocation.getDirection()); //设置方向
                builder.latitude(bdLocation.getLatitude()); //设置纬度
                builder.longitude(bdLocation.getLongitude()); //设置经度
                Log.i(TAG, "onReceiveLocation: "+"bdLocation.getRadius()="+bdLocation.getRadius()+"bdLocation.getLatitude()"+bdLocation.getLatitude()+"bdLocation.getDirection()"+bdLocation.getDirection()+"bdLocation.getLongitude()"+bdLocation.getLongitude());
                MyLocationData locationData = builder.build();
                //吧定位数据显示到地图上
                baiduMap.setMyLocationData(locationData);
            }

            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取位置描述信息相关的结果
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String locationDescribe = bdLocation.getLocationDescribe();    //获取位置描述信息

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(bdLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(bdLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(bdLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(bdLocation.getRadius());
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(bdLocation.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(bdLocation.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(bdLocation.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(bdLocation.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
            List<Poi> list = bdLocation.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    public abstract void init();

    /**
     * 在屏幕中央显示一个Toast
     * @param text
     */
    public void showToast(CharSequence text) {
        ToastUtil.showToast(this, text);
    }
}
