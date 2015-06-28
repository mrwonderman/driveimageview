package ch.haclyon.driveimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class DriveImageViewLayout extends View {

    private static Map<String, Integer> integerMap = new HashMap<>();
    private static int canvasWidth = 0;
    private static int canvasHeight = 0;

    private Paint backgroundPaint;
    private Paint outlinePaint;
    private Paint textPaint;
    private Paint debugPaint;

    private float customHeight;
    private float customFolderSpacing;
    private float folderCorner = 14f;
    private float imageViewHeight;
    private float imageViewWidth;

    private String folderText;
    private String mainText;
    private String backgroundColour = "#094ab2";
    private String divideColour = "#FFFFFF";
    private String textColour = "#FFFFFF";
    private String debugColour = "#FF0000";

    private float alphaValue = 1;
    private Context context;
    private Rect folderRect;
    private Rect mainRect;
    private Path folderPath = null;
    private Path dividerPath = null;

    private Paint paint = new Paint();
    private Rect rec = new Rect();


    public DriveImageViewLayout(Context context) {
        super(context);
        initializePaints(context);
    }

    public DriveImageViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaints(context);
    }

    public DriveImageViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializePaints(context);
    }

    private void initializePaints(Context context) {
        this.context = context;
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor(backgroundColour));
        backgroundPaint.setStrokeWidth(convertDpToPx(10, context));
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);

        outlinePaint = new Paint();
        outlinePaint.setColor(Color.parseColor(divideColour));
        outlinePaint.setStrokeWidth(convertDpToPx(3, context));
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor(textColour));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(convertDpToPx(30, context));
        textPaint.setStyle(Paint.Style.STROKE);

        debugPaint = new Paint();
        debugPaint.setColor(Color.parseColor(debugColour));
        debugPaint.setAntiAlias(true);
        debugPaint.setStyle(Paint.Style.STROKE);

        folderCorner = convertDpToPx(folderCorner, context);

        getWritableFolderRect(canvasWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvasWidth == 0) {
            canvasWidth = canvas.getWidth();
        }
        if (canvasHeight == 0) {
            canvasHeight = canvas.getHeight();
        }

        if (customHeight == 0.0 && customFolderSpacing == 0.0 && imageViewHeight == 0.0 || imageViewHeight == 0.0) {
            return;
        }

        if (customFolderSpacing == 0.0) {
            this.customFolderSpacing = Float.valueOf(String.valueOf(imageViewWidth - (imageViewWidth / ((1 + Math.sqrt(5)) / 2))));
        }

        if (customHeight == 0.0) {
            this.customHeight = Float.valueOf(String.valueOf(imageViewHeight - (imageViewHeight / ((1 + Math.sqrt(5)) / 2))));
        }

        canvas.drawRect(0, imageViewHeight - customHeight, canvasWidth, canvasHeight, backgroundPaint);
        canvas.drawPath(getFolderPath(), backgroundPaint);
        canvas.drawPath(getDividerPath(), outlinePaint);

        if (folderText != null && folderText.length() > 0) {
            getWritableFolderRect(canvasWidth);
            textPaint.setTextSize(determineMaxTextSize(folderText, folderRect));
            canvas.drawText(folderText, folderRect.left, folderRect.bottom, textPaint);
        }

        if (mainText != null && mainText.length() > 0) {
            getWritableMainRect(canvasWidth, canvasHeight);
            textPaint.setTextSize(determineMaxTextSize(mainText, mainRect));
            textPaint.setTextAlign(Paint.Align.LEFT);

            int spacing = (int) (((mainRect.bottom - mainRect.top) / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
            canvas.drawText(
                    mainText,
                    mainRect.left,
                    mainRect.top + spacing, textPaint);
        }

        setAlpha(alphaValue);
    }

    private Rect getWritableFolderRect(int cw) {
        if (cw != 0) {
            Rect r = new Rect();
            r.left = (int) (imageViewWidth - customFolderSpacing + (folderCorner + 8));
            r.top = (int) (imageViewHeight - customHeight - (folderCorner - 8));
            r.right = cw - 16;
            r.bottom = (int) (imageViewHeight - customHeight);
            folderRect = r;
            return folderRect;
        } else {
            return null;
        }
    }

    private Rect getWritableMainRect(int cw, int ch) {
        if (cw != 0 && ch != 0 && imageViewHeight != 0) {
            mainRect = new Rect(convertDpToPx(8, context), (int) (imageViewHeight - customHeight + convertDpToPx(8, context)), cw - convertDpToPx(8, context), ch - convertDpToPx(8, context));
            return mainRect;
        } else {
            return null;
        }
    }

    private Path getDividerPath() {
        Path p = new Path();
        p.moveTo(0, imageViewHeight - customHeight);
        p.lineTo(imageViewWidth - customFolderSpacing, imageViewHeight - customHeight);
        p.lineTo(imageViewWidth - customFolderSpacing + folderCorner, imageViewHeight - customHeight - folderCorner);
        p.lineTo(canvasWidth, imageViewHeight - customHeight - folderCorner);
        dividerPath = p;
        return dividerPath;
    }

    private Path getFolderPath() {
        Path p = new Path();
        p.moveTo(imageViewWidth - customFolderSpacing, imageViewHeight - customHeight);
        p.lineTo(imageViewWidth - customFolderSpacing + folderCorner, imageViewHeight - customHeight - folderCorner);
        p.lineTo(canvasWidth, imageViewHeight - customHeight - folderCorner);
        p.lineTo(canvasWidth, imageViewHeight - customHeight);
        p.close();
        folderPath = p;
        return folderPath;
    }

    public void setFolderText(String folderText) {
        this.folderText = folderText;
        this.invalidate();
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        this.invalidate();
    }

    private int determineMaxTextSize(String str, Rect rect) {
        int size = 0;
        boolean b, c;

        if (!integerMap.containsKey(str)) {
            do {
                paint.setTextSize(++size);
                paint.getTextBounds(str, 0, str.length(), rec);
                b = (rect.right - rect.left) > (rec.right - rec.left);
                c = (rect.bottom - rect.top) > (rec.bottom - rec.top);

            }
            while (b && c);
            integerMap.put(str, size - 1);
        }
        return integerMap.get(str);

    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
        backgroundPaint.setColor(Color.parseColor(backgroundColour));
        this.invalidate();
    }

    public String getDivideColour() {
        return divideColour;
    }

    public void setDivideColour(String divideColour) {
        this.divideColour = divideColour;
        this.invalidate();
    }

    public String getTextColour() {
        return textColour;
    }

    public void setTextColour(String textColour) {
        this.textColour = textColour;
        this.invalidate();
    }

    public float getAlphaValue() {
        return alphaValue;
    }

    public void setAlphaValue(float alphaValue) {
        this.alphaValue = alphaValue;
        this.invalidate();
    }

    public float getCustomHeight() {
        return customHeight;
    }

    public void setCustomHeight(float customHeight) {
        this.customHeight = convertDpToPx(customHeight, getContext());
        integerMap.remove(mainText);
        this.invalidate();
    }

    public float getCustomFolderSpacing() {
        return customFolderSpacing;
    }

    public void setCustomFolderSpacing(float customFolderSpacing) {
        this.customFolderSpacing = convertDpToPx(customFolderSpacing, getContext());
        ;
        this.invalidate();
    }

    public float getImageViewHeight() {
        return imageViewHeight;
    }

    public void setImageViewHeight(float imageViewHeight) {
        this.imageViewHeight = imageViewHeight;
        this.invalidate();
    }

    public float getImageViewWidth() {
        return imageViewWidth;
    }

    public void setImageViewWidth(float imageViewWidth) {
        this.imageViewWidth = imageViewWidth;
        this.invalidate();
    }

    public static int convertDpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public float getFolderCorner() {
        return folderCorner;
    }

    public void setFolderCorner(float folderCorner) {
        this.folderCorner = convertDpToPx(folderCorner, getContext());
        integerMap.remove(folderText);
        this.invalidate();
    }
}
