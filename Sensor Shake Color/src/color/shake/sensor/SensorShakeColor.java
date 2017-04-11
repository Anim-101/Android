package color.shake.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

public class SensorShakeColor extends Activity implements SensorEventListener{
    /** Called when the activity is first created. */
   
	private SensorManager sensMgr;
	private boolean isColor = false;
	private View view;
	private long lastUpdate;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        view=findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);
        
        sensMgr=(SensorManager)getSystemService(SENSOR_SERVICE);
        lastUpdate=System.currentTimeMillis();
    }
	
	public void onAccuracyChanged(Sensor sensor,int accuracy)
	{
		
	}
	
	public void onSensorChanged(SensorEvent event)
	{
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			getAccelerometer(event);
		}
	}
	
	private void getAccelerometer(SensorEvent event)
	{
		float [] values = event.values;
		
		float x=values[0];
		float y=values[1];
		float z=values[2];
		
		float accelationSquareRoot = (x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
		
		long actualTime = System.currentTimeMillis();
		
		Toast.makeText(getApplicationContext(),String.valueOf(accelationSquareRoot)+SensorManager.GRAVITY_EARTH,Toast.LENGTH_SHORT).show();
		
		if(accelationSquareRoot>=2)
		{
			
			if(actualTime-lastUpdate<200)
			{
				return;
			}
			
			lastUpdate=actualTime;
			if(isColor)
			{
				view.setBackgroundColor(Color.GREEN);
			}
			else
			{
				view.setBackgroundColor(Color.RED);
			}
			
			isColor=!isColor;
		}
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		sensMgr.registerListener(this,sensMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void onPause ()
	{
		super.onPause();
		sensMgr.unregisterListener(this);
	}
}