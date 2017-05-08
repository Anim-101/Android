package breakout.ball.dx;

import android.graphics.RectF;

public class Paddle 
{
	private RectF rect;
	
	//Paddle's Length and Height Measurement
	private float length;
	private float height;
	
	//X to hold value of how much width will paddle take due the start of the game
	//Y to hold the value of how height will paddle take due the start of the game
	private float x,y;
	
	private float paddleSpeed;
	
	//Paddle movement State;
	
	public final int stoppedState =0;
	public final int leftState =1;
	public final int rightState =2;
	
	//Intializing paddle's Intial Movement State
	private int paddleMovement = stoppedState;
	
	public Paddle (int screenX,int screenY)
	{
		//130px wide and 20px high
		length = 130;
		height = 20;
		
		x=screenX/2-65;
		y=screenY-20;
		
		rect = new RectF(x,y,x+length,y+height);
		
		paddleSpeed = 350;
	}
	
	public RectF getRect ()
	{
		return rect;
	}
	
	public void setMovementState (int state)
	{
		paddleMovement = state;
	}
	
	public void update (long fps)
	{
		if(paddleMovement==leftState)
		{
			x=x-paddleSpeed/fps;
		}
		if (paddleMovement==rightState)
		{
			x=x+paddleSpeed/fps;
		}
		rect.left=x;
		rect.right=x+length;
	}
}
