package ch.haclyon.driveimageview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DriveImageView extends RelativeLayout {
    private DriveImageViewLayout driveLayout;
    private ImageView imageView;

    public DriveImageModel driveImageModel;

    public DriveImageView(Context context) {
        super(context);
        initialize(context);
    }

    public DriveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DriveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.driveimage, this, true);
        driveLayout = (DriveImageViewLayout) findViewById(R.id.driveImageViewLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        driveLayout.bringToFront();
    }

    public DriveImageModel getDriveImageModel() {
        return driveImageModel;
    }

    public void setDriveImageModel(DriveImageModel driveImageModel) {
        this.driveImageModel = driveImageModel;

        if (driveImageModel.isDrawable()) {
            imageView.setImageResource(driveImageModel.getDrawable());
        } else {
            imageView.setImageBitmap(driveImageModel.getImageBitmap());
        }

        imageView.post(new Runnable() {
            @Override
            public void run() {
                driveLayout.setImageViewHeight(imageView.getMeasuredHeight());
                driveLayout.setImageViewWidth(imageView.getMeasuredWidth());
            }
        });

        driveLayout.setFolderText(driveImageModel.getFolderTitle());
        driveLayout.setMainText(driveImageModel.getMainTitle());

    }

    public void setBackgroundColour(String backgroundColour) {
        driveLayout.setBackgroundColour(backgroundColour);
    }

    public void setDevideColour(String devideColour) {
        driveLayout.setDivideColour(devideColour);
    }

    public void setTextColour(String textColour) {
        driveLayout.setTextColour(textColour);
    }

    public void setAlphaValue(float alphaValue) {
        driveLayout.setAlphaValue(alphaValue);
    }

    public void setImageScaleType(ImageView.ScaleType scale) {
        imageView.setScaleType(scale);
    }

    public void animateText() {
        animateText(3000);
    }

    public void animateText(int duration) {
        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
        animation.setDuration(duration);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setAlphaValue((Float) valueAnimator.getAnimatedValue());
            }
        });
        animation.start();
    }

    public void setCustomHeight(float customHeight) {
        driveLayout.setCustomHeight(customHeight);
        driveLayout.setImageViewHeight(imageView.getMeasuredHeight());
        driveLayout.setImageViewWidth(imageView.getMeasuredWidth());
    }

    public void setCustomFolderSpacing(float customFolderSpacing) {
        driveLayout.setCustomFolderSpacing(customFolderSpacing);
    }

    public float getFolderCorner() {
        return driveLayout.getFolderCorner();
    }

    public void setFolderCorner(float folderCorner) {
        driveLayout.setFolderCorner(folderCorner);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        driveLayout.setImageViewHeight(imageView.getMeasuredHeight());
        driveLayout.setImageViewWidth(imageView.getMeasuredWidth());
    }
}
