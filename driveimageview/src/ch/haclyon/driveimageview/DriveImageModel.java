package ch.haclyon.driveimageview;

import android.graphics.Bitmap;

public class DriveImageModel {

    private String mainTitle;
    private String folderTitle;
    private int drawable = R.drawable.placeholder;

    // TODO set placeholder as default
    private Bitmap imageBitmap;

    private boolean isDrawable = true;

    public DriveImageModel() {
        // nothing to do
    }

    public DriveImageModel(String mainTitle, String folderTitle, int drawable) {
        this.mainTitle = mainTitle;
        this.folderTitle = folderTitle;
        if (drawable != 0) {
            this.drawable = drawable;
        } else {
            this.drawable = R.drawable.placeholder;
        }
        this.isDrawable = true;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getFolderTitle() {
        return folderTitle;
    }

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
        isDrawable = true;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        isDrawable = false;
    }

    public boolean isDrawable() {
        return isDrawable;
    }

    public void setIsDrawable(boolean isDrawable) {
        this.isDrawable = isDrawable;
    }
}
