package com.gdxGame.utils;


public class CameraInfo {

	public float x,y;
	public int viewportWidth, viewportHeight;
	public boolean fixed;

	public CameraInfo(float x,float y, boolean fixed,int viewportWidth,int viewportHeight) {
		this.x = x;
		this.y = y;
		this.fixed = fixed;
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
	}

	public CameraInfo(float x, float y, boolean fixed) {
		this(x,y,fixed,80,45);
	}
	public CameraInfo(float x, float y) {
		this(x,y,false);
	}
}
