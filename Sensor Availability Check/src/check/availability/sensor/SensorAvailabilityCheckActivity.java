package check.availability.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.TextView;
import android.hardware.Sensor;
import java.util.List;

public class SensorAvailabilityCheckActivity extends Activity 
{
	TextView tv =null;
	private SensorManager sensMgr;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv=(TextView)findViewById(R.id.textView);
        tv.setVisibility(View.GONE);
        
        sensMgr=(SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor>list=sensMgr.getSensorList(Sensor.TYPE_ALL);
        
        for(int i=1;i<list.size();i++)
        {
        	tv.setVisibility(View.VISIBLE);
        	tv.append("\n"+list.get(i).getName()+"\n"+list.get(i).getVendor()+"\n"+list.get(i).getVersion());
        	
        }
    }
}