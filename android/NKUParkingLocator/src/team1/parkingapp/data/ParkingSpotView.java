package team1.parkingapp.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ParkingSpotView extends View
{

	private float topLeftX;
	private float topLeftY;
	private float botRightX;
	private float botRightY;
	private boolean isAvail;
	private double Latitude;
	private double Longitude;
	private int spotID;
	private Paint linePaint;
	private Paint circlePaint;
	private Paint unAvailPaint;
	private float radius;
	private static final float THICKNESS = 10;
	
	public ParkingSpotView(Context ctx, float topLeftX, float topLeftY, float botRightX, float botRightY,
			float radius, boolean isAvail, double Latitude, double Longitude, int spotID, Activity parent)
	{
		super(ctx);
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.botRightX = botRightX;
		this.botRightY = botRightY;
		this.isAvail = isAvail;
		this.Latitude = Latitude;
		this.Longitude = Longitude;
		this.spotID = spotID;
		this.radius = radius;
		linePaint = new Paint();
		linePaint.setStrokeWidth(THICKNESS);
		linePaint.setColor(Color.rgb(47, 79, 79));
		circlePaint = new Paint();
		circlePaint.setColor(Color.GREEN);
		unAvailPaint = new Paint();
		unAvailPaint.setColor(Color.RED);
		unAvailPaint.setStrokeWidth(THICKNESS);
		
		this.setMeasuredDimension((int)(botRightX - topLeftX), (int)(botRightY - topLeftY));
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawLine(topLeftX, topLeftY, botRightX, topLeftY, linePaint);
		canvas.drawLine(topLeftX, topLeftY, topLeftX, botRightY, linePaint);
		canvas.drawLine(botRightX, topLeftY, botRightX, botRightY, linePaint);
		canvas.drawLine(topLeftX, botRightY, botRightX, botRightY, linePaint);
		if(isAvail)
			canvas.drawCircle( (topLeftX + botRightX) / 2, (topLeftY + botRightY) / 2, radius, circlePaint);
		else
		{
			canvas.drawLine(topLeftX, topLeftY, botRightX, botRightY, unAvailPaint);
			canvas.drawLine(botRightX, topLeftY, topLeftX, botRightY, unAvailPaint);
		}
		
	}
	
	public boolean isAvail()
	{
		return isAvail;
	}
	public double getLatitude()
	{
		return Latitude;
	}
	public double getLongitude()
	{
		return Longitude;
	}
	public int getSpotID()
	{
		return spotID;
	}
	
}
