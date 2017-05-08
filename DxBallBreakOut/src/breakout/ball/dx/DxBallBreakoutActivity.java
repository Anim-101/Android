package breakout.ball.dx;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DxBallBreakoutActivity extends Activity {
    /** Called when the activity is first created. */
    
	DxBallBreakOutGameView gameView;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        gameView = new DxBallBreakOutGameView (this);
        setContentView(gameView);
    }
	
	class DxBallBreakOutGameView extends SurfaceView implements Runnable
	{
		Thread gameThread =null;
		SurfaceHolder gameSurface;
		volatile boolean playStatus;
		boolean paused =true;
		Canvas canvas;
		Paint paint;
		long fps;
		private long timeFrame;
		int screenX;
		int screenY;
		Paddle paddle;
		Ball ball;
		Brick [] bricks = new Brick [200];
		int numBricks =0;
		//Initializing scores and and Lives at Game Start
		int score=0;
		int lives=3;
		public DxBallBreakOutGameView (Context context)
		{
			super (context);
			gameSurface= getHolder();
			paint = new Paint ();
			Display display = getWindowManager().getDefaultDisplay();
			Point takeSize = new Point ();
			
			takeSize.x=display.getWidth();
			takeSize.y=display.getHeight();
			
			screenX=takeSize.x;
			screenY=takeSize.y;
			
			paddle = new Paddle (screenX,screenY);
			ball = new Ball (screenX,screenY);
			
			restart ();
		}
		
		public void restart ()
		{
			ball.reset(screenX, screenY);
			paddle = new Paddle (screenX, screenY);
			
			int brickWidth = screenX/8;
			int brickHeight = screenY/10;
			
			numBricks=0;
			
			for(int column=0;column<8;column++)
			{
				for (int row=0;row<3;row++)
				{
					 bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
					 numBricks++;
				}
			}
			
			//Reseting Game if it's over
			if(lives==0)
			{
				score=0;
				lives=3;
			}
		}

		public void run() 
		{
			// TODO Auto-generated method stub
			while (playStatus)
			{
				long startTimeFrame = System.currentTimeMillis();
				
				if(!paused)
				{
					update();
				}
				
				draw();
				
				timeFrame = System.currentTimeMillis()-startTimeFrame;
				
				if(timeFrame>=1)
				{
					fps =1000/timeFrame;
				}
			}
			
		}

		public void update()
		{
			paddle.update(fps);
			ball.update(fps);
			
			//Checking the ball collision with a brick
			for (int i=0;i<numBricks;i++)
			{
				if (bricks[i].getVisible())
				{
					if(RectF.intersects(bricks[i].getRect(), ball.getRect()))
					{
						 bricks[i].setInvisible();
	                     ball.reverseYVelocity();
	                     score = score + 10;
					}
				}
			}
			
			//Checking the ball collision with Paddle
			if(RectF.intersects(paddle.getRect(),ball.getRect()))
			{
				ball.setRandomXVelocity();
				ball.reverseYVelocity();
				ball.clearObstacleY(paddle.getRect().top-2);
			}
			
			//Checking the ball collision with bottom of Screen and bounce back
			if (ball.getRect().bottom>screenY)
			{
				ball.reverseYVelocity();
				ball.clearObstacleY(screenY-2);
				
				lives--;
				
				if(lives==0)
				{
					paused=true;
					restart();
				}
			}
			
			//Checking the ball collision with top of Screen and bounce
			if(ball.getRect().top<0)
			{
				ball.reverseYVelocity();
				ball.clearObstacleY(12);
			}
			
			//Checking the ball collision with left wall and bounce 
			if (ball.getRect().left<0)
			{
				ball.reverseXVelocity();
				ball.clearObstacleX(2);
			}
			
			//Checking the ball collison with right wall and bounce
			if(ball.getRect().right>screenX-10)
			{
				ball.reverseXVelocity();
				ball.clearObstacleX(screenX-22);
			}
			
			if(score==numBricks*10)
			{
				paused=true;
				restart();
			}
		}
		
		private void draw()
		{
				if (gameSurface.getSurface().isValid())
				{
					canvas = gameSurface.lockCanvas();
					
					//Drawing Background Color
					canvas.drawColor(Color.argb(255, 26, 120, 102));
					
					// Choosing the brush color for drawing
	                paint.setColor(Color.argb(150, 0, 0, 0));
	                
	                // Drawing the paddle
	                canvas.drawRect(paddle.getRect(), paint);
	                
	                //Drawing the Ball
	               	canvas.drawOval(ball.getRect(), paint);               
	                
	                //Choosing the brush color for Drawing Bricks
	                
	                paint.setColor(Color.argb(189, 136, 222, 235));
	                
	                //Drawing Visible
	                for (int i=0;i<numBricks;i++)
	                {
	                	if(bricks[i].getVisible())
	                	{
	                		canvas.drawRect(bricks[i].getRect(), paint);
	                	}
	                }
	                
	                //Chossing the brus color for drawing
	                paint.setColor(Color.argb(255, 255, 255, 255));
	                
	                // Drawing the score
	                paint.setTextSize(40);
	                canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);
	 
	                // Player Screen clear check
	                if (score == numBricks * 10)
	                {
	                    paint.setTextSize(90);
	                    canvas.drawText("YOU HAVE WON!", screenX/4, screenY / 2, paint);
	                }

	                gameSurface.unlockCanvasAndPost(canvas);
				}
		}
		
		public void resumeGame ()
		{
			playStatus = true;
			gameThread = new Thread (this);
			gameThread.start();
		}
		public void pauseGame ()
		{
			playStatus =false;
			try
			{
				gameThread.join();
			}
			catch (InterruptedException e)
			{
				
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			switch(event.getAction() & MotionEvent.ACTION_MASK)
			
			{
				case MotionEvent.ACTION_DOWN:
					
					paused=false;
					if(event.getX()>screenX/2)
					{
						paddle.setMovementState(paddle.rightState);
					}
					else 
					{
						paddle.setMovementState(paddle.leftState);
					}
					break;
				case MotionEvent.ACTION_UP:
					
					paddle.setMovementState(paddle.stoppedState);
					break;
			}
			return true;
		}
		
		
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		gameView.resumeGame();
	}

	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		
		gameView.pauseGame();
	}	
}