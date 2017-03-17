package com.gdxGame.utils;

import com.gdxGame.Screens.HostScreen;

public interface Bluetooth {

	boolean isBluetoothAvailable();

	void turnBluetoothOn();

	void enableBluetoothDiscovering(HostScreen multiplayerSettingsWindow);

	boolean startDiscoverBluetooth();

}