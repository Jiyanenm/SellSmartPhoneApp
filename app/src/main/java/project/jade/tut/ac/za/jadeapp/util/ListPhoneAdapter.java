package project.jade.tut.ac.za.jadeapp.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import project.jade.tut.ac.za.jadeapp.R;
import project.jade.tut.ac.za.jadeapp.model.Phone;

public class ListPhoneAdapter  extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Phone> phoneItems;

    ImageLoader imageLoader = AppController.getmInstance().getImageLoader();

    public ListPhoneAdapter(Activity activity, List<Phone> phoneItems) {

        this.activity = activity;
        this.phoneItems = phoneItems;
    }


    @Override
    public int getCount() {
        return phoneItems.size();
    }

    @Override
    public Object getItem(int location) {
        return phoneItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)


            convertView = inflater.inflate(R.layout.list_row,null);

        if (imageLoader == null)

            imageLoader = AppController.getmInstance().getImageLoader();

        try
        {
            NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
            TextView brand = (TextView) convertView.findViewById(R.id.brand);
            TextView model = (TextView) convertView.findViewById(R.id.model);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            //TextView year = (TextView) convertView.findViewById(R.id.year);

            // getting movie data for the row
            Phone objPhone = phoneItems.get(position);

            //Defualt to Image
            thumbNail.setDefaultImageResId(R.drawable.def);
            thumbNail.setImageUrl(objPhone.getImage(),imageLoader);

            brand.setText(objPhone.getBrand());
            model.setText(objPhone.getModel());
            price.setText(String.valueOf(objPhone.getPrice()));
            //year.setText(objPhone.getYearReleased());

        }
        catch(Exception err)
        {
            Log.e("CustomeListAdapter : ", err.getMessage());
            err.printStackTrace();
        }

        return convertView;
    }
}
