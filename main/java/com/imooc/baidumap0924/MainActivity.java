package com.imooc.baidumap0924;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.imooc.baidumap0924.bean.AddressInfo;
import com.imooc.baidumap0924.bean.AddressInfoLab;
import com.imooc.baidumap0924.view.AddressView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mMap;

    private LocationInstance mLocationInstance;
    private BDLocation mLastLocation;

    private SensorInstance mSensorInstance;

    private boolean mIsFirstLocation = true;

    private AddressView mAddressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = findViewById(R.id.bmapView);
        mMap = mMapView.getMap();
        mMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15f));
        // 开启定位图层
        mMap.setMyLocationEnabled(true);

        mAddressView = findViewById(R.id.id_address_view);

        init();

        initEvent();
    }

    private void initEvent() {

        mMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mAddressView.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        mMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                Bundle extraInfo = marker.getExtraInfo();
                AddressInfo addressInfo = (AddressInfo) extraInfo.getSerializable(KEY_SHOP_INFO);
                Log.d("imooc_map" , addressInfo.getName());
                // show view
                mAddressView.setAddressInfo(addressInfo);
                return false;
            }
        });

    }

    private void init() {
        initLocationDetect();

        initSensorDetect();
    }

    private void initSensorDetect() {
        mSensorInstance = new SensorInstance(getApplicationContext());
        mSensorInstance.setOnOrientationChangedListener(
                new SensorInstance.OnOrientationChangedListener() {
                    @Override
                    public void onOrientation(float x) {
                        // 设置定位图标；
                        if (mLastLocation == null) {
                            return;
                        }

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mLastLocation.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(x).latitude(mLastLocation.getLatitude())
                                .longitude(mLastLocation.getLongitude()).build();

                        // 设置定位数据
                        mMap.setMyLocationData(locData);

                        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                        .fromResource(R.drawable.navi_map_gps_locked);
                        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                                true, null);
                        mMap.setMyLocationConfiguration(config);


                        if (mIsFirstLocation) {
                            mIsFirstLocation = false;
                            LatLng point = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                            mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
                        }


                        // 当不需要定位图层时关闭定位图层
//                mMap.setMyLocationEnabled(false);

                    }
                });
    }

    private void initLocationDetect() {
        mLocationInstance = new LocationInstance(this,
                new LocationInstance.MyLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation location) {
                        super.onReceiveLocation(location);
                        mLastLocation = location;

                        Log.d("imooc_map", location.getAddrStr() + " , " + location.getLatitude() + " , " + location.getLongitude());

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationInstance.start();
        mSensorInstance.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationInstance.stop();
        mSensorInstance.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    private static final int ITEM_ID_NORMAL_MAP = 101;
    private static final int ITEM_ID_SATELLITE_MAP = 102;
    private static final int ITEM_LOCATION = 103;
    private static final int ITEM_SHOW_SHOPS = 104;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, ITEM_ID_NORMAL_MAP, 0, "切换为普通地图");
        menu.add(Menu.NONE, ITEM_ID_SATELLITE_MAP, 0, "切换为卫星地图");
        menu.add(Menu.NONE, ITEM_LOCATION, 0, "定位到我的位置");
        menu.add(Menu.NONE, ITEM_SHOW_SHOPS, 0, "显示所有商家");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case ITEM_ID_NORMAL_MAP:
                mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case ITEM_ID_SATELLITE_MAP:
                mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case ITEM_LOCATION:
                mMap.clear();
                //定义Maker坐标点
                LatLng point = new LatLng(mLastLocation.getLatitude(),
                        mLastLocation.getLongitude());
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.navi_map_gps_locked);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                // mMap.addOverlay(option);

                mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
                break;
            case ITEM_SHOW_SHOPS:
                mAddressView.setVisibility(View.GONE);
                // 1.遍历商家，添加marker
                showShops();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final String KEY_SHOP_INFO = "key_shop_info";

    private void showShops() {
        mMap.clear();
        List<AddressInfo> addressInfos = AddressInfoLab.generateDatas();
        for (AddressInfo addressInfo : addressInfos) {
            //定义Maker坐标点
            LatLng point = new LatLng(addressInfo.getLatitude(),
                    addressInfo.getLongitude());
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.maker);

            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_SHOP_INFO, addressInfo);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .extraInfo(bundle)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mMap.addOverlay(option);
        }
        if (addressInfos.isEmpty()) {
            return;
        }
        LatLng point = new LatLng(addressInfos.get(0).getLatitude(),
                addressInfos.get(0).getLongitude());
        mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
    }
}
