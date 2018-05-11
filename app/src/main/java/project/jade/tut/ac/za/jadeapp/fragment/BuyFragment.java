package project.jade.tut.ac.za.jadeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import project.jade.tut.ac.za.jadeapp.PhoneActivity;
import project.jade.tut.ac.za.jadeapp.ProcessingActivity;
import project.jade.tut.ac.za.jadeapp.R;


public class BuyFragment extends Fragment {

    private Spinner spinnerSmartPhoneType;
    private Spinner spinnerSmartPrice;
    private Button btnProceed;
    private Button btnClear;

    ArrayAdapter<String> spinnerArrayAdapter =  null;
    ArrayAdapter<String> spinnerArrayAdapterPrice =  null;

    public BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buy, container, false);


        btnProceed = (Button) rootView.findViewById(R.id.btnProceed);
        btnClear = (Button) rootView.findViewById(R.id.btnClear);
        spinnerSmartPhoneType = (Spinner)rootView.findViewById(R.id.spinnerCellPhoneType);
       // spinnerSmartPrice = (Spinner)rootView.findViewById(R.id.spinnerCellPhonePrice);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phoneType = spinnerSmartPhoneType.getSelectedItem().toString();
              //  String phonePrice = spinnerSmartPrice.getSelectedItem().toString();

                Intent intent = new Intent(getActivity(), PhoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phoneType",phoneType);
             //   bundle.putString("phonePrice",phonePrice);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);



               // System.out.println("PhonePrice " + phonePrice);
               // System.out.println("PhoneType " + phoneType);

                //getActivity().startActivity(intent);



            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerSmartPhoneType.setSelection(0);
                //spinnerSmartPrice.setSelection(0);

            }
        });
        // Initializing a String Array
        String[] phones = new String[]{
                "Select Smart Phone",
                "Samsung",
                "Apple",
                "Huawei"
        };
        // Initializing a String price Array
       String[] prices = new String[]{
                "Select Phone Price",
                "8500",
                "12000",
                "15000"
        };

        // Initializing an ArrayAdapter
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,phones);

        // Initializing an ArrayAdapter
       // spinnerArrayAdapterPrice = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,prices);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSmartPhoneType.setAdapter(spinnerArrayAdapter);
        spinnerSmartPhoneType.setSelection(0);

      //  spinnerArrayAdapterPrice.setDropDownViewResource(R.layout.spinner_item);
     //   spinnerSmartPrice.setAdapter(spinnerArrayAdapterPrice);
     ///   spinnerSmartPrice.setSelection(0);

        return rootView;
    }
}
