package com.altimetrik.sampleapplicationjsonparsing;
/**
 * Created by bkondaiah on 09-10-2015.
 */

public interface Listener {
	
	public void onError(String errorMessage);
	
	public void onResponse(String result, String res);
	
	public void locError(String msg);
	
	public void locSuccess(String msg);
	
}
