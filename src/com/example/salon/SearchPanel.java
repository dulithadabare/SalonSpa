package com.example.salon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SearchPanel extends RelativeLayout{

	
	public SearchPanel(Context context)
	{
	super(context);
	}

	public SearchPanel(Context context, AttributeSet attrs)
	{
	super(context, attrs);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
	Paint innerPaint = new Paint();
	Paint borderPaint = new Paint();

	RectF drawRect = new RectF();
	drawRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());

	innerPaint.setARGB(190, 245, 245, 245);

	borderPaint.setARGB(255, 21, 159, 209);
	borderPaint.setAntiAlias(true);
	borderPaint.setStyle(Style.STROKE);
	borderPaint.setStrokeWidth(3);

	canvas.drawRoundRect(drawRect, 40, 40, innerPaint);
	canvas.drawRoundRect(drawRect, 40, 40, borderPaint);

	super.dispatchDraw(canvas);
	}
}
