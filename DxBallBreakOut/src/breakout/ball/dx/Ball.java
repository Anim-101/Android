package breakout.ball.dx;

import java.util.Random;
import android.graphics.RectF;

public class Ball 
{
	RectF rect;
	float xVelocity;
	float yVelocity;
	float ballWidth=10;
	float ballHeight=10;
	
	public Ball (int screenX,int screenY)
	{
		xVelocity=200;
		yVelocity=-400;
		
		rect = new RectF();
	}
	
	public RectF getRect ()
	{
		return rect;
	}
	public void update (long fps)
	{
		rect.left = rect.left + (xVelocity/fps);
		rect.top = rect.top + (yVelocity/fps);
		rect.right=rect.left +ballWidth;
		rect.bottom = rect.top-ballHeight;
	}
	
	public void reverseYVelocity ()
	{
		yVelocity = -yVelocity;
	}
	public void reverseXVelocity ()
	{
		xVelocity = -xVelocity;
	}
	
	public void setRandomXVelocity ()
	{
		Random generator = new Random ();
		int randomVal = generator.nextInt(2);
		if(randomVal==0)
		{
			reverseXVelocity();
		}
	}
	
	public void clearObstacleY (float y)
	{
		rect.bottom=y;
		rect.top=y-ballHeight;
	}
	
	public void clearObstacleX (float x)
	{
		rect.left =x;
		rect.right = x+ballWidth;
	}
	
	public void reset (int screenX,int screenY)
	{
		rect.left = screenX/2;
		rect.top =screenY-20;
		rect.right = screenX/2+ballWidth;
		rect.bottom = screenY -20-ballHeight;
	}
}
