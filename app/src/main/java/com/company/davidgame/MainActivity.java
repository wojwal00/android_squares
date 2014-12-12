package com.company.davidgame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends Activity {

    private int gameType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        gameType = getIntent().getIntExtra("gameType", 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public Activity activity;

        public PlaceholderFragment() {
        }

        /**
         * Called to do initial creation of a fragment.  This is called after
         * {@link #onAttach(android.app.Activity)} and before
         * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
         * <p/>
         * <p>Note that this can be called while the fragment's activity is
         * still in the process of being created.  As such, you can not rely
         * on things like the activity's content view hierarchy being initialized
         * at this point.  If you want to do work once the activity itself is
         * created, see {@link #onActivityCreated(android.os.Bundle)}.
         *
         * @param savedInstanceState If the fragment is being re-created from
         *                           a previous saved state, this is the state.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View _view = inflater.inflate(R.layout.layout_drawing_fragment,
                    container, false);
            //lets keep a reference of DrawView
            // drawView = (DrawView ) _view.findViewById(R.id.drawing);

            return _view;

          //  View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //return rootView;
        }

        /**
         * Called when the fragment's activity has been created and this
         * fragment's view hierarchy instantiated.  It can be used to do final
         * initialization once these pieces are in place, such as retrieving
         * views or restoring state.  It is also useful for fragments that use
         * {@link #setRetainInstance(boolean)} to retain their instance,
         * as this callback tells the fragment when it is fully associated with
         * the new activity instance.  This is called after {@link #onCreateView}
         * and before {@link #onViewStateRestored(android.os.Bundle)}.
         *
         * @param savedInstanceState If the fragment is being re-created from
         *                           a previous saved state, this is the state.
         */
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        /**
         * Called when the Fragment is visible to the user.  This is generally
         * tied to {@link android.app.Activity#onStart() Activity.onStart} of the containing
         * Activity's lifecycle.
         */
        @Override
        public void onStart() {
            super.onStart();
            /*View _view = getView();
            DrawView drawView = (DrawView) _view.findViewById(R.id.drawing);
            drawView.init();*/
        }

        /**
         * Called when a fragment is first attached to its activity.
         * {@link #onCreate(android.os.Bundle)} will be called after this.
         *
         * @param activity
         */
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.activity = activity;
        }
    }
}
