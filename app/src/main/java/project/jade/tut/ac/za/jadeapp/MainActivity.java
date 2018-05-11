package project.jade.tut.ac.za.jadeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.jade.tut.ac.za.jadeapp.fragment.BuyFragment;
import project.jade.tut.ac.za.jadeapp.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private EditText editFullName;
    private EditText editEmail;
    private Spinner spinnerSmartPhoneType;
    private Button btnBuy;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {R.drawable.advert,
            R.drawable.booking,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();


    }


    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
      //  tabLayout.getTabAt(2).setIcon(tabIcons[2]);
      //  tabLayout.getTabAt(3).setIcon(tabIcons[3]);
      //  tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new BuyFragment(), "Buy");
       // adapter.addFragment(new BookingFragment(), "Booking");
     //   adapter.addFragment(new EventFragment(), "Events");
       // adapter.addFragment(new AdvertFragment(),"Adverts");


        viewPager.setAdapter(adapter);

    }
    class  ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(Object supportFragmentManager) {
            super((android.support.v4.app.FragmentManager) supportFragmentManager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {


            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),
                    "No Settings Yet!...", Toast.LENGTH_LONG).show();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {

            System.exit(1);
            Toast.makeText(getApplicationContext(),
                    "GoodBye!...", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
