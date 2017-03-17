package com.mygdx.game;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import com.gdxGame.Screens.HostScreen;
import com.gdxGame.utils.Bluetooth;


public class AndroidBluetooth implements Bluetooth {

	public static final int REQUEST_ENABLE_BT = 1;
	public static final int DISCOVERY_ENABLED_BT = 2;
	public static final int DISCOVERY_DURATION = 30;

	Context appContext;

	public AndroidBluetooth(Context context) {
		appContext = context;
	}


	public boolean isBluetoothAvailable() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter == null)
			return false;
		else
			return true;
	}

	@Override
	public void turnBluetoothOn() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter != null){
			if(!mBluetoothAdapter.isEnabled()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				((Activity) appContext).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}

	}
	@Override
	public void enableBluetoothDiscovering(HostScreen multiplayerSettingsWindow) {
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERY_DURATION);
		((Activity) appContext).startActivityForResult(discoverableIntent, DISCOVERY_ENABLED_BT );

	}

	@Override
	public boolean startDiscoverBluetooth(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		return mBluetoothAdapter.startDiscovery();
	}
}
