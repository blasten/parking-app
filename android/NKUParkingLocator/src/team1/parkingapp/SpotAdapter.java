/*
 * SpotAdapter.java
 * 4/28/14
 * Travis Carney
 * 
 * This class is used to implement a SpotList. It formats the views to display information about
 * each spot in a list.
 */
package team1.parkingapp;

import java.util.List;
import java.util.Locale;

import team1.parkingapp.data.Spot;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpotAdapter extends ArrayAdapter<Spot> {
	private final Context ctx;								// Context of the activity using this adapter
	private final List<Spot> values;						// Source of data
	private final String SPOT_NUM_HEADER = "Spot Number: "; // Header for the spot number
	private final String SPOT_STATUS_HEADER = "Status: ";	// Header for the spot status

	/*
	 * Assign references.
	 */
	public SpotAdapter(Context ctx, int resource, List<Spot> values) {
		super(ctx, resource, values);
		this.ctx = ctx;
		this.values = values;
	}
	
	/*
	 * Creates a row containing information about a spot.
	 * The first piece of information is the spot number and the second is its status.
	 * EX)
	 * Spot Number: 123
	 * Status: Occupied
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) ctx
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    // Get the views
	    View row = inflater.inflate(R.layout.spot_row, parent, false);
	    TextView spotNum = (TextView) row.findViewById(R.id.spot_num);
	    TextView spotStatus = (TextView) row.findViewById(R.id.spot_status);
	    
	    // Assign the values
	    spotNum.setText(SPOT_NUM_HEADER + Integer.toString(values.get(position).getId()));
	    spotStatus.setText(SPOT_STATUS_HEADER + capitalize(values.get(position).getStatus()));
	    
	    return row;
	}
	
	/*
	 * Simply capitalizes the given string. 
	 */
	private String capitalize(String str) {
		String temp = str.substring(1).toLowerCase(Locale.ENGLISH);
		char first = str.charAt(0);
		
		return first + temp;
	}
}
