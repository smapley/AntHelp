package com.smapley.anthelp.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.smapley.anthelp.R;
import com.smapley.base.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuzhixiong on 2017/5/2.
 */
@ContentView(R.layout.activity_test)
public class Test extends BaseActivity {

    private static final String TAG = "TEST----->>";
    @ViewInject(R.id.listView)
    private ListView listView;

    private SimpleAdapter adapter;
    private List<Map<String,String>> list = new ArrayList<Map<String,String>>();

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private boolean mScanning;
    private Handler mHandler = new Handler();
    // 10秒后停止寻找.
    private static final long SCAN_PERIOD = 10000;

    @Override
    public void initArgument() {

        final BluetoothManager bluetoothManager= (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(this,R.string.error_bluetooth_not_supported,Toast.LENGTH_SHORT).show();
        }else{
            //开启蓝牙
            bluetoothAdapter.enable();
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }




    }

    @Override
    public void initView() {
        adapter = new SimpleAdapter(this,list,R.layout.layout_item,new String[]{"name","uuid"},new int[]{R.id.name,R.id.uuid});
        listView.setAdapter(adapter);
    }


    private void scanLeDevice(final boolean enable){
        if(enable){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothLeScanner.stopScan(scanCallback);
                }
            },SCAN_PERIOD);
            mScanning = true;
            bluetoothLeScanner.startScan(scanCallback);
        }else{
            mScanning = false;
            bluetoothLeScanner.stopScan(scanCallback);
        }
    }

    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            if(device !=null) {
                Log.d(TAG, "Device name: " + device.getName());
                Log.d(TAG, "Device address: " + device.getAddress());
                Log.d(TAG, "Device service UUIDs: " + device.getUuids());

                ScanRecord record = result.getScanRecord();
                Log.d(TAG, "Record advertise flags: 0x" + Integer.toHexString(record.getAdvertiseFlags()));
                Log.d(TAG, "Record Tx power level: " + record.getTxPowerLevel());
                Log.d(TAG, "Record device name: " + record.getDeviceName());
                Log.d(TAG, "Record service UUIDs: " + record.getServiceUuids());
                Log.d(TAG, "Record service data: " + record.getServiceData());
                Log.d(TAG, "------------------------------------------------");

                Map map = new HashMap();
                map.put("name", device.getAddress());
                map.put("uuid", device.getUuids());
                list.add(map);
            }
        }
    };

    @Event({R.id.start,R.id.stop})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.start:
                scanLeDevice(true);
                break;
            case R.id.stop:
                scanLeDevice(false);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
