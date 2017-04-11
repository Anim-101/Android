package Calculator.Demo.New;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

public class CalculatorGo extends Activity implements OnClickListener
{
	private ListView mytextList;
	private Button goBack;
	final Context context=this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go);
	      Intent intent = getIntent();
	    
				
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		this.goBack = (Button)findViewById (R.id.goBack);
		
		mytextList.setOnClickListener(this);
		goBack.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Button btn = (Button)v;
		
		if(btn==goBack)
		{
			//Intent intent = new Intent (context,CalculatorActivity.class);
			//startActivity(intent);
			
		}
		
	}
	

}
