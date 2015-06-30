package ch.haclyon.driveimageview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * The basic {@link DriveImageView}. This class includes all the methods you
 * need to modify your {@link DriveImageView}.
 *
 * @author Yannick Signer
 * @since 1.0.0
 */
public class DriveImageView extends RelativeLayout {
    private DriveImageViewLayout driveLayout;
    private ImageView imageView;

    public DriveImageModel driveImageModel;

    /**
     * A new DriveImageView.
     *
     * @param context the {@link Context}
     * @author ysigner
     */
    public DriveImageView(Context context) {
        super(context);
        initialize(context);
    }

    /**
     * A new DriveImageView.
     *
     * @param context the {@link Context}
     * @param attrs   the {@link AttributeSet}
     */
    public DriveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    /**
     * A new DriveImageView.
     *
     * @param context      the {@link Context}
     * @param attrs        the {@link AttributeSet}
     * @param defStyleAttr the defStyleAttr
     */
    public DriveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * This initialises the main android widgets of the layout.
     *
     * @param context the {@link Context}
     */
    private void initialize(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.driveimage, this, true);
        driveLayout = (DriveImageViewLayout) findViewById(R.id.driveImageViewLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        driveLayout.bringToFront();
    }

    /**
     * Returns the current {@link DriveImageModel} from the {@link DriveImageView}.
     *
     * @return the current model.
     */
    public DriveImageModel getDriveImageModel() {
        return driveImageModel;
    }

    /**
     * Sets the {@link DriveImageModel} to the {@link DriveImageView}.
     * This will cause the DriveImageView to redraw its figures and it also recalculates the size of the Canvas, because the size of the image can change.
     *
     * @param driveImageModel the {@link DriveImageModel}
     */
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

    /**
     * Sets the background-color of the main figure on the canvas.
     *
     * @param backgroundColor the color as a Color-String. Using {@link android.graphics.Color#parseColor(String)}.
     */
    public void setBackgroundColor(String backgroundColor) {
        driveLayout.setBackgroundColour(backgroundColor);
    }

    /**
     * Sets the background-color of the divider figure.
     *
     * @param divideColor the color as a Color-String. Using {@link android.graphics.Color#parseColor(String)}.
     */
    public void setDivideColor(String divideColor) {
        driveLayout.setDivideColour(divideColor);
    }

    /**
     * Sets the text-color of both text's (main-text and folder-text)
     *
     * @param textColor the color as a Color-String. Using {@link android.graphics.Color#parseColor(String)}.
     */
    public void setTextColor(String textColor) {
        driveLayout.setTextColour(textColor);
    }

    /**
     * Sets the opacity value of all the figures on the canvas.
     *
     * @param alphaValue the opacity (alpha) value in float, between 0f and 1f.
     */
    public void setAlphaValue(float alphaValue) {
        driveLayout.setAlphaValue(alphaValue);
    }

    /**
     * Sets the {@link ImageView.ScaleType} onto the {@link ImageView}.
     *
     * @param scale the {@link ImageView.ScaleType}.
     */
    public void setImageScaleType(ImageView.ScaleType scale) {
        imageView.setScaleType(scale);
    }

    /**
     * This animates the text with a duration of 3 seconds.
     */
    public void animateText() {
        animateText(3000);
    }

    /**
     * This a animates a "blend-in" animation of the figure. The duration can be customised.
     *
     * @param duration The duration in milliseconds
     */
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

    /**
     * Sets the custom height of the whole figure. This recalculates the text-sizes inside the Canvas.
     *
     * @param customHeight the desired height in dp.
     */
    public void setCustomHeight(float customHeight) {
        driveLayout.setCustomHeight(customHeight);
        driveLayout.setImageViewHeight(imageView.getMeasuredHeight());
        driveLayout.setImageViewWidth(imageView.getMeasuredWidth());
    }

    /**
     * Sets the spacing between the left border of the DriveImageView and the crack before the folder-text.
     *
     * @param customFolderSpacing the desired spacing in dp.
     */
    public void setCustomFolderSpacing(float customFolderSpacing) {
        driveLayout.setCustomFolderSpacing(customFolderSpacing);
    }

    /**
     * Returns the value of the folderCorner.
     *
     * @return the folderCorner value.
     */
    public float getFolderCorner() {
        return driveLayout.getFolderCorner();
    }

    /**
     * Sets the height and width of the folder corner.
     *
     * @param folderCorner height and width in dp.
     */
    public void setFolderCorner(float folderCorner) {
        driveLayout.setFolderCorner(folderCorner);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        driveLayout.setImageViewHeight(imageView.getMeasuredHeight());
        driveLayout.setImageViewWidth(imageView.getMeasuredWidth());
    }
}
