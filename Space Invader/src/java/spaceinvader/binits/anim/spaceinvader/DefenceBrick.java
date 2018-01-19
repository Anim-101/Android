package spaceinvader.binits.anim.spaceinvader;

import android.graphics.RectF;

/**
 * Created by Anim on 1/7/2018.
 */

public class DefenceBrick
{
    private RectF rect;
    private boolean isVisible;

    public DefenceBrick(int row,int column,int shelterNumber,int screenX,int screenY)
    {
        int width=screenX/90;
        int height=screenY/40;
        isVisible=true;
        int brickPadding=1;
        int shelterPadding=screenX/9;
        int startHeight=screenY-(screenY/8*2);

        rect=new RectF(column*width+brickPadding+
                (shelterPadding*shelterNumber)+
                shelterPadding+shelterNumber*shelterPadding,
                row*height+brickPadding+startHeight,
                column*width+width-brickPadding+
                        (shelterPadding*shelterNumber)+
                        shelterPadding+shelterPadding*shelterNumber,
                row*height+height-brickPadding+startHeight
        );
    }

    public RectF getRect()
    {
        return this.rect;
    }

    public void setInvisible()
    {
        isVisible=false;
    }

    public boolean getVisibility()
    {
        return isVisible;
    }
}
