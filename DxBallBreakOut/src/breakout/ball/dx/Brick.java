package breakout.ball.dx;
import android.graphics.RectF;

public class Brick
{
	private RectF rect;
	private boolean visible;
	
	public Brick (int row,int column,int width,int height)
	{
		visible =true;
		int padding=1;
		rect = new RectF (column*width+padding,
				row*height+padding,
				column*width+width-padding,
				row*height+height-padding);				
	}
	
	public RectF getRect ()
	{
		return this.rect;
	}
	
	public void setInvisible ()
	{
		visible =false;
	}
	public boolean getVisible ()
	{
		return visible;
	}
}
