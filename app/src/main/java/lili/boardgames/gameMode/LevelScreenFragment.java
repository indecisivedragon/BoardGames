package lili.boardgames.gameMode;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import lili.boardgames.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LevelScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LevelScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelScreenFragment extends Fragment {
    //take in height and width of screen
    private static final String SCREEN_HEIGHT = "height";
    private static final String SCREEN_WIDTH = "width";
    private static final String SCREEN_ROTATION = "rotation";

    private int height;
    private int width;
    private int rotation;

    private GameView gameView;
    private String gameType;

    private OnFragmentInteractionListener mListener;

    private String DEBUG_TAG = "level screen fragment";

    public LevelScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param height the height of the total screen
     * @param width the width of the total screen
     * @param rotation the screen orientation
     * @return A new instance of fragment LevelScreenFragment.
     */
    public static LevelScreenFragment newInstance(int height, int width, int rotation, String gameType) {
        LevelScreenFragment fragment = new LevelScreenFragment();
        Bundle args = new Bundle();
        args.putInt(SCREEN_HEIGHT, height);
        args.putInt(SCREEN_WIDTH, width);
        args.putInt(SCREEN_ROTATION, rotation);
        args.putString(StartGameActivity.extra_key_name, gameType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            height = getArguments().getInt(SCREEN_HEIGHT);
            width = getArguments().getInt(SCREEN_WIDTH);
            rotation = getArguments().getInt(SCREEN_ROTATION);
            this.gameType = getArguments().getString(StartGameActivity.extra_key_name);
        }

        Log.d(DEBUG_TAG, "level screen fragment created as " + gameType);

        mListener.onFragmentStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level_screen, container, false);

        FrameLayout layout = (FrameLayout) view.findViewById(R.id.level_screen_layout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();

        //depending on orientation, resize the screen
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            params.height = height/2;
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
        }
        else if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.width = width/2;
            params.gravity = Gravity.END;
        }
        layout.setLayoutParams(params);

        Log.d(DEBUG_TAG, "input: height " + params.height + ", width " + params.width + ", rotation " + rotation);

        gameView = new GameView(this.getContext(), gameType);
        gameView.setBackgroundColor(Color.GRAY);
        layout.addView(gameView);

        return view;
        //return inflater.inflate(R.layout.fragment_level_screen, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void resetGameScreen(int height, int width) {
        System.out.println("Reset Game Screen");
        gameView.setBounds(height, width);
        gameView.resetScreen(true);
        gameView.invalidate();

        mListener.onLevelScreenChange(gameView.getGameStatus());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onFragmentStart();

        void onLevelScreenChange(String s);
    }

    public GameView getGameView() {
        return gameView;
    }
}
