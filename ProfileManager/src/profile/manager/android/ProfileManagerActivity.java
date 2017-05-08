package profile.manager.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ProfileManagerActivity extends Activity {
    
	Button startServiceButton,stopServiceButton;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        startServiceButton = (Button) findViewById (R.id.startServiceButton);
        stopServiceButton = (Button) findViewById (R.id.stopServiceButton);
        
        
        startServiceButton.setOnClickListener( new OnClickListener ()
        {

			public void onClick(View arg0)
			{
				Intent i = new Intent (getBaseContext(),ProfileManager.class);
				//Toast.makeText(getApplicationContext(), "Service has Started",Toast.LENGTH_SHORT).show();
				startService(i);
			}
        	
        });
        
        stopServiceButton.setOnClickListener( new OnClickListener ()
        {

			public void onClick(View arg0)
			{
				Intent i = new Intent (getBaseContext(),ProfileManager.class);
				Toast.makeText(getApplicationContext(), "Service has Stopped",Toast.LENGTH_SHORT).show();
				stopService(i);
			}
        	
        });
    }
}