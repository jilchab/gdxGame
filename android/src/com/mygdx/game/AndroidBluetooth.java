package com.mygdx.game;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.gdxGame.Screens.HostScreen;
import com.gdxGame.utils.Bluetooth;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class AndroidBluetooth implements Bluetooth {

	public static final int REQUEST_ENABLE_BT = 1;
	public static final int DISCOVERY_ENABLED_BT = 2;
	public static final int DISCOVERY_DURATION = 30;
	public static final UUID MY_UUID = UUID.randomUUID();
	//		UUID.fromString("ABCDABCDABCDABCDABCDABCDABCDABCD");

	Context appContext;
	//private  BluetoothSocket socket;
	public Set<BluetoothDevice> devices;


	public AndroidBluetooth(Context context) {
		appContext = context;
		devices = new HashSet<>();
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
		if(!isBluetoothAvailable()) return;
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

		BluetoothSocket socket;
		((AndroidLauncher)appContext).bluetoothReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					if (devices.add(device)) {
						if(device.getName()!=null) System.out.println(device.getName() + " : "+device.getAddress());
						else System.out.println("no name : "+device.getAddress());
						try {
							BluetoothSocket tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		appContext.registerReceiver(((AndroidLauncher)appContext).bluetoothReceiver, filter);

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		return mBluetoothAdapter.startDiscovery();
	}

	@Override
	public void getBondedDevice(){

		devices.addAll(BluetoothAdapter.getDefaultAdapter().getBondedDevices());
		for (BluetoothDevice d : devices) {
			System.out.println(d.getName());
		}
		System.out.println();
	}

	private void pairingDevice(BluetoothDevice device,boolean pairOrUnpair) {

		((AndroidLauncher)appContext).bluetoothReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
					final int state        = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
					final int prevState    = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

					if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
						System.out.println("Paired");
					} else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
						System.out.println("Unpaired");
					}

				}
			}
		};

		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		appContext.registerReceiver(((AndroidLauncher)appContext).bluetoothReceiver, filter);

		if(pairOrUnpair) {
			try {
				Method method = device.getClass().getMethod("createBond", (Class[]) null);
				method.invoke(device, (Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Method method = device.getClass().getMethod("removeBond", (Class[]) null);
				method.invoke(device, (Object[]) null);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private boolean hostBegin(BluetoothDevice device,boolean pairOrUnpair) throws IOException {

		BluetoothServerSocket  serverSocket =
				BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("gdxGame",MY_UUID);
		BluetoothSocket socket = null;
		while (true) {
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				break;
			}

			if (socket != null) {
				// A connection was accepted. Perform work associated with
				// the connection in a separate thread.
				serverSocket.close();
				return true;
			}
		}
		return false;
	}

}
