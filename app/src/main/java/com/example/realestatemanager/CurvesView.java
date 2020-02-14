package com.example.realestatemanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class CurvesView extends View {

    private Path topLinePath, bottomLinePath, topLineBorderPath, bottomLineBorderPath;
    private Paint topLinePaint, bottomLinePaint, topLineBorderPaint, bottomLineBorderPaint;

    Context context;
    CurvesType type = CurvesType.undefined;

    public enum CurvesType {
        undefined, toolbar_blue, indicators_white_grey, indicators_grey_white, toolbar_blue_reversed, dashboard_weather_white_top, toolbar_white, login_bottom_black
    }

    public CurvesView(Context context)
    {
        super(context);
        this.context = context;
        init();

    }

    public CurvesView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CurvesView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        init();

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void init() {
        topLinePath = new Path();
        bottomLinePath = new Path();
        topLineBorderPath = new Path();
        bottomLineBorderPath = new Path();
        topLinePaint = new Paint();
        bottomLinePaint = new Paint();
        topLineBorderPaint = new Paint();
        bottomLineBorderPaint = new Paint();
    }

    private void init(CurvesType type) {
        topLinePath = new Path();
        bottomLinePath = new Path();
        topLineBorderPath = new Path();
        bottomLineBorderPath = new Path();
        topLinePaint = new Paint();
        bottomLinePaint = new Paint();
        topLineBorderPaint = new Paint();
        bottomLineBorderPaint = new Paint();

        switch (type) {
            case toolbar_blue:
            case toolbar_blue_reversed:
                topLinePaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
                topLinePaint.setAntiAlias(true);
                topLinePaint.setStyle(Paint.Style.FILL);

                bottomLinePaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
                bottomLinePaint.setAntiAlias(true);
                bottomLinePaint.setStyle(Paint.Style.FILL);
                bottomLinePaint.setAlpha(125);
                break;

            case indicators_white_grey:
            case indicators_grey_white:
                topLinePaint.setColor(ContextCompat.getColor(context, R.color.colorWhite));
                topLinePaint.setAntiAlias(true);
                topLinePaint.setStyle(Paint.Style.FILL);
                topLinePaint.setShadowLayer(30, 0, 0, ContextCompat.getColor(context, R.color.colorLocationDark));
                setLayerType(LAYER_TYPE_SOFTWARE, topLinePaint);
                break;

            case dashboard_weather_white_top:

            case toolbar_white:
                topLinePaint.setColor(ContextCompat.getColor(context, R.color.colorWhite));
                topLinePaint.setAntiAlias(true);
                topLinePaint.setStyle(Paint.Style.FILL);

                bottomLinePaint.setColor(ContextCompat.getColor(context, R.color.colorWhite));
                bottomLinePaint.setAntiAlias(true);
                bottomLinePaint.setStyle(Paint.Style.FILL);
                bottomLinePaint.setAlpha(125);
                break;

            case login_bottom_black:
                bottomLinePaint.setColor(ContextCompat.getColor(context, R.color.colorBlack));
                bottomLinePaint.setAntiAlias(true);
                bottomLinePaint.setStyle(Paint.Style.FILL);
                bottomLinePaint.setAlpha(95);
                break;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (type) {
            case toolbar_blue:
                bottomLinePath.moveTo(0, 0);
                bottomLinePath.lineTo(0, (float) (getHeight() * (72.5 / 100.0)));
                bottomLinePath.cubicTo((float) (getWidth() * (28.7 / 100.0)), (float) (getHeight() * (143.8 / 100.0)), (float) (getWidth() * (58.7 / 100.0)), (float) (getHeight() * (36.3 / 100.0)), getWidth(), (float) (getHeight() * (37.5 / 100.0)));
                bottomLinePath.lineTo(getWidth(), 0);
                bottomLinePath.close();
                canvas.drawPath(bottomLinePath, bottomLinePaint);

                topLinePath.moveTo(0, 0);
                topLinePath.cubicTo((float) (getWidth() * (37.5 / 100.0)), (float) (getHeight() * (87.5 / 100.0)), (float) (getWidth() * (59.2 / 100.0)), (float) (getHeight() * (12.5 / 100.0)), getWidth(), (float) (getHeight() * (18.6 / 100.0)));
                topLinePath.lineTo(getWidth(), 0);
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case toolbar_blue_reversed:
                bottomLinePath.moveTo(getWidth(), 0);
                bottomLinePath.lineTo(getWidth(), (float) (getHeight() * (72.5 / 100.0)));
                bottomLinePath.cubicTo((float) (getWidth() * (58.7 / 100.0)), (float) (getHeight() * (143.8 / 100.0)), (float) (getWidth() * (28.7 / 100.0)), (float) (getHeight() * (36.3 / 100.0)), 0, (float) (getHeight() * (37.5 / 100.0)));
                bottomLinePath.lineTo(0, 0);
                bottomLinePath.close();
                canvas.drawPath(bottomLinePath, bottomLinePaint);

                topLinePath.moveTo(getWidth(), 0);
                topLinePath.cubicTo((float) (getWidth() * (59.2 / 100.0)), (float) (getHeight() * (87.5 / 100.0)), (float) (getWidth() * (37.5 / 100.0)), (float) (getHeight() * (12.5 / 100.0)), 0, (float) (getHeight() * (18.6 / 100.0)));
                topLinePath.lineTo(0, 0);
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case indicators_white_grey:
                topLinePath.moveTo((float) (-getWidth() * (10.0 / 100.0)), (float) (-getHeight() * (5.0 / 100.0)));
                topLinePath.cubicTo((float) (getWidth() * (42.6 / 100.0)), (float) (getHeight() * (110.5 / 100.0)), (float) (getWidth() * (133.3 / 100.0)), (float) (getHeight() * (71.1 / 100.0)), (float) (getWidth() * (110.0 / 100.0)), (float) (-getHeight() * (5.0 / 100.0)));
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case indicators_grey_white:
                topLinePath.moveTo((float) (getWidth() * (130.0 / 100.0)), getHeight());
                topLinePath.cubicTo((float) (getWidth() * (63.3 / 100.0)), (float) (getHeight() * (91.1 / 100.0)), (float) (getWidth() * (22.6 / 100.0)), (float) (getHeight() * (35.5 / 100.0)), (float) (-getWidth() * (10.0 / 100.0)), getHeight());
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case dashboard_weather_white_top:
                bottomLinePath.moveTo(0, getHeight());
                bottomLinePath.lineTo(0, (float) (getHeight() * (27.5 / 100.0)));
                bottomLinePath.cubicTo((float) (getWidth() * (32.7 / 100.0)), (float) (getHeight() * (-43.8 / 100.0)), (float) (getWidth() * (58.7 / 100.0)), (float) (getHeight() * (63.7 / 100.0)), getWidth(), (float) (getHeight() * (90.0 / 100.0)));
                bottomLinePath.lineTo(getWidth(), getHeight());
                bottomLinePath.close();
                canvas.drawPath(bottomLinePath, bottomLinePaint);

                topLinePath.moveTo(0, getHeight());
                topLinePath.lineTo(0, (float) (getHeight() * (62.0 / 100.0)));
                topLinePath.cubicTo((float) (getWidth() * (37.5 / 100.0)), (float) (getHeight() * (23.8 / 100.0)), (float) (getWidth() * (59.2 / 100.0)), (float) (getHeight() * (87.5 / 100.0)), getWidth(), getHeight());
                topLinePath.lineTo(getWidth(), getHeight());
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case toolbar_white:
                bottomLinePath.moveTo(getWidth(), 0);
                bottomLinePath.lineTo(getWidth(), (float) (getHeight() * (52.5 / 100.0)));
                bottomLinePath.cubicTo((float) (getWidth() * (58.7 / 100.0)), (float) (getHeight() * (143.8 / 100.0)), (float) (getWidth() * (28.7 / 100.0)), (float) (getHeight() * (36.3 / 100.0)), 0, (float) (getHeight() * (37.5 / 100.0)));
                bottomLinePath.lineTo(0, 0);
                bottomLinePath.close();
                canvas.drawPath(bottomLinePath, bottomLinePaint);

                topLinePath.moveTo(getWidth(), 0);
                topLinePath.cubicTo((float) (getWidth() * (59.2 / 100.0)), (float) (getHeight() * (87.5 / 100.0)), (float) (getWidth() * (37.5 / 100.0)), (float) (getHeight() * (12.5 / 100.0)), 0, (float) (getHeight() * (18.6 / 100.0)));
                topLinePath.lineTo(0, 0);
                topLinePath.close();
                canvas.drawPath(topLinePath, topLinePaint);
                break;

            case login_bottom_black:
                bottomLinePath.moveTo(getWidth(), getHeight());
                bottomLinePath.lineTo(getWidth(), (float) (getHeight() * (15.2 / 100.0)));
                bottomLinePath.cubicTo((float) (getWidth() * (68.7 / 100.0)), (float) (getHeight() * (0 / 100.0)), (float) (getWidth() * (18.7 / 100.0)), (float) (getHeight() * (20.7 / 100.0)), 0, (float) (getHeight() * (20.7 / 100.0)));
                bottomLinePath.lineTo(0, getHeight());
                bottomLinePath.close();
                canvas.drawPath(bottomLinePath, bottomLinePaint);
                break;
        }
    }
}
