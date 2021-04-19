package com.imooc.baidumap0924.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imooc.baidumap0924.R;
import com.imooc.baidumap0924.bean.AddressInfo;

/**
 * Created by hyman for imooc.com.
 */

public class AddressView extends RelativeLayout {

    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvDistance;

    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setVisibility(View.GONE);
        LayoutInflater.from(context).inflate(R.layout.item_address_info,this);

        mIvIcon = findViewById(R.id.id_iv_icon);
        mTvName = findViewById(R.id.id_tv_name);
        mTvDistance = findViewById(R.id.id_tv_distance);
    }

    public void setAddressInfo(AddressInfo info){
        mIvIcon.setImageResource(info.getImgId());
        mTvName.setText(info.getName());
        mTvDistance.setText(info.getDistance());

        setVisibility(View.VISIBLE);
    }



}
