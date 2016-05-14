package com.lilisoft.openpulseband.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.util.ArrayMap;

import java.util.List;


/**
 * This class manage bluetooth le device.
 */
public class ManageBLE {

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner = null;
    private boolean mScanning;
    private Handler mHandler;
    private ArrayMap<String, BluetoothDevice> mLeDeviceListAdapter;
    private ScanCallback mLeScanCallback =
            new ScanCallback() {
                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    for (ScanResult item : results) {
                        mLeDeviceListAdapter.put(item.getDevice().getName(), item.getDevice());
                    }
                }
            };

    /**
     * Instantiates a new Manage ble.
     *
     * @param mBluetoothAdapter the m bluetooth adapter
     */
    public ManageBLE(BluetoothAdapter mBluetoothAdapter) {
        this.mLeDeviceListAdapter = new ArrayMap<>();
        this.mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mLEScanner.stopScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mLEScanner.startScan(mLeScanCallback);
        } else {
            mScanning = false;
            mLEScanner.stopScan(mLeScanCallback);
        }
    }
}
