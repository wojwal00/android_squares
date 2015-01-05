package com.company.davidgame.squaregame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.company.davidgame.R;

public class SquareGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //do sth
            }
        });*/

        setContentView(R.layout.activity_square_game);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.square_game, menu);
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
    @Override
    protected void onDestroy() {
        Log.d("SquareGameActivity", "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("SquareGameActivity", "Stopping...");
        super.onStop();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_square_game, container, false);
           // MainGamePanel mainGamePanel = new MainGamePanel(getActivity());
           // mainGamePanel.getHolder().setFixedSize(500,500);
           //return mainGamePanel;
           //return new MainGamePanel(getActivity());
            return rootView;
        }

        /**
         * Called to ask the fragment to save its current dynamic state, so it
         * can later be reconstructed in a new instance of its process is
         * restarted.  If a new instance of the fragment later needs to be
         * created, the data you place in the Bundle here will be available
         * in the Bundle given to {@link #onCreate(android.os.Bundle)},
         * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}, and
         * {@link #onActivityCreated(android.os.Bundle)}.
         * <p/>
         * <p>This corresponds to {@link android.app.Activity#onSaveInstanceState(android.os.Bundle)
         * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
         * applies here as well.  Note however: <em>this method may be called
         * at any time before {@link #onDestroy()}</em>.  There are many situations
         * where a fragment may be mostly torn down (such as when placed on the
         * back stack with no UI showing), but its state will not be saved until
         * its owning activity actually needs to save its state.
         *
         * @param outState Bundle in which to place your saved state.
         */
        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            //todo save state of game
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
            Log.d("onActivityCreated","...");
            //todo restore state of game
        }
    }
}
