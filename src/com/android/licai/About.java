package com.android.licai;

import com.android.licai.R;
import com.android.licai.data.Constant;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class About extends ActivityGroup {
	private Spinner SpinnerCountries;
	private ArrayAdapter<CharSequence> AdapterCountries;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.about);
	    	
	    	//���� array
	        final String[] Cities={"�Ͼ�","����","�Ϻ�","����","����","����","��"};
	        SpinnerCountries =(Spinner)findViewById(R.id.SpinnerCity);
	        
	        AdapterCountries=new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
	        AdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        SpinnerCountries.setAdapter(AdapterCountries);
	        
	        int LenCountries=Cities.length;
	        
	        for(int i=0;i<LenCountries;i++){
	        	AdapterCountries.add(Cities[i]);
	        }
	        SpinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// ѡ��������
					Log.v(Constant.LiCai,"item select");
					Long l=SpinnerCountries.getSelectedItemId();
					int index=l.intValue();
					Constant.city=Cities[index];
					Toast toast=Toast.makeText(getApplicationContext(), Constant.city, Toast.LENGTH_SHORT);
					toast.show();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// �Ͼ�
					Constant.city="�Ͼ�";
				}
	        	
	        });
	 }
}
