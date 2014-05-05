package ch.eth.soms.mosgap.nervous;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.util.Log;

public class ServiceInfo extends MainActivity {
	
	private SharedPreferences preferences;
	
	private static final String DEBUG_TAG = "ServiceInfo";


	public ServiceInfo(Context context){
		preferences = context.getSharedPreferences("ServiceInfo", MODE_PRIVATE);
	}
	
	public void cleanOnServiceStart(){
        Editor editor = preferences.edit();
        editor.putLong("first", 0);
        editor.putLong("last", 0);
        editor.putInt("amountOfFrames", 0);
        editor.commit();
	}
	
	public void frameAdded(){
        Editor editor = preferences.edit();
        int amount = preferences.getInt("amountOfFrames", 0) + 1;
        editor.putInt("amountOfFrames", amount);
        editor.commit();
	}
	
	public int getAmountOfFrames(){
		return preferences.getInt("amountOfFrames", 0);
	}
	
	public long getTimeOfFirstFrame(){
		long first = preferences.getLong("first", 0);
		return first;
	}
	
	public void setTimeOfLastFrame(){

        Editor editor = preferences.edit();
		long time = SystemClock.elapsedRealtime();
        editor.putLong("last", time);
        
        if(preferences.getLong("first", 0) == 0){
        	editor.putLong("first", SystemClock.elapsedRealtime());        	
    		Log.d(DEBUG_TAG, "Time of first frame has been set.");
        }
        frameAdded();
        editor.commit();
	}
	
	public boolean ServiceIsRunning(){
		
		long last = preferences.getLong("last", 0);
		long now = SystemClock.elapsedRealtime();
		
		
		if(last != 0 && Math.abs((now-last)/1000) < 60){
			return true;
		}
		else{
			return false;
		}
	}
}