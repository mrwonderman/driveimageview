package ch.haclyon.driveimageview.example;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ch.haclyon.driveimageview.DriveImageModel;
import ch.haclyon.driveimageview.DriveImageView;
import ch.haclyon.driveimageview.example.fragments.DetailFragment;
import ch.haclyon.driveimageview.example.fragments.MainFragment;


public class DisplayItemAdapter extends ArrayAdapter<String> {


    private FragmentManager fragmentManager;

    public DisplayItemAdapter(Context context, ArrayList<String> descriptions, FragmentManager fragmentManager) {
        super(context, 0, descriptions);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String desc = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(ch.haclyon.driveimageview.example.R.layout.item, parent, false);
        }

        String ftext = "photo number " + (position + 1) + " of " + MainFragment.samplePictures.length;
        DriveImageModel m = new DriveImageModel(desc, ftext, MainFragment.samplePictures[position]);

        DriveImageView view = (DriveImageView) convertView.findViewById(ch.haclyon.driveimageview.example.R.id.driveImageView);
        view.setDriveImageModel(m);
        view.setBackgroundColour(MainFragment.sampleColours[position]);
        view.setCustomFolderSpacing(100f);
        view.setAlphaValue(0.7f);
        view.setCustomHeight(MainFragment.sampleHeights[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailFragment detailFragment = DetailFragment.newInstance(position);
                fragmentTransaction.replace(ch.haclyon.driveimageview.example.R.id.fragment_container, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }

}