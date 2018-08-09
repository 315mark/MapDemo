package com.yangon.baidumap;

import com.baidu.mapapi.map.TextOptions;

/**
 * Created by Yangon on 2018/8/8
 */

public class TextOverlayActivity extends BaseActivity{

    @Override
    public void init() {
        TextOptions options =new TextOptions();
        options.position(pinanPos)  //位置
        .text("平安金融大厦")
        .fontSize(20)
        .fontColor(0xFF000000)  //字体颜色
        .bgColor(0x55FF0000)  //背景颜色
        .rotate(90);  //旋转90
        baiduMap.addOverlay(options);
    }
}
