package com.yangon.baidumap;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.yangon.baidumap.bean.ClassAndName;
import com.yangon.baidumap.utils.ToastUtil;

/**
 * Created by Yangon on 2018/8/7
 */

public class DemoListActivity extends ListActivity {
    private BroadcastReceiver receiver;
    private ClassAndName[] datas={new ClassAndName(DemoListActivity.class,"百度地图选择页面") ,
           new ClassAndName(MainActivity.class,"自动定位"),
           new ClassAndName(SearchInBoundActivity.class,"范围内搜索"),
           new ClassAndName(DrivingSearchActivity.class,"驾车路线搜索"),
            new ClassAndName(TransitSearchActivity.class, "换乘路线搜索"),
            new ClassAndName(WalkingSearchActivity.class, "步行路线搜索"),
            new ClassAndName(TextOverlayActivity.class, "文字覆盖物"),
            new ClassAndName(MarkerOverlayActivity.class, "标志覆盖物"),
            new ClassAndName(SearchInCityActivity.class, "在城市内搜索"),
            new ClassAndName(SearchInNearbyActivity.class, "在周边内搜索"),
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //广播监听百度SDK
        registerSDKCheckReceiver();
        ArrayAdapter<ClassAndName> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 取出单击位置的ClassAndName
        ClassAndName classAndName = (ClassAndName) l.getItemAtPosition(position);
        startActivity(new Intent(this, classAndName.clazz));
    }

    private void registerSDKCheckReceiver() {
        receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
                    ToastUtil.showToast(DemoListActivity.this,"网络错误");
                }else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
                    ToastUtil.showToast(DemoListActivity.this,"key验证失败");
                }
            }
        };

        IntentFilter filter= new IntentFilter();
        //监听网络错误
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        //监听百度地图SDK 的key是否正确
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
