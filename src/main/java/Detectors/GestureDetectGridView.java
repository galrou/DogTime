package Detectors;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import com.example.gal.dogtime.DisplayPuzzleActivity;

/**
 * Created by gal on 17/05/2018.
 */
//created my own view,here i handle all the touches using gesuredetector
public class GestureDetectGridView  extends GridView {

    private GestureDetector gDetector;//decalring an instance
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;

    private static final int SWIPE_MIN_DISTANCE = 100;//the min distance for a motion event be considered as a swipe.
    private static final int SWIPE_MAX_OFF_PATH = 100;//used to force to consider straight line when user make a swipe.
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;// is the velocity threshold for a swipe be considered.

    //those 4 constructor android made because i extended gridview i just addded the call to init function

    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        //simpleGestureListener is a A convenience class to extend when you only want to listen for a subset of all
        // the gestures. This implements all methods in the GestureDetector.OnGestureListener,
        // GestureDetector.OnDoubleTapListener, and GestureDetector.OnContextClickListener
        System.out.print("kkk");
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            /**
             * The onDown event return false by default, and every gesture start with onDown so
             * my onFling event will not fire.
             *
             * all gestures begin with an onDown() message.
             * If i return false from onDown(), as GestureDetector.SimpleOnGestureListener does,
             * the system assumes that you want to ignore the rest of the gesture,
             * and the other methods of GestureDetector.OnGestureListener never get
             * called
             * @param event
             * @return
             */
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                System.out.println("kkk");
                final int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(e1.getX()), Math.round(e1.getY()));//the position of the tile

                //here i handle the vertical gestures(up,down)
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {

                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    //if the distnace is greater than the min distance move up
                    if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                        DisplayPuzzleActivity.moveTiles(context, DisplayPuzzleActivity.up, position);
                        // Toast.makeText(context,"UP",Toast.LENGTH_SHORT).show();


                    }
                    //else if move down
                    else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                        DisplayPuzzleActivity.moveTiles(context, DisplayPuzzleActivity.down, position);
                        // Toast.makeText(context,"DOWN",Toast.LENGTH_SHORT).show();
                    }
                }
                //here i handle the horzental getstures(right,left)
                else {
                    //if the velocity of the gesture is too slow,dont move any tile
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    //if the motion is above the min distance then move left
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                        DisplayPuzzleActivity.moveTiles(context, DisplayPuzzleActivity.left, position);
                        // Toast.makeText(context,"LEFT",Toast.LENGTH_SHORT).show();
                    }
                    //else move right
                    else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                        DisplayPuzzleActivity.moveTiles(context, DisplayPuzzleActivity.right, position);
                        //Toast.makeText(context,"RIGHT",Toast.LENGTH_SHORT).show();
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     *Handling touch events in a ViewGroup takes special care, because it's common
     * for a ViewGroup to have children that are targets for different touch events
     * than the ViewGroup itself. To make sure that each view correctly receives
     * the touch events intended for it, i override the onInterceptTouchEvent() method.
     * Intercept touch events before passing to children
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();// extract the action event without the pointer index
        gDetector.onTouchEvent(ev);//returns true or false

       //if the pressed gesture has finished or the current gesture has been aborted
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;

        }
        //if a pressed gesture has started
        else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        }

        else {
            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);//passing event to here
    }
}