package com.gdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.gdxGame.GameActors.Box;
import com.gdxGame.GameActors.Picks;
import com.gdxGame.utils.CameraInfo;
import com.gdxGame.utils.Level;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;


public class Levels {

	Level level;

	public Levels(){
		level = new Level(new World(new Vector2(0,-98.1f),true),new Group(),new CameraInfo(0,0));
	}

	public Level load(String levelLoaded) {

		JSONObject jLevels = new JSONObject(loadJsonFile("Levels.json"));
		JSONArray levelArray = jLevels.getJSONArray(levelLoaded);
		for (int i = 0; i < levelArray.length(); i++) {
			String type = levelArray.getJSONObject(i).getString("type");
			float x = (float)levelArray.getJSONObject(i).getDouble("x");
			float y = (float)levelArray.getJSONObject(i).getDouble("y");
			if(type.equals("box")) {
				float w = (float)levelArray.getJSONObject(i).getDouble("w");
				float h = (float)levelArray.getJSONObject(i).getDouble("h");
				box(x,y,w,h);
			}
			else if(type.equals("picks")) {
				int nb = levelArray.getJSONObject(i).getInt("nb");
				picks(x,y,nb);
			}
			else if(type.equals("camera")) {
				boolean fixed = levelArray.getJSONObject(i).getBoolean("fixed");
				int w = levelArray.getJSONObject(i).getInt("w");
				int h = levelArray.getJSONObject(i).getInt("h");
				level.camInfo = new CameraInfo(x,y,fixed,w,h);
			}
			else if(type.equals("world")) {
				level.world.setGravity(new Vector2(x,y));
			}
		}

		return level ;
	}

	private void box(float x,float y,float w, float h) {
		level.actors.addActor(new Box(level.world,x,y,w,h));
	}
	private void picks(float x,float y,int nb) {
		level.actors.addActor(new Picks(level.world,x,y,nb));
	}

	private String loadJsonFile(String path) {
		String jsonData = "";
		BufferedReader br = null;
		try {
			String line;
			FileHandle file = Gdx.files.internal(path);
			br = new BufferedReader(file.reader());
			while ((line = br.readLine()) != null) {
				jsonData += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return jsonData;
	}
}
