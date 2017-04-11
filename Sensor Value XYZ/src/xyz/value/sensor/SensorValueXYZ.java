package xyz.value.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import java.util.List;


public class SensorValueXYZ extends Activity
{
	SensorManager sensMgr = null;
	TextView textView =null;
	List list;
	
	
	SensorEventListener sensEvLis = new SensorEventListener ()
	{
		public void onAccuracyChanged (Sensor sensor,int accuracy)
		{
			
		}
		public void onSensorChanged (SensorEvent event)
		{
			float [] values = event.values;
			textView.setText("x:+"+values[0]+"\ny:"+values[1]+"\nz:"+values[2]);
		}
	};
   
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        sensMgr=(SensorManager)getSystemService(SENSOR_SERVICE);
        
        textView=(TextView)findViewById(R.id.textView);
        
        list = sensMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
        
        if(list.size()>0)
        {
        	sensMgr.registerListener(sensEvLis,(Sensor) list.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
        	Toast.makeText(getBaseContext(), "EROR:NO ACCELEROMETER.", Toast.LENGTH_LONG).show();
        }
    }


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		if(list.size()>0)
		{
			sensMgr.unregisterListener(sensEvLis);
		}
	}
    
    
}