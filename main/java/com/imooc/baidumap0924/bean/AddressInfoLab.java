package com.imooc.baidumap0924.bean;

import com.imooc.baidumap0924.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyman for imooc.com.
 */

public class AddressInfoLab {

    public static List<AddressInfo> generateDatas(){
        List<AddressInfo> addressInfos = new ArrayList<>();
        // http://api.map.baidu.com/lbsapi/getpoint/index.html
        addressInfos.add(new AddressInfo(40.076683, 116.32626, R.drawable.a1, "龙泽地铁站",
                "距离209米"));
        addressInfos.add(new AddressInfo(40.084936, 116.323493, R.drawable.a2, "北京龙禧医院",
                "距离897米"));
        addressInfos.add(new AddressInfo(40.082397, 116.338657, R.drawable.a3, "龙腾苑3区",
                "距离249米"));
        addressInfos.add(new AddressInfo(40.070596, 116.324589, R.drawable.a4, "蓝天嘉园",
                "距离679米"));


        return addressInfos;
    }

}
