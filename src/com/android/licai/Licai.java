package com.android.licai;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.android.licai.data.Constant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.net.NetworkInfo; 

public class Licai extends TabActivity {
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		//Constant.licaidb.close();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	//�����ݿ�
    	Constant.licaidb = openOrCreateDatabase(Constant.DBname, Context.MODE_PRIVATE, null);
    	
    	//�������ݿ�str
    	String DbCreate = "create table " + Constant.DBTable + 
    						"( _id integer primary key autoincrement,"+
    						"money double not null,"+
    						"date Date not null,"+
    						"item text not null,"+
    						"addr text,"+
    						"info text);";
    	
    //	ɾ�����ݿ�
    //	Constant.licaidb.execSQL("drop table "+Constant.DBTable);
    	try{
    		//���Խ������ݿ�
    		Constant.licaidb.execSQL(DbCreate);
    	}catch(SQLException e){
    		Log.e(Constant.LiCai,e.getMessage());
    	}
    	
    	Constant.queue = new ArrayList<String>();
    	
    	//����tab
    	Resources res 	= getResources();
    	TabHost tabHost = getTabHost(); 
    	TabHost.TabSpec spec;
    	Intent intent	= null;
    	  	

    	// ������һ��intent
    	intent = new Intent().setClass(this, Manage.class);

    	// ���õ�һ��tab�ϵ�ͼ�������
    	spec = tabHost.newTabSpec("manage").setIndicator("��֧����",
    	                          res.getDrawable(R.drawable.manage_tab_view))
    	                      	.setContent(intent);
    	tabHost.addTab(spec);

    	// as below
    	intent = new Intent().setClass(this, Statistics.class);
        spec = tabHost.newTabSpec("statistics").setIndicator("����ͳ��",
        						  res.getDrawable(R.drawable.stat_tab_view))
        						  .setContent(intent);
    	tabHost.addTab(spec);
    	
    	//api��ʹ������  
    	/*
    	intent = new Intent().setClass(this, ExchangeRate.class);
    	spec = tabHost.newTabSpec("exchangerate").setIndicator("����",
    							  res.getDrawable(R.drawable.licai_tab_view))
    							  .setContent(intent);
    	tabHost.addTab(spec);
		*/
    	intent = new Intent().setClass(this, About.class);
    	spec = tabHost.newTabSpec("about").setIndicator("����",
    	                      res.getDrawable(R.drawable.about_tab_view))
    	                  .setContent(intent);
    	    tabHost.addTab(spec);
 
    	tabHost.setCurrentTab(0);
    	
    	//internet�����ж�
    	
    	//if(!checkForInternetConnection()) {
    	//	showDialog(DIALOG_YES_NO_MESSAGE);
    	//	Constant.connectToInternet = false;
    	//}
    	//else Constant.connectToInternet = true;
    	
    }
    
    private static final int DIALOG_YES_NO_MESSAGE = 1;
    
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	switch (id) {
        case DIALOG_YES_NO_MESSAGE:
            return new AlertDialog.Builder(Licai.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_content)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                })
                /*.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })*/
                .create();
    	}
		return null;
    }
    
    //���internet״̬
	private boolean checkForInternetConnection(){
		
		String strUrl = "http://code.google.com/intl/zh-CN/apis/chart/";
		String enCoding = "utf-8";
		HttpURLConnection urlConnection = null;
		
		int timeOut = 1;
		try{
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setConnectTimeout(timeOut*1000);
			urlConnection.setRequestProperty("User-Agent","Mozilla/4.0"+" (compatible; MSIE 6.0; Windows 2000)");
			urlConnection.setRequestProperty("Content-type", "text/html; charset="+enCoding);
			urlConnection.connect();
		}catch(Exception e){
			Log.e(Constant.LiCai,e.getMessage());
			return false;
		}
		urlConnection.disconnect();
		
		return true;
		/*ConnectivityManager nw=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo=nw.getActiveNetworkInfo();
		return netinfo.isAvailable();*/
	}
}