package com.madhouse.msu.v10.MSU;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;

import java.util.List;
import java.util.Locale;

/**
 * Created by krishnapratapsingh on 05/08/17.
 */

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private UserPreferences userPreferences;
    private RegistrationDetails registrationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        userPreferences = UserPreferences.getInstance(WelcomeActivity.this);

//        if (!userPreferences.isFirstTimeLaunch()) {
//            launchHomeScreen();
//            finish();
//        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if(userPreferences.getLanguageActivity().equalsIgnoreCase("english")){

            String languageToLoad = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());


        }

        else{
            String languageToLoad = "hi"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
                R.layout.welcome_slide6
        };

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    launchHomeScreen();
                } else {
                    launcLoginScreen();
                }


            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
       // userPreferences.setFirstTimeLaunch(false);
        //startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
       // Intent mainIntent = new Intent(WelcomeActivity.this, LoginScreen.class);
       // mainIntent.putExtra("empType", "admin");
       // startActivity(mainIntent);
       // finish();


        Intent mainIntent = new Intent(WelcomeActivity.this, RegistrationDetails.class);
        startActivity(mainIntent);
        finish();

//        number = mobileNumber.getText().toString();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("contactNumber", number);
//        registrationFragment = new RegistrationDetails();
//        registrationFragment.setArguments(bundle);
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
//        transaction.add(R.id.fragment_container, registrationFragment, "registrationFragment");
//        transaction.addToBackStack("registrationFragment");
//        transaction.commit();



//        Bundle bundle = new Bundle();
//        bundle.putString("contactNumber", "temp");
//        registrationFragment = new RegistrationDetails();
//        registrationFragment.setArguments(bundle);
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right);
//        transaction.add(R.id.fragment_container, registrationFragment, "registrationFragment");
//        transaction.addToBackStack("registrationFragment");
//        transaction.commit();

    }


    private void launcLoginScreen() {

        Intent mainIntent = new Intent(WelcomeActivity.this, LoginScreen.class);
        startActivity(mainIntent);
        finish();

    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

//            if(position == 0){
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
//            }
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start_signup));
               // btnSkip.setVisibility(View.GONE);
                btnSkip.setText(R.string.start_login);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);


            switch (position) {
                case 0:

                    try {
                        Button test = (Button) view.findViewById(R.id.testbtn);
                        RadioGroup languageGroup = (RadioGroup) view.findViewById(R.id.language_sel_group);

                        android.support.v7.widget.AppCompatRadioButton englishRad = (android.support.v7.widget.AppCompatRadioButton) view.findViewById(R.id.radio_english);
                        android.support.v7.widget.AppCompatRadioButton hindiRad = (android.support.v7.widget.AppCompatRadioButton) view.findViewById(R.id.radio_hindi);

                        if (userPreferences.getLanguageActivity().equalsIgnoreCase("english")) {
                            englishRad.setChecked(true);
                        } else {
                            hindiRad.setChecked(true);
                        }

                        languageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                if (checkedId == R.id.radio_english) {


                                    userPreferences.setLanguageActivity("english");


                                    String languageToLoad = "en"; // your language
                                    Locale locale = new Locale(languageToLoad);
                                    Locale.setDefault(locale);
                                    Configuration config = new Configuration();
                                    config.locale = locale;
                                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    //setContentView(R.layout.activity_welcome);

                                } else {

                                    userPreferences.setLanguageActivity("hindi");

                                    String languageToLoad = "hi"; // your language
                                    Locale locale = new Locale(languageToLoad);
                                    Locale.setDefault(locale);
                                    Configuration config = new Configuration();
                                    config.locale = locale;
                                    getBaseContext().getResources().updateConfiguration(config,
                                            getBaseContext().getResources().getDisplayMetrics());

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    // setContentView(R.layout.activity_welcome);

                                }

                            }
                        });


                        test.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(WelcomeActivity.this, "Yes it works", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    catch (Exception er){
                        er.printStackTrace();
                    }
                    break;
                case 1:
                    //resId = R.layout.left;
                    break;
                case 2:
                    //resId = R.layout.middle;
                    break;
                case 3:
                    //resId = R.layout.right;
                    break;
                case 4:
                    //resId = R.layout.farright;
                    break;
                case 5:
                    //resId = R.layout.farright;
                    break;

            }



            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, WelcomeActivity.class);
        startActivity(refresh);
        finish();
    }
}