package spaceinvader.binits.anim.spaceinvader;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

/**
 * Created by Anim on 1/7/2018.
 */

public class SpaceInvaderView extends SurfaceView implements Runnable
{
    Context context;
    private Thread gameThread = null;
    private SurfaceHolder gameHolder;
    private volatile boolean playStatus;
    private boolean paused = true;
    private Canvas canvas;
    private Paint paint;
    private long fps;
    private long timeFrame;
    private int screenX;
    private int screenY;
    private PlayerShip playerShip;
    private Bullet bullet;
    private Bullet [] invaderBullets = new Bullet[200];
    private int nextBullet;
    private int maxInvaderBullets = 10;
    Invader [] invaders = new Invader[60];
    int numInvader = 0;
    private DefenceBrick[] bricks = new DefenceBrick[400];
    private int numBricks;

    private SoundPool soundPool;
    private int playerExplodeID = -1;
    private int invaderExplodeID = -1;
    private int shootID = -1;
    private int damageShelterID = -1;
    private int pepID = -1;
    private int popID = -1;

    int score = 0;
    private int lives = 3;
    private long gameOverInterval = 1000;
    private boolean peppop;
    private long lastGameOverTime = System.currentTimeMillis();

    public SpaceInvaderView(Context context,int x,int y)
    {
        super(context);
        this.context=context;
        gameHolder=getHolder();
        paint=new Paint();
        screenX=x;
        screenY=y;

        soundPool=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try
        {
            AssetManager assetManager=context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor=assetManager.openFd("playerExplode.ogg");
            playerExplodeID=soundPool.load(descriptor,0);

            descriptor=assetManager.openFd("invaderExplode.ogg");
            invaderExplodeID=soundPool.load(descriptor,0);

            descriptor=assetManager.openFd("damageShelter.ogg");
            damageShelterID=soundPool.load(descriptor,0);

            descriptor=assetManager.openFd("pep.ogg");
            pepID=soundPool.load(descriptor,0);

            descriptor=assetManager.openFd("pop.ogg");
            popID=soundPool.load(descriptor,0);

            descriptor=assetManager.openFd("shoot.ogg");
            shootID=soundPool.load(descriptor,0);


        } catch (IOException e) {
            Log.e("Error","Failed to Load Sound Files");
        }

        prepareGameLevel();
    }

    private void prepareGameLevel()
    {
        gameOverInterval=1000;
        playerShip=new PlayerShip(context,screenX,screenY);
        playerShip.update(fps);
        canvas.drawBitmap(playerShip.getBitmap(),playerShip.getX(),screenY-50,paint);

        numInvader=0;
        for(int column=0;column<6;column++)
        {
            for(int row=0;row<5;row++)
            {
                invaders[numInvader]=new Invader(context,row,column,screenX,screenY);
                numInvader++;
            }
        }

        numBricks=0;
        for(int shelterNumber=0;shelterNumber<4;shelterNumber++)
        {
            for(int column=0;column<10;column++)
            {
                for(int row=0;row<5;row++)
                {
                    bricks[numBricks]=new DefenceBrick(row,column,shelterNumber,screenX,screenY);
                    numBricks++;
                }
            }
        }

        bullet=new Bullet(screenY);
        for(int i=0;i<invaderBullets.length;i++)
        {
            invaderBullets[i]=new Bullet(screenY);
        }

        if(bullet.getStatus())
        {
            bullet.update(fps);
        }

        for(int i=0;i<invaderBullets.length;i++)
        {
            if(invaderBullets[i].getStatus())
            {
                invaderBullets[i].update(fps);
            }
        }

        if(bullet.getStatus())
        {
            canvas.drawRect(bullet.getRect(),paint);
        }

        for(int i=0;i<invaderBullets.length;i++)
        {
            if(invaderBullets[i].getStatus())
            {
                canvas.drawRect(invaderBullets[i].getRect(),paint);
            }
        }
    }

    @Override
    public void run()
    {
        long starttimeFrame;
        while(playStatus)
        {
            starttimeFrame=System.currentTimeMillis();

            if(!paused)
            {
                update();
            }

            draw();

            timeFrame=System.currentTimeMillis()-starttimeFrame;
            if(timeFrame>=1)
            {
                fps=1000/timeFrame;
            }

            if(!paused)
            {
                if((starttimeFrame-lastGameOverTime)>gameOverInterval)
                {
                    if(peppop)
                    {
                        soundPool.play(pepID,1,1,0,0,1);
                    }
                    else
                    {
                        soundPool.play(popID,1,1,0,0,1);
                    }
                    lastGameOverTime=System.currentTimeMillis();
                    peppop=!peppop;
                }
            }
        }


    }

    private void update()
    {
        boolean bumped=false;
        boolean lost=false;
        if(lost)
        {
            prepareGameLevel();
        }

        for(int i=0;i<numInvader;i++)
        {
            if(invaders[i].getVisbility())
            {
                invaders[i].update(fps);
                if(invaders[i].takeAim(playerShip.getX(),playerShip.getLength()))
                {
                    if(invaderBullets[nextBullet].shoot(invaders[i].getX()+
                            invaders[i].getLength()/2,invaders[i].getY(),bullet.DOWN))
                    {
                        nextBullet++;
                        if(nextBullet==maxInvaderBullets)
                        {
                            nextBullet=0;
                        }
                    }
                }
                if((invaders[i].getX()>screenY-invaders[i].getLength())||invaders[i].getX()<0)
                {
                    bumped=true;
                }
            }
        }

        if(bumped)
        {
            for(int i=0;i<numInvader;i++)
            {
                invaders[i].dropDownAndReverse();
                if(invaders[i].getY()>screenY-screenX/10)
                {
                    lost=true;
                }
            }
        }

        if(bullet.getImpactPointY()<0)
        {
            bullet.setInactive();
        }

        for(int i=0;i<invaderBullets.length;i++)
        {
            if(invaderBullets[i].getImpactPointY()>screenY)
            {
                invaderBullets[i].setInactive();
            }
        }

        if(bullet.getStatus())
        {
            for(int i=0;i<numInvader;i++)
            {
                if(invaders[i].getVisbility())
                {
                    if(RectF.intersects(bullet.getRect(),invaders[i].getRect()))
                    {
                        invaders[i].setInvisible();
                        soundPool.play(invaderExplodeID,1,1,0,0,1);
                        bullet.setInactive();
                        score=score+10;

                        if(score==numInvader*10)
                        {
                            paused=true;
                            score=0;
                            lives=3;
                            prepareGameLevel();
                        }
                    }
                }
            }
        }

        for(int i=0;i<invaderBullets.length;i++)
        {
            if(invaderBullets[i].getStatus())
            {
                for(int j=0;j<numBricks;j++)
                {
                    if(bricks[j].getVisibility())
                    {
                        if(RectF.intersects(invaderBullets[i].getRect(),bricks[j].getRect()))
                        {
                            invaderBullets[i].setInactive();
                            bricks[j].setInvisible();
                            soundPool.play(damageShelterID,1,1,0,0,1);
                        }
                    }
                }
            }
        }

        if(bullet.getStatus())
        {
            for(int i=0;i<numBricks;i++)
            {
                if(bricks[i].getVisibility())
                {
                    if(RectF.intersects(bullet.getRect(),bricks[i].getRect()))
                    {
                        bullet.setInactive();;
                        bricks[i].setInvisible();
                        soundPool.play(damageShelterID,1,1,0,0,1);
                    }
                }
            }
        }

        for(int i=0;i<invaderBullets.length;i++)
        {
            if(invaderBullets[i].getStatus())
            {
                if(RectF.intersects(playerShip.getReact(),invaderBullets[i].getRect()))
                {
                    invaderBullets[i].setInactive();
                    lives--;
                    soundPool.play(playerExplodeID,1,1,0,0,1);

                    if(lives==0)
                    {
                        paused=true;
                        lives=3;
                        score=0;
                        prepareGameLevel();
                    }
                }
            }
        }

    }

    private void draw()
    {
        if(gameHolder.getSurface().isValid())
        {
            canvas=gameHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,26,128,182));
            paint.setColor(Color.argb(255,255,255,255));

            for(int i=0;i<numInvader;i++)
            {
                if(invaders[i].getVisbility())
                {
                    if(peppop)
                    {
                        canvas.drawBitmap(invaders[i].getBitmap1(),invaders[i].getX(),invaders[i].getY(),paint);
                        canvas.drawBitmap(invaders[i].getBitmap2(),invaders[i].getX(),invaders[i].getY(),paint);

                    }
                }
            }

            for(int i=0;i<numBricks;i++)
            {
                if(bricks[i].getVisibility())
                {
                    canvas.drawRect(bricks[i].getRect(),paint);
                }
            }

            paint.setColor(Color.argb(255,249,129,0));
            paint.setTextSize(40);
            canvas.drawText("Score: "+score+" Lives: "+lives,10,50,paint);

            gameHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause()
    {
        playStatus=false;
        try
        {
            gameThread.join();
        }
        catch (InterruptedException e) {
            Log.e("Error", "Joining Game");
        }
    }

    public void resume()
    {
        playStatus=true;
        gameThread=new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                paused=false;
                if(motionEvent.getY()>screenY-screenY/8)
                {
                    playerShip.setMovementState(playerShip.RIGHT);
                }
                else
                {
                    playerShip.setMovementState(playerShip.LEFT);
                }

                if(motionEvent.getY()<screenY-screenY/8)
                {
                    if(bullet.shoot(playerShip.getX()+playerShip.getLength()/2,screenY,bullet.UP))
                    {
                        soundPool.play(shootID,1,1,0,0,1);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(motionEvent.getY()>screenY-screenX/10)
                {
                    playerShip.setMovementState(playerShip.STOPPED);
                }
                break;
        }
        return true;
    }
}