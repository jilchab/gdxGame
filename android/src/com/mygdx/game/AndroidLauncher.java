package com.mygdx.game;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gdxGame.GameName;

public class AndroidLauncher extends AndroidApplication {

	BroadcastReceiver bluetoothReceiver;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode= true;
		initialize(new GameName(new AndroidBluetooth(this)), config);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(bluetoothReceiver);
		super.onDestroy();
	}
}
