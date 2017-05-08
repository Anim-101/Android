package profile.manager.android;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

public class ProfileManager extends Service implements SensorEventListener
{
	private double lightVal;
	private String localTime;
	
	private double valueX;
	private double valueY;
	private double valueZ;
	
	AudioManager audioManager;
	SensorManager sensorManager;
	
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{}
	

	@Override
	public IBinder onBind(Intent arg0) 
	{return null;}

	@Override
	public void onCreate()
	{
		super.onCreate();
		
		lightVal=0.0;
		audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		if(lightSensor != null)
		{
			sensorManager.registerListener(this, lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if(accelerometer != null)
		{
			sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+6:00"));
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date = new SimpleDateFormat("HH:mm a");
		date.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));
		localTime = date.format(currentLocalTime);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		
		//Toast.makeText(this, "Service has Started", Toast.LENGTH_LONG).show();
		return START_STICKY;
	}

	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		sensorManager.unregisterListener(this);
		Toast.makeText(this, "Service has Destroyed", Toast.LENGTH_LONG).show();
	}

	public void onSensorChanged (SensorEvent event)
	{
		if(event.sensor.getType()==Sensor.TYPE_LIGHT)
		{
			lightVal = event.values[0];
			if(lightVal<8.0)
			{
				Toast.makeText(this, localTime, Toast.LENGTH_LONG).show();
				setVibrate();
			}
			else if (lightVal>=8.0)
			{
				setRinging();
			}
		}
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			valueX=event.values[0];
			valueY=event.values[1];
			valueZ=event.values[2];
			
			if((valueX <=2.0 && valueX >=-1.5) && (valueY>=0.5 && valueY <=2.5) && (valueZ>=9.1) )	
			{				
				setRinging();
			}
			else if ((valueX <=-3.0 && valueX >=3.0) && (valueY>=0.5 && valueY <=1.8) && (valueZ<=-7.5 && valueZ >=-9.0) )
			{
				setVibrate();
			}
			else if (valueZ<=5.0)
			{
				setSilent();
			}
		}
	}
	
	public void setVibrate ()
	{
		audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		SystemClock.sleep(30);
	}
	
	public void setRinging ()
	{
		audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		SystemClock.sleep(30);
	}
	
	public void setSilent()
	{
		audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		SystemClock.sleep(30);
	}
}
	

