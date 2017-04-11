package Calculator.Demo.New;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

public class CalculatorActivity extends Activity implements OnClickListener, OnLongClickListener {
	
	private TextView display;
	private Button btn_one;
	private Button btn_two;
	private Button btn_three;
	private Button btn_four;
	private Button btn_five;
	private Button btn_six;
	private Button btn_seven;
	private Button btn_eight;
	private Button btn_nine;
	private Button btn_zero;
	private Button btn_dot;
	private Button btn_equal;
	private Button btn_memClear;
	private Button btn_memRestore;
	private Button btn_memAdd;
	private Button btn_memRemove;
	private Button btn_plus;
	private Button btn_minus;
	private Button btn_multiply;
	private Button btn_divide;
	private Button btn_allClear;
	private Button btn_root;
	private Button btn_percent;
	private Button btn_delete;
	private Button btn_reCall;
	private Button btn_square;
	private Button btn_cube;
	private Button btn_power;
	private Button btn_go;
	
	private int i=0,opVal=0;
	private long sq;
	private String text,re,pow,resultString,op,str;
	private Double res=0.0,sharedVal=0.0;
	private DbHelper myDb;
	private String file ="myData";
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String VALUE = "valKey";
	
	SharedPreferences sharedpreferences;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.myDb = new DbHelper (this);  
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        
        this.str="0.0";
		SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(VALUE, str);
        editor.commit();
        this.str=null;
       
    }
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//Setting Id of Buttons and TextViews
		this.display=(TextView)findViewById(R.id.display);
		this.btn_one=(Button)findViewById(R.id.btn_one);
		this.btn_two=(Button)findViewById(R.id.btn_two);
		this.btn_three=(Button)findViewById(R.id.btn_three);
		this.btn_four=(Button)findViewById(R.id.btn_four);
		this.btn_five=(Button)findViewById(R.id.btn_five);
		this.btn_six=(Button)findViewById(R.id.btn_six);
		this.btn_seven=(Button)findViewById(R.id.btn_seven);
		this.btn_eight=(Button)findViewById(R.id.btn_eight);
		this.btn_nine=(Button)findViewById(R.id.btn_nine);
		this.btn_zero=(Button)findViewById(R.id.btn_zero);
		this.btn_dot=(Button)findViewById(R.id.btn_dot);
		this.btn_equal=(Button)findViewById(R.id.btn_equal);
		this.btn_plus=(Button)findViewById(R.id.btn_plus);
		this.btn_minus=(Button)findViewById(R.id.btn_minus);
		this.btn_multiply=(Button)findViewById(R.id.btn_multiply);
		this.btn_divide=(Button)findViewById(R.id.btn_divide);
		this.btn_memClear=(Button)findViewById(R.id.btn_memClear);
		this.btn_memAdd=(Button)findViewById(R.id.btn_memAdd);
		this.btn_memRemove=(Button)findViewById(R.id.btn_memRemove);
		this.btn_memRestore=(Button)findViewById(R.id.btn_memRestore);
		this.btn_allClear=(Button)findViewById(R.id.btn_allClear);
		this.btn_root=(Button)findViewById(R.id.btn_root);
		this.btn_percent=(Button)findViewById(R.id.btn_percent);
		this.btn_delete=(Button)findViewById(R.id.btn_delete);
		this.btn_reCall=(Button)findViewById(R.id.btn_reCall);
		this.btn_square=(Button)findViewById(R.id.btn_square);
		this.btn_cube=(Button)findViewById(R.id.btn_cube);
		this.btn_power=(Button)findViewById(R.id.btn_power);
		this.btn_go=(Button)findViewById(R.id.btn_go);
		
		//Setting Listeners for Buttons and TextViews
		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_five.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_eight.setOnClickListener(this);
		btn_nine.setOnClickListener(this);
		btn_zero.setOnClickListener(this);
		btn_dot.setOnClickListener(this);
		btn_equal.setOnClickListener(this);
		btn_plus.setOnClickListener(this);
		btn_minus.setOnClickListener(this);
		btn_multiply.setOnClickListener(this);
		btn_divide.setOnClickListener(this);
		btn_memClear.setOnClickListener(this);
		btn_memAdd.setOnClickListener(this);
		btn_memRemove.setOnClickListener(this);
		btn_memRestore.setOnClickListener(this);
		btn_allClear.setOnClickListener(this);
		btn_root.setOnClickListener(this);
		btn_percent.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		btn_reCall.setOnClickListener(this);
		btn_square.setOnClickListener(this);
		btn_cube.setOnClickListener(this);
		btn_power.setOnClickListener(this);
		btn_go.setOnClickListener(this);	
		
		btn_delete.setOnLongClickListener(this);
		
		
    }

	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
		Button btn = (Button)v;
		if(btn==btn_one)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_two)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_three)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_four)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_five)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_six)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_seven)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_eight)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_nine)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_zero)
		{
			this.display.setText (this.display.getText().toString()+btn.getText().toString());
		}
		else if (btn==btn_dot)
		{
			if (this.i==0)
			{
				this.display.setText (this.display.getText().toString()+btn.getText().toString());
				this.i++;
			}
		}
		else if (btn==btn_plus)
		{
			if(this.opVal==0)
			{
				this.display.setText (this.display.getText().toString()+btn.getText().toString());
				this.opVal=1;
			}
		}
		else if (btn==btn_minus)
		{
			if(this.opVal==0)
			{
				this.display.setText (this.display.getText().toString()+btn.getText().toString());
				this.opVal=1;
			}
		}
		else if (btn==btn_equal)
		{
			this.str =this.display.getText().toString();
			this.re=str;
			String [] result;
			String lastOne=null;
			int val=0;
			if(str!=null && str.equals("")!=true)
				
			{
				if (str.charAt(str.length()-1)!='^' && str.charAt(str.length()-1)!='-' && str.charAt(str.length()-1)!='+' && str.charAt(str.length()-1)!='*' && str.charAt(str.length()-1)!='/')
					{
						this.resultString=str;
						if(str.contains("^"))
						{
							result = str.split("^");
							lastOne = result[result.length-1];
							val=Integer.parseInt(lastOne);
						}
						else 
						{
							result = str.split("(?<=[^\\d.])(?=\\d)|(?<=\\d)(?=[^\\d.])");
						}
							
						this.op=null;
						this.res=0.0;
						for (String s: result)
						{
							if(s.equals("+") || s.equals ("-") || s.equals ("*") || s.equals ("/") || s.equals("^"))
							{
								this.op=s;
								Toast.makeText(getApplicationContext(), ""+op+"", Toast.LENGTH_SHORT).show();
							}
							else 
							{
								if(this.op==null)
								{
									this.res=Double.parseDouble(s);
								}
								else
								{
									if (this.op.equals("+"))
									{
										this.res=res+Double.parseDouble(s);
									}
									else if (this.op.equals("-"))
									{
										this.res=res-Double.parseDouble(s);
									}
									if (this.op.equals("*"))
									{
										this.res=res*Double.parseDouble(s);
									}
									if (this.op.equals("/"))
									{
										this.res=res/Double.parseDouble(s);
									}
									if (this.op.equals("^"))
									{
										
										for (int i=1;i<=val;i++)
											{
												this.res=res*res;
											}
										this.display.setText(String.valueOf(this.res));
										break;
									}
								}
							}
						}
						this.display.setText (String.format("%.2f",this.res));
						this.resultString=resultString;
						try
						{	boolean bool;
							bool=this.myDb.insertResult(resultString);
							/*FileOutputStream fout = openFileOutput(file,MODE_WORLD_READABLE);
							fout.write(resultString.getBytes());
							fout.close();*/
							if(bool==true)
							{
								Toast.makeText(getApplicationContext(), "File Saved", Toast.LENGTH_SHORT).show();
							}
							else 
							{
								Toast.makeText(getApplicationContext(), "File hasn't Saved", Toast.LENGTH_SHORT).show();								
							}
						}
						catch (Exception e)
						{
							Toast.makeText(getApplicationContext(), ""+e+"", Toast.LENGTH_SHORT).show();
						}
						Toast.makeText(getApplicationContext(), ""+res+"", Toast.LENGTH_SHORT).show();
						this.text=this.display.getText().toString();
						this.opVal=0;
					}
				
				else if (str.indexOf("√")>-1)
				{
					String value=str.substring(1);
					double d = Double.parseDouble(value);
					double root = Math.sqrt(d);
					double absolute = Math.abs(root);
					this.display.setText(String.valueOf(absolute));
					
				}
					else
					{
						this.display.setText("Syntax Error");
					}	
			}
			
			else 
			{
				Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
				this.text=this.display.getText().toString();
			}
					
		}
		else if (btn==btn_multiply)
		{
			if(this.opVal==0)
			{
				this.display.setText (this.display.getText().toString()+btn.getText().toString());
				this.opVal=1;
			}
		}
		else if (btn==btn_divide)
		{
			if(this.opVal==0)
			{
				this.display.setText (this.display.getText().toString()+btn.getText().toString());
				this.opVal=1;
			}
		}
		else if (btn==btn_memClear)
		{
			SharedPreferences preferences = getSharedPreferences("Myprefs", 0);
			preferences.edit().remove(VALUE).commit();
			
			this.str="0.0";
			this.sharedVal=0.0;
			
			this.sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
			SharedPreferences.Editor editor = this.sharedpreferences.edit();

            editor.putString(VALUE, str);
            editor.commit();
            this.str=null;
		}
		else if (btn==btn_memRemove)
		{
       this.str=this.display.getText().toString();
			
			if (str!=null && str.contains("")!=true)
			{
				if (str.contains("+")!=true && str.contains("-")!=true && str.contains("/")!=true && str.contains("*")!=true && str.contains("^")!=true)
				{
					double data=Double.parseDouble(str);
					
					SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPrefs", 0);
				    settings = getApplicationContext().getSharedPreferences("MyPrefs", 0);
				    String datas = settings.getString(VALUE, "");
					
					this.sharedVal=data-Double.parseDouble(datas);
					this.str=String.valueOf(sharedVal);
					
					SharedPreferences.Editor editor1 = sharedpreferences.edit();
		            editor1.putString(VALUE, str);
		            editor1.commit();
		            Toast.makeText(getApplicationContext(), "Poped", Toast.LENGTH_SHORT).show();
		            
				}
				else 
				{
					Toast.makeText(getApplicationContext(), "Can't Remove", Toast.LENGTH_SHORT).show();
					this.text=this.display.getText().toString();					
				}
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "Can't Remove", Toast.LENGTH_SHORT).show();
			}
		}
		else if (btn==btn_memAdd)
		{
				this.str=this.display.getText().toString();
			
			if (str!=null && str.contains("")!=true)
			{
				if (str.contains("+")!=true && str.contains("-")!=true && str.contains("/")!=true && str.contains("*")!=true && str.contains("^")!=true)
				{
					double data=Double.parseDouble(str);
					
					SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPrefs", 0);
				    settings = getApplicationContext().getSharedPreferences("MyPrefs", 0);
				    String datas = settings.getString(VALUE, "");
					
					this.sharedVal=data+Double.parseDouble(datas);
					this.str=String.valueOf(sharedVal);
					
					SharedPreferences.Editor editor1 = sharedpreferences.edit();
		            editor1.putString(VALUE, str);
		            editor1.commit();
		            Toast.makeText(getApplicationContext(), "Pushed", Toast.LENGTH_SHORT).show();
		            
				}
				else 
				{
					Toast.makeText(getApplicationContext(), "Can't Add", Toast.LENGTH_SHORT).show();
					this.text=this.display.getText().toString();					
				}
			}
			else 
			{
				Toast.makeText(getApplicationContext(), "Can't Add", Toast.LENGTH_SHORT).show();
			}
		}		
		else if (btn==btn_memRestore)
		{
			
			SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE); 
		    Editor editor = pref.edit();
			String data = pref.getString(VALUE, "");
			editor.commit();
			this.display.setText(data);
			
		}
		else if (btn==btn_allClear)
		{
			this.i=0;
			this.re=this.display.getText().toString();
			this.display.setText ("");
			this.opVal=0;
		}
		else if (btn==btn_root)
		{
			this.str=this.display.getText().toString();
			if(this.str.equals("") || this.str==null )
			{
				if(opVal==0)
				{
					this.display.setText("√");
					this.opVal=1;
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Clear Screen to Perform", Toast.LENGTH_SHORT).show();
			}
			
		}
		else if (btn==btn_percent)
		{
		}
		else if (btn==btn_delete)
		{
			this.str=this.display.getText().toString();
			if(str!=null && str.equals("")!=true)
			{
					this.str=str.substring(0,str.length()-1);
					this.display.setText(str);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Nothing to Delete", Toast.LENGTH_SHORT).show();
				this.text=this.display.getText().toString();
			}
		}
		else if (btn==btn_reCall)
		{
			if(this.re!=null)
			{
				this.display.setText(this.re);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Nothing to ReCall", Toast.LENGTH_SHORT).show();
				this.text=this.display.getText().toString();
			}
		}
		else if (btn==btn_square)
		{
			if (this.opVal==0)
			{
				if (this.display.getText().toString() !=null)
				{	
					this.sq=Long.parseLong(this.display.getText().toString());
					this.sq=sq*sq;
					this.display.setText(Long.toString(this.sq));
				}
				this.opVal=1;
			}
			
		}
		else if (btn==btn_cube)
		{
			if (this.opVal==0)
			{
				if (this.display.getText().toString()!=null)
				{	
					this.sq=Long.parseLong(this.display.getText().toString());
					this.sq=sq*sq*sq;
					this.display.setText(Long.toString(this.sq));
				}
				this.opVal=1;
			}
		}
		else if (btn==btn_power)
		{
			this.str=this.display.getText().toString();
			if (this.opVal==0)
			{
				if(str.charAt(str.length()-1)!='^')
				{
					this.display.setText (this.display.getText().toString()+btn.getText().toString());
					this.opVal=1;
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Can't", Toast.LENGTH_SHORT).show();
					this.text=this.display.getText().toString();
				}
			
		}
		//To view Stored data From DataBase
		else if (btn==btn_go)
		{
			Intent i = new Intent (CalculatorActivity.this,CalculatorGo.class);
			CalculatorActivity.this.startActivity(i);
		}
	}
 }

	public boolean onLongClick(View v)
	{
		    this.display.setText("");
			return true;
		
	}
	
	
}  
