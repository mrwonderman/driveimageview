package ch.haclyon.driveimageview.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

import ch.haclyon.driveimageview.example.DisplayItemAdapter;
import ch.haclyon.driveimageview.example.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    public static String[] sampleTexts = new String[]{"check the map", "watching out", "on our way", "forest way", "warming up"};
    public static int[] samplePictures = new int[]{R.drawable.dscf4046, R.drawable.dscf4131, R.drawable.dscf7236, R.drawable.dscf7252, R.drawable.dscf7346};
    public static String[] sampleColours = new String[]{"#F44336", "#009688", "#4CAF50", "#FF5722", "#795548"};
    public static float[] sampleHeights = new float[]{50f, 100f, 60f, 45f, 125f};

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new DisplayItemAdapter(view.getContext(), new ArrayList<>(Arrays.asList(sampleTexts)), getFragmentManager()));

        return view;
    }
}
