package me.chrissembiring.tap2fly;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ManifestAdapter extends BaseAdapter
{

    Context c;
    ArrayList<FlightManifest> flightManifest;

    public ManifestAdapter(Context context, ArrayList<FlightManifest> list)
    {
        this.c = context;
        flightManifest = list;
    }

    @Override
    public int getCount()
    {
        return flightManifest.size();
    }

    @Override
    public Object getItem(int position)
    {
        return flightManifest.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        FlightManifest fm = flightManifest.get(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_adapter, null);
        }

        TextView passengerName = (TextView) convertView.findViewById(R.id.passengerName);
        TextView boardingTime = (TextView) convertView.findViewById(R.id.boardingTime);
        TextView flightNo = (TextView) convertView.findViewById(R.id.flghtno);
        TextView seatNumber = (TextView) convertView.findViewById(R.id.seatnumber);

        return convertView;
    }

}