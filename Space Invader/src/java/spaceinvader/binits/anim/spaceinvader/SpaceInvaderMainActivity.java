package spaceinvader.binits.anim.spaceinvader;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class SpaceInvaderMainActivity extends Activity
{
    SpaceInvaderView spaceInvaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        spaceInvaderView = new SpaceInvaderView(this,size.x,size.y);
        setContentView(spaceInvaderView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        spaceInvaderView.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        spaceInvaderView.pause();
    }
}
