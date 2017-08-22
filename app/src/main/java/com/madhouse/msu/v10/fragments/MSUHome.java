package com.madhouse.msu.v10.fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouse.msu.v10.MSU.MainActivity;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.AlbumsAdapter;
import com.madhouse.msu.v10.applicationUtils.ApplicationConstants;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.OnSwipeTouchListener;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.applicationUtils.Youtube;
import com.madhouse.msu.v10.bean.Album;
import com.madhouse.msu.v10.bean.User;
import com.madhouse.msu.v10.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MSUHome extends Fragment implements
		AnimationListener {
	String res;
	Button musicButton, submitBtnDialog;
	// 2208
	// Animation
	Animation animFadein, slide_in_right;
	LinearLayout redBulb;
	ViewPager view_pager_cc, view_pager;
	int count = 0;
	int countNew = 0;
	Timer timer, timer2;
	Editor editor;
	private UserPreferences userPreferences;

	// 2208
	LinearLayout customercare;
	LinearLayout callBrpl, callBypl;
	ImageButton closeDilogUpdateInfo, closeDialog;
	EditText caInputEditText, caInputEditTextInstaPay;
	String frmActivity = "";
	TextView txtViewOnSelectingOption;
	private boolean isBackClick = false;
	// 2508
	User user;
	//Instant Payment
	boolean internetbool = true;
	boolean notPayable=false;
	String smsResponse = "";
	Button submitBtnDialogLogin, submitBtnDialog1;
	Button loginBtn;
	// Feedback
	EditText fbUserName, fbMobileNo, fbEmailId, empVerify;
	// static NewUserActivity instance;
	// 0909
	// 1409
	ImageView complainBtnImg;
	Editor editorSetGCMRegi, editorCAKeys;
	ProgressDialog progressDialog;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;

	public static String FACEBOOK_URL = "https://www.facebook.com/RajCMO/";
	public static String FACEBOOK_PAGE_ID = "948589071935486";



	// 276043932521201- brpl page id
	public static String twitter_user_name = "diprrajasthan";
	Button fb, twitter;
	Animation shake;
	private Button locateus;
	LinearLayout strLightReg, reachUsNew, feedbackBtn;
	Spinner companytxt;
	String INSTANT_CA_NUM = "instantca";
	private boolean fbConnect;
	private boolean  twitterConnect;
	public static final String PAYMENT_CA_PREFS_NAME = "MyPrefsFile";

	private RecyclerView recyclerView;
	private AlbumsAdapter compundAdapter;
	private List<Album> albumList;
	private ArrayList<String> tutorialinfo = new ArrayList<>();
	private int[] covers1= new int[5];
	View viewm = null;
	private final static int INTERVAL = 1000 * 60 * 2; //2 minutes
	private ImageView iv2,iv,iv1,iv3,iv4;
	private boolean otherLinkToggle = false;
	private View rootView ;
	private String canYouDoImg1, canYouDoImg2, canYouDoImg3, canYouDoImg4, canYouDoImg5;
	private ImageView done;
	private TextView yes, no;
	ProgressDialog dialog;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




		// TODO Auto-generated method stub
      /*
       *
       * text blink code Animation anim = new AlphaAnimation(0.0f, 1.0f);
       * anim.setDuration(50); //You can manage the blinking time with this
       * parameter anim.setStartOffset(20);
       * anim.setRepeatMode(Animation.REVERSE);
       * anim.setRepeatCount(Animation.INFINITE); myText.startAnimation(anim);
       */


		super.onCreate(savedInstanceState);
		//setContentView(R.layout.msu_home);
		//Inflate the layout for this fragment
		rootView =  inflater.inflate( R.layout.msu_home, container, false);
		dialog = new ProgressDialog(getActivity());

		//Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
		//getActivity().setSupportActionBar(toolbar);
		//((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

		userPreferences = UserPreferences.getInstance(getActivity());

		//initCollapsingToolbar();
		recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

		albumList = new ArrayList<>();
		compundAdapter = new AlbumsAdapter(getActivity(), albumList);

		GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

		mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				// switch(adapter.getItemViewType(position)){
				//return compundAdapter.isHeader(position) ? 2 : 1;
				if(compundAdapter.isHeader(position) == 1){
					return 2;
				}
				if(compundAdapter.isHeader(position) == 2){
					return 2;
				}
				if(compundAdapter.isHeader(position) == 3){
					return 2;
				}
				if(compundAdapter.isHeader(position) == 4){
					return 2;
				}
				else{
					return 1;
				}
				// }
			}
		});

		recyclerView.setLayoutManager(mLayoutManager);
		recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.setAdapter(compundAdapter);
      /*ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

      scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
         public void run() {
            prepareAlbums();
         }
      }, 0, 10, TimeUnit.SECONDS);*/


		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref1))){
			tutorialinfo.add(0,getString(R.string.pref1));
			tutorialinfo.add(1,"2oNsMva04MM");
			tutorialinfo.add(2,"Bu3ulVhO3z4");
			tutorialinfo.add(3,"http://www.wikihow.com/Become-an-Artist");
			tutorialinfo.add(4,"http://www.wikihow.com/Paint");
			tutorialinfo.add(5,"6ky_zdx0f64");
			tutorialinfo.add(6,"7GPXGHjeDHI");
			tutorialinfo.add(7,"http://paintingbusinesspro.com/how-to-start-a-painting-business-from-scratch/");
			tutorialinfo.add(8,"http://emptyeasel.com/2010/02/23/10-steps-for-creating-a-successful-business-from-your-art/");

			covers1 = new int[]{
					R.drawable.a1,
					R.drawable.a2,
					R.drawable.a3,
					R.drawable.a4,
					R.drawable.a5

			};

		}

		else if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref2))){
			tutorialinfo.add(0,getString(R.string.pref2));
			tutorialinfo.add(1,"MWYi4_COZMU");
			tutorialinfo.add(2,"X9CYKgF-f9M");
			tutorialinfo.add(3,"http://www.wikihow.com/Be-a-Professional-Content-Writer");
			tutorialinfo.add(4,"https://amylynnandrews.com/how-to-start-a-blog/");
			tutorialinfo.add(5,"kfiYXowNzdg");
			tutorialinfo.add(6,"tDEyoU787sc");
			tutorialinfo.add(7,"http://www.makingsenseofcents.com/2016/09/how-to-quit-your-job-and-become-a-full-time-blogger.html");
			tutorialinfo.add(8,"https://www.peopleperhour.com/freelance-content-writing-jobs");

		}

		else if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref3))){
			tutorialinfo.add(0,getString(R.string.pref3));
			tutorialinfo.add(1,"qWnjlqGkB9I");
			tutorialinfo.add(2,"MQIupdAyRtI");
			tutorialinfo.add(3,"http://www.wikihow.com/Design-Clothes");
			tutorialinfo.add(4,"http://www.clothingpatterns101.com/");
			tutorialinfo.add(5,"klqDzbOU7Ok");
			tutorialinfo.add(6,"x8NJKqAaecE");
			tutorialinfo.add(7,"http://www.marieclaire.com/culture/a7896/how-to-start-your-own-clothing-label/");
			tutorialinfo.add(8,"http://www.wikihow.com/Start-a-Clothing-Line");

			covers1 = new int[]{
					R.drawable.cloth,
					R.drawable.cloth2,
					R.drawable.cloth3,
					R.drawable.cloth4,
					R.drawable.cloth5

			};

		}
		else if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref4))){
			tutorialinfo.add(0,getString(R.string.pref4));
			tutorialinfo.add(1,"8kCziFDK3Yk");
			tutorialinfo.add(2,"M63eqRT9k4");
			tutorialinfo.add(3,"http://www.wikihow.com/Organise-an-Event");
			tutorialinfo.add(4,"https://www.wildapricot.com/articles/how-to-plan-an-event");
			tutorialinfo.add(5,"0r4RBVX1XnY");
			tutorialinfo.add(6,"8kCziFDK3Yk");
			tutorialinfo.add(7,"http://www.wikihow.com/Become-a-Certified-Event-Planner");
			tutorialinfo.add(8,"http://www.wikihow.com/Become-an-Event-Planner");

			covers1 = new int[]{
					R.drawable.event1,
					R.drawable.event2,
					R.drawable.event3,
					R.drawable.event4,
					R.drawable.event5

			};

		}
		else if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref5))){
			tutorialinfo.add(0,getString(R.string.pref5));
			tutorialinfo.add(1,"Tkv8vG5-fVQ");
			tutorialinfo.add(2,"qTV-MdXsEcA");
			tutorialinfo.add(3,"http://www.wikihow.com/Make-a-Greeting-Card");
			tutorialinfo.add(4,"http://www.wikihow.com/Make-Homemade-Candles");
			tutorialinfo.add(5,"BHqikU4rwZ4");
			tutorialinfo.add(6,"ocVfbYcp1Os");
			tutorialinfo.add(7,"http://www.wikihow.com/Make-and-Sell-Greeting-Cards");
			tutorialinfo.add(8,"http://www.wikihow.com/Start-a-Candle-Making-Business");

			covers1 = new int[]{
					R.drawable.card1,
					R.drawable.card2,
					R.drawable.card3,
					R.drawable.card4,
					R.drawable.card5

			};

		}
		else if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref6))){
			tutorialinfo.add(0,getString(R.string.pref6));
			tutorialinfo.add(1,"-3uKqIbhpmo");
			tutorialinfo.add(2,"Av8iBIaG6Nc");
			tutorialinfo.add(3,"http://www.wikihow.com/Category:Crafts");
			tutorialinfo.add(4,"http://www.wikihow.com/Make-a-Crafts-Box");
			tutorialinfo.add(5,"QUcxyhaz7u8");
			tutorialinfo.add(6,"WRbaNcxBtKw");
			tutorialinfo.add(7,"http://www.wikihow.com/Make-Money-Selling-Crafts");
			tutorialinfo.add(8,"http://lifehacker.com/how-can-i-sell-my-homemade-stuff-1440113633");

			covers1 = new int[]{
					R.drawable.craft1,
					R.drawable.craft2,
					R.drawable.craft3,
					R.drawable.craft4,
					R.drawable.craft5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref7))){
			tutorialinfo.add(0,getString(R.string.pref7));
			tutorialinfo.add(1,"sEQdqZm0gNE");
			tutorialinfo.add(2,"H8U-11kw8Ig");
			tutorialinfo.add(3,"http://www.wikihow.com/Become-an-Interior-Decorator");
			tutorialinfo.add(4,"http://www.huffingtonpost.com/phil-good/think-like-an-interior-designer-in-7-steps_b_9753540.html");
			tutorialinfo.add(5,"nj4aadMoOvk");
			tutorialinfo.add(6,"R7lj9UzSJYY");
			tutorialinfo.add(7,"http://www.wikihow.com/Make-a-Portfolio-for-Interior-Design");
			tutorialinfo.add(8,"http://launchpadacademy.in/8-things-to-know-about-becoming-an-interior-designer/");

			covers1 = new int[]{
					R.drawable.inter1,
					R.drawable.inter2,
					R.drawable.inter3,
					R.drawable.inter4,
					R.drawable.inter5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref8))){
			tutorialinfo.add(0,getString(R.string.pref8));
			tutorialinfo.add(1,"9mwj5Ff74is");
			tutorialinfo.add(2,"XYfNwQXSykI");
			tutorialinfo.add(3,"http://www.wikihow.com/Make-Handmade-Jewelry");
			tutorialinfo.add(4,"http://www.how-to-make-jewelry.com/");
			tutorialinfo.add(5,"NCb1PqiXXpc");
			tutorialinfo.add(6,"wfhuZ4jXuQM");
			tutorialinfo.add(7,"http://www.wikihow.com/Sell-Homemade-Jewelry-Online");
			tutorialinfo.add(8,"https://blog.scottsmarketplace.com/selling-handmade-jewelry/");

			covers1 = new int[]{
					R.drawable.jewel1,
					R.drawable.jewel2,
					R.drawable.jewel3,
					R.drawable.jewel4,
					R.drawable.jewel5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref9))){
			tutorialinfo.add(0,getString(R.string.pref9));
			tutorialinfo.add(1,"Mj2WWtllGaU");
			tutorialinfo.add(2,"w7l0tml_BFo");
			tutorialinfo.add(3,"http://www.wikihow.com/Become-a-Maternity-Nurse");
			tutorialinfo.add(4,"http://www.wikihow.com/Become-a-Prenatal-Nurse");
			tutorialinfo.add(5,"ffYlWZiy98I");
			tutorialinfo.add(6,"_aWDPXYUmQk");
			tutorialinfo.add(7,"http://www.wikihow.com/Be-a-Nurse");
			tutorialinfo.add(8,"http://www.childsurvival-india.org/maternal---child-health.html");



		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref10))){
			tutorialinfo.add(0,getString(R.string.pref10));
			tutorialinfo.add(1,"qz-mcZvj_sE");
			tutorialinfo.add(2,"QRQfX7aUPqs");
			tutorialinfo.add(3,"http://www.wikihow.com/Make-Pottery");
			tutorialinfo.add(4,"http://www.howtomakepottery.com/");
			tutorialinfo.add(5,"cgMAAaFFx78");
			tutorialinfo.add(6,"-PBQmN1B70s");
			tutorialinfo.add(7,"https://www.youthkiawaaz.com/2009/12/in-focus-pottery-an-alternate-career-option/");
			tutorialinfo.add(8,"https://ceramicartsnetwork.org/daily/ceramic-art-and-artists/ceramic-artists/a-pottery-paycheck-expert-insights-into-making-a-living-as-a-potter/");

			covers1 = new int[]{
					R.drawable.pottery1,
					R.drawable.pottery2,
					R.drawable.pottery3,
					R.drawable.pottery4,
					R.drawable.pottery5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref11))){
			tutorialinfo.add(0,getString(R.string.pref11));
			tutorialinfo.add(1,"KCgEUwESYZk");
			tutorialinfo.add(2,"q_CaLBKLYLE");
			tutorialinfo.add(3,"http://www.wikihow.com/Sew");
			tutorialinfo.add(4,"http://www.wikihow.com/Knit");
			tutorialinfo.add(5,"eSqPYt4EReA");
			tutorialinfo.add(6,"A_FIJC_JgVM");
			tutorialinfo.add(7,"http://www.wikihow.com/Work-from-Home-Sewing");
			tutorialinfo.add(8,"http://www.sewmyplace.com/how-to-make-money-sewing");

			covers1 = new int[]{
					R.drawable.sew1,
					R.drawable.sew2,
					R.drawable.sew3,
					R.drawable.sew4,
					R.drawable.sew5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref12))){
			tutorialinfo.add(0,getString(R.string.pref12));
			tutorialinfo.add(1,"QoE5Shw5cro");
			tutorialinfo.add(2,"66jMNxcw0A4");
			tutorialinfo.add(3,"http://www.wikihow.com/Apply-Makeup");
			tutorialinfo.add(4,"http://www.wikihow.com/Give-a-Full-Body-Massage");
			tutorialinfo.add(5,"7fceFgnr_Hw");
			tutorialinfo.add(6,"RmXlarIPzZc");
			tutorialinfo.add(7,"http://www.wikihow.com/Start-a-Beauty-Salon");
			tutorialinfo.add(8,"http://business.mapsofindia.com/how-to-start/salon.html");

			covers1 = new int[]{
					R.drawable.spa1,
					R.drawable.spa2,
					R.drawable.spa3,
					R.drawable.spa4,
					R.drawable.spa5

			};

		}
		if(userPreferences.getPREFERENCE_HOBBY().toString().equalsIgnoreCase(getString(R.string.pref13))){
			tutorialinfo.add(0,getString(R.string.pref13));
			tutorialinfo.add(1,"VfK7tfDCSIk");
			tutorialinfo.add(2,"QW7S_R9vEkc");
			tutorialinfo.add(3,"http://www.wikihow.com/Teach");
			tutorialinfo.add(4,"https://www.realsimple.com/work-life/family/kids-parenting/teachers-tips-handle-children");
			tutorialinfo.add(5,"PFzY1Gm23lc");
			tutorialinfo.add(6,"Bcy7qlQ9THQ");
			tutorialinfo.add(7,"http://www.wikihow.com/Set-up-a-Home-Tutoring-Business");
			tutorialinfo.add(8,"http://www.naaree.com/start-tuition-centre-home-tutoring-service/");

			covers1 = new int[]{
					R.drawable.sub1,
					R.drawable.sub2,
					R.drawable.sub3,
					R.drawable.sub4,
					R.drawable.sub5

			};

		}

		else{

			tutorialinfo.add(0,getString(R.string.pref13));
			tutorialinfo.add(1,"VfK7tfDCSIk");
			tutorialinfo.add(2,"QW7S_R9vEkc");
			tutorialinfo.add(3,"http://www.wikihow.com/Teach");
			tutorialinfo.add(4,"https://www.realsimple.com/work-life/family/kids-parenting/teachers-tips-handle-children");
			tutorialinfo.add(5,"PFzY1Gm23lc");
			tutorialinfo.add(6,"Bcy7qlQ9THQ");
			tutorialinfo.add(7,"http://www.wikihow.com/Set-up-a-Home-Tutoring-Business");
			tutorialinfo.add(8,"http://www.naaree.com/start-tuition-centre-home-tutoring-service/");

			covers1 = new int[]{
					R.drawable.sub1,
					R.drawable.sub2,
					R.drawable.sub3,
					R.drawable.sub4,
					R.drawable.sub5

			};

		}
      /*if(userPreferences.getPREFERENCE_HOBBY().toString().equals(getString(R.string.pref5a))){
         tutorialinfo.add(0,getString(R.string.pref5a));
         tutorialinfo.add(1,"VfK7tfDCSIk");
         tutorialinfo.add(2,"Bu3ulVhO3z4");
         tutorialinfo.add(3,"http://www.wikihow.com/Become-an-Artist");
         tutorialinfo.add(4,"http://www.wikihow.com/Paint");
         tutorialinfo.add(5,"6ky_zdx0f64");
         tutorialinfo.add(6,"7GPXGHjeDHI");
         tutorialinfo.add(7,"http://paintingbusinesspro.com/how-to-start-a-painting-business-from-scratch/");
         tutorialinfo.add(8,"http://emptyeasel.com/2010/02/23/10-steps-for-creating-a-successful-business-from-your-art/");

         covers1 = new int[]{
               R.drawable.a1,
               R.drawable.a2,
               R.drawable.a3,
               R.drawable.a4,
               R.drawable.a5

         };

      }*/

		prepareAlbums();





		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				prepareAlbums();
				handler.postDelayed(this, 12000); //now is every 2 minutes
			}
		}, 12000); //Every 120000 ms (2 minutes)

		// Create an `ItemTouchHelper` and attach it to the `RecyclerView`
      /*ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
      ith.attachToRecyclerView(recyclerView);*/

		recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
			@Override
			public void onClick(final View view, int position) {


				try {

					if (position == 0) {
						//Toast.makeText(MSUHome.this, "000", Toast.LENGTH_LONG).show();
						//noCurrentComp(view);
					} else if (position == 1) {
						//Toast.makeText(MSUHome.this, "111", Toast.LENGTH_LONG).show();
						//myAccount(view);
						String link = tutorialinfo.get(1);
						Intent intent = new Intent(getActivity(),Youtube.class);
						intent.putExtra("video",link);
						startActivity(intent);


					} else if (position == 2) {
						//Toast.makeText(MSUHome.this, "222", Toast.LENGTH_LONG).show();
						//streetLightComp(view);
						String link = tutorialinfo.get(2);
						Intent intent = new Intent(getActivity(),Youtube.class);
						intent.putExtra("video",link);
						startActivity(intent);

					} else if (position == 3) {
						//Toast.makeText(MSUHome.this, "333", Toast.LENGTH_LONG).show();
						// dssActivities(view);

						dialog.show();
						dialog.setMessage("Loading..");

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								dialog.dismiss();
							}
						}, 3000); // 3000 milliseconds delay

						Bundle bundle=new Bundle();
						bundle.putString("link", tutorialinfo.get(3));

						Fragment wv = new Web_View();
						wv.setArguments(bundle);

						getFragmentManager().beginTransaction().add(R.id.qw,wv,"WV").addToBackStack("WV").commit();
					}

					else if (position == 4) {
						//Toast.makeText(MSUHome.this, "444", Toast.LENGTH_LONG).show();
						//feedback(view);

						dialog.show();
						dialog.setMessage("Loading..");

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								dialog.dismiss();
							}
						}, 3000); // 3000 milliseconds delay


						Bundle bundle=new Bundle();
						bundle.putString("link", tutorialinfo.get(4));
						Fragment wv = new Web_View();
						wv.setArguments(bundle);

						getFragmentManager().beginTransaction().add(R.id.qw,wv,"WV").addToBackStack("WV").commit();
					}

					else if (position == 5) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						// instantPayment(view);
					}

					else if (position == 6) {
						//Toast.makeText(MSUHome.this, "666", Toast.LENGTH_LONG).show();
//						iv2 = (ImageView) view.findViewById(R.id.cardlogo2);
//						iv1 = (ImageView) view.findViewById(R.id.cardlogo1);
//						iv3 = (ImageView) view.findViewById(R.id.cardlogo3);
//
//						iv1.setVisibility(view.VISIBLE);
//						iv3.setVisibility(view.VISIBLE);
//
//						Animation anim = new AlphaAnimation(0.50f, 1.0f);
//						anim.setDuration(1000); // You can manage the blinking time
//						// with this parameter
//						anim.setStartOffset(20);
//						anim.setRepeatMode(Animation.REVERSE);
//						anim.setRepeatCount(Animation.INFINITE);
//						iv1.startAnimation(anim);
//						iv3.startAnimation(anim);
//
//						iv2.setImageResource(R.drawable.call_blue);

						String link = tutorialinfo.get(5);
						Intent intent = new Intent(getActivity(),Youtube.class);
						intent.putExtra("video",link);
						startActivity(intent);



                  /*iv2.setOnDragListener(new View.OnDragListener() {
                     @Override
                     public boolean onDrag(View view, DragEvent dragEvent) {
                        String uri = "@drawable/call_red";  // where myresource (without the extension) is the file

                        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                        Drawable res = getResources().getDrawable(imageResource);
                        iv2.setImageDrawable(res);

                        return false;
                     }
                  });*/


                  /*iv2.setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        iv1.setVisibility(view.VISIBLE);
                        iv3.setVisibility(view.VISIBLE);
                     }
                  });*/


               /* iv2.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

                     public void onSwipeTop() {
                        Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                     }
                     public void onSwipeRight() {
                        Toast.makeText(getActivity(), "Calling BRPL", Toast.LENGTH_SHORT).show();
                        iv3.setVisibility(view.VISIBLE);
                        //callBRPL(view);
                     }
                     public void onSwipeLeft() {
                        Toast.makeText(getActivity(), "Calling BYPL", Toast.LENGTH_SHORT).show();
                        //callBYPL(view);
                     }
                     public void onSwipeBottom() {
                        Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
                     }

                  });*/
						//Toast.makeText(MSUHome.this, "777", Toast.LENGTH_LONG).show();
						//arun(view);

					}

					else if (position == 7) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						//callingWebpage(view, "http://www.bsesdelhi.com/HTML/wb_bsesataglance.html");
						String link = tutorialinfo.get(6);
						Intent intent = new Intent(getActivity(),Youtube.class);
						intent.putExtra("video",link);
						startActivity(intent);
					}

					else if (position == 8) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						//callingWebpage(view, "http://www.bsesdelhi.com/HTML/wb_energyconservation.html");

						dialog.show();
						dialog.setMessage("Loading..");

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								dialog.dismiss();
							}
						}, 3000); // 3000 milliseconds delay


						Bundle bundle=new Bundle();
						bundle.putString("link", tutorialinfo.get(7));

						Fragment wv = new Web_View();
						wv.setArguments(bundle);

						getFragmentManager().beginTransaction().add(R.id.qw,wv,"WV").addToBackStack("WV").commit();
					}

					else if (position == 9) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						//callingWebpage(view, "http://www.bsesdelhi.com/HTML/wb_safety.html");

						dialog.show();
						dialog.setMessage("Loading..");

						Handler handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run() {
								dialog.dismiss();
							}
						}, 3000); // 3000 milliseconds delay


						Bundle bundle=new Bundle();
						bundle.putString("link", tutorialinfo.get(8));

						Fragment wv = new Web_View();
						wv.setArguments(bundle);

						getFragmentManager().beginTransaction().add(R.id.qw,wv,"WV").addToBackStack("WV").commit();
					}

					else if (position == 10) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						//callingWebpage(view, "http://www.bsesdelhi.com/HTML/wb_energycalculator.html");
					}

					else if (position == 11) {
						//Toast.makeText(MSUHome.this, "555", Toast.LENGTH_LONG).show();
						//callingWebpage(view, "http://www.bsesdelhi.com/HTML/wb_dosndonts.html");

						yes = (TextView) view.findViewById(R.id.title);
						no = (TextView) view.findViewById(R.id.count);
						done = (ImageView) view.findViewById(R.id.overflow);


						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg1 = "YES";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG1("DONE");

							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg1 = "NO";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG1("DONE");
							}
						});



					}

					else if (position == 12) {

						yes = (TextView) view.findViewById(R.id.title);
						no = (TextView) view.findViewById(R.id.count);
						done = (ImageView) view.findViewById(R.id.overflow);


						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg2 = "YES";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG2("DONE");

							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg2 = "NO";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG2("DONE");
							}
						});


						//Toast.makeText(MSUHome.this, "777", Toast.LENGTH_LONG).show();
						//arun(view);
						//dfdf
//                if (!otherLinkToggle) {
//                   Album ar = new Album("ARUN", "Advertise", 0, 0, 0, 0, 0);
//                   albumList.add(ar);
//
//                   Album nm = new Album("Net Metering", "Advertise", 0, 0, 0, 0, 0);
//                   albumList.add(nm);
//
//                   compundAdapter.notifyDataSetChanged();
//                   recyclerView.smoothScrollToPosition(compundAdapter.getItemCount());
//                   otherLinkToggle = true;
//                }
					}

					else if (position == 13) {

						yes = (TextView) view.findViewById(R.id.title);
						no = (TextView) view.findViewById(R.id.count);
						done = (ImageView) view.findViewById(R.id.overflow);


						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg3 = "YES";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG3("DONE");

							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg3 = "NO";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG3("DONE");
							}
						});

						//Toast.makeText(MSUHome.this, "777", Toast.LENGTH_LONG).show();
						//arun(view);
					}

					else if (position == 14) {
						// netMeter(view);

						yes = (TextView) view.findViewById(R.id.title);
						no = (TextView) view.findViewById(R.id.count);
						done = (ImageView) view.findViewById(R.id.overflow);


						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg4 = "YES";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG4("DONE");

							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg4 = "NO";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG4("DONE");
							}
						});
					}


					else if (position == 15) {
						// netMeter(view);

						yes = (TextView) view.findViewById(R.id.title);
						no = (TextView) view.findViewById(R.id.count);
						done = (ImageView) view.findViewById(R.id.overflow);


						yes.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg5 = "YES";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG5("DONE");

							}
						});
						no.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								canYouDoImg5 = "NO";
								done.setVisibility(v.VISIBLE);
								yes.setVisibility(v.GONE);
								no.setVisibility(v.GONE);

								userPreferences.setCANYOUDOIMG5("DONE");
							}
						});
					}

					else {
						if (position == 0) {

							//Toast.makeText(MSUHome.this, "zzz", Toast.LENGTH_LONG).show();
						}
					}

				}

				catch (Exception er) {
					er.printStackTrace();
				}
			}

			@Override
			public void onLongClick(View view, int position) {

			}

		}));

		try {
			// Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
		} catch (Exception e) {
			e.printStackTrace();
		}


		BottomNavigationView bottomNavigationView = (BottomNavigationView)
				rootView.findViewById(R.id.navigation);
		//bottomNavigationView.setKeepScreenOn(true);
		bottomNavigationView.setSelected(false);

		bottomNavigationView.setOnNavigationItemSelectedListener
				(new BottomNavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem item) {

						switch (item.getItemId()) {

							case R.id.action_item2:

								facebook(item.getActionView());
								break;
							case R.id.action_item3:
								//selectedFragment = ItemThreeFragment.newInstance();
								twitter(item.getActionView());
								break;


							case R.id.action_item5:
								//selectedFragment = ItemThreeFragment.newInstance();
								shareApp();
								break;


						}
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                  transaction.replace(R.id.frame_layout, selectedFragment);
                  transaction.commit();*/
						return true;
					}
				});


   /* swipe = new Swipe();
      //Swipe(int swipingThreshold, int swipedThreshold);
      swipe.setListener(new SwipeListener() {
         @Override public void onSwipingLeft(final MotionEvent event) {
            info.setText("SWIPING_LEFT");
         }

         @Override public void onSwipedLeft(final MotionEvent event) {
            info.setText("SWIPED_LEFT");
         }

         @Override public void onSwipingRight(final MotionEvent event) {
            info.setText("SWIPING_RIGHT");
         }

         @Override public void onSwipedRight(final MotionEvent event) {
            info.setText("SWIPED_RIGHT");
         }

         @Override public void onSwipingUp(final MotionEvent event) {
            info.setText("SWIPING_UP");
         }

         @Override public void onSwipedUp(final MotionEvent event) {
            info.setText("SWIPED_UP");
         }

         @Override public void onSwipingDown(final MotionEvent event) {
            info.setText("SWIPING_DOWN");
         }

         @Override public void onSwipedDown(final MotionEvent event) {
            info.setText("SWIPED_DOWN");
         }
      });*/




   /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
      }*/

		//LinearLayout orderBtn = (LinearLayout) findViewById(R.id.viewOrderBtn);
		//LinearLayout billBtn = (LinearLayout) findViewById(R.id.viewBillBtn);
		//feedbackBtn = (LinearLayout) findViewById(R.id.feedback);
		//LinearLayout instantPayment = (LinearLayout) findViewById(R.id.empverification);

		//strLightReg = (LinearLayout) findViewById(R.id.strLightReg);
		//;
		//reachUsNew = (LinearLayout) findViewById(R.id.reachUsNew);

		// without employee verification
		// empverification.setVisibility(View.GONE);

		// customercare = (LinearLayout)findViewById(R.id.customerCareBoth);

		//LinearLayout complainBtn = (LinearLayout) findViewById(R.id.complainBtn);
		//Button exitBtn = (Button) findViewById(R.id.exitBtn);

		//musicButton = (Button) findViewById(R.id.music);

		//sharedPreferences = getSharedPreferences(ApplicationConstants.DATABASE,Context.MODE_PRIVATE);
		//String data = sharedPreferences.getString(ApplicationConstants.MUSICDATA, "");

		//System.out.println("music option :" + data.toString());

      /*if (data.equalsIgnoreCase("on") || data.equalsIgnoreCase("")) {
         musicButton.setBackgroundResource(R.drawable.music_on);
      } else {
         musicButton.setBackgroundResource(R.drawable.music_off);
      }*/



   /* musicButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View arg0) {
            // TODO Auto-generated method stub
            SharedPreferences sharedPreferences = getSharedPreferences(
                  ApplicationConstants.DATABASE, Context.MODE_PRIVATE);
            System.out.println("..................clicked");
            String music = sharedPreferences.getString(
                  ApplicationConstants.MUSICDATA, "");
            if (music.equalsIgnoreCase("on") || music.equalsIgnoreCase("")) {
               musicButton.setBackgroundResource(R.drawable.music_off);
               editor = sharedPreferences.edit();
               editor.putString(ApplicationConstants.MUSICDATA, "off");
               editor.commit();
               stopAudio();
            } else {
               musicButton.setBackgroundResource(R.drawable.music_on);
               editor = sharedPreferences.edit();
               editor.putString(ApplicationConstants.MUSICDATA, "on");
               editor.commit();
               // playAudio();
            }
         }
      });*/

		shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
		animFadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
		animFadein.setAnimationListener(this);
      /*redBulb = (LinearLayout) findViewById(R.id.complainBtn);
      redBulb.setVisibility(View.VISIBLE);*/
		// start the animation

		slide_in_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
		slide_in_right.setAnimationListener(this);

      /*Handler handlerRedBulb = new Handler();
      handlerRedBulb.postDelayed(new Runnable() {

         @Override
         public void run() {

         }
      }, 500);*/


		return rootView;

	}




	public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

		private GestureDetector gestureDetector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context, final RecyclerView recyclerEmpView, final ClickListener clickListener) {
			this.clickListener = clickListener;
			gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					return true;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					View child = recyclerEmpView.findChildViewUnder(e.getX(), e.getY());
					if (child != null && clickListener != null) {
						clickListener.onLongClick(child, recyclerEmpView.getChildPosition(child));
					}
				}
			});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {
		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

		}
	}


	public interface ClickListener {
		void onClick(View view, int position);
		void onLongClick(View view, int position);
	}


	/**
	 * Initializing collapsing toolbar
	 * Will show and hide the toolbar title on scroll
	 */
// private void initCollapsingToolbar() {
//    final CollapsingToolbarLayout collapsingToolbar =(CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
//    collapsingToolbar.setTitle(" ");
//    AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
//
//    final BottomNavigationView bottomNavigationView = (BottomNavigationView) rootView.findViewById(R.id.navigation);
//
//    //appBarLayout.setExpanded(true);
//
//    // hiding & showing the title when toolbar expanded & collapsed
//    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//       boolean isShow = false;
//       int scrollRange = -1;
//
//       @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//       @Override
//       public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//          Window window = getActivity().getWindow();
//
//          // clear FLAG_TRANSLUCENT_STATUS flag:
//          window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//          // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
//          window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//          // finally change the color
//
//
//          if (scrollRange == -1) {
//             scrollRange = appBarLayout.getTotalScrollRange();
//             //bottomNavigationView.setBackground(ContextCompat.getDrawable(MSUHome.this,R.drawable.border_shadow));
//          }
//          if (scrollRange + verticalOffset == 0) {
//             collapsingToolbar.setTitle(getString(R.string.app_name));
//
//             if(Build.VERSION.SDK_INT >=21) {
//                window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//             }
//             bottomNavigationView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_shadow));
//             isShow = true;
//          }
//          else if (isShow) {
//             collapsingToolbar.setTitle(" ");
//             if(Build.VERSION.SDK_INT >=21) {
//
//                window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//             }
//
//             bottomNavigationView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_shadow));
//
//
//             isShow = false;
//          }
//       }
//    });
// }


   /*@Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.bottom_navigation_items, menu);
      MenuItem item = menu.getItem(0); //here we are getting our menu item.
      SpannableString s = new SpannableString(item.getTitle()); //get text from our menu item.
      s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0); //here I am just setting a custom color to the menu item. leave this out if you want it left at black.
      s.setSpan(new RelativeSizeSpan(.7f),0,s.length(),0); //here is where we are actually setting the size with a float (proportion).
      item.setTitle(s); //Then just set the menu item to our SpannableString.

      return true;
   }*/

	/**
	 * Adding few albums for testing
	 */
	private void prepareAlbums() {

		albumList.clear();
		int[] covers = new int[]{
				R.drawable.video_icon,
				R.drawable.web_icon,
				R.drawable.correct_green
		};

		Album a = new Album("", getString(R.string.allaboutwork), covers[0],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.videotut1), tutorialinfo.get(1), covers[0],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.videotut2), tutorialinfo.get(2), covers[0],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.webtut1),tutorialinfo.get(3), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.webtut2), tutorialinfo.get(4), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album("", getString(R.string.howtoestab), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.videotut1), tutorialinfo.get(5),covers[0],covers[0],covers[0],covers[0],covers[0],0);
		albumList.add(a);

		a = new Album(getString(R.string.videotut2), tutorialinfo.get(6), covers[0],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.webtut1), tutorialinfo.get(7), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.webtut2), tutorialinfo.get(8), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album("", getString(R.string.canyoudo), covers[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.yes), getString(R.string.no), covers1[1],0,0,0,0,0);
		albumList.add(a);

		a = new Album(getString(R.string.yes), getString(R.string.no), covers1[2],0,0,0,0,0);
		albumList.add(a);


		//if(otherLinkToggle) {
		a = new Album(getString(R.string.yes), getString(R.string.no), covers1[3], 0, 0, 0, 0,0);
		albumList.add(a);

		a = new Album(getString(R.string.yes),getString(R.string.no), covers1[4], 0, 0, 0, 0,0);
		albumList.add(a);

		a = new Album(getString(R.string.yes), getString(R.string.no), covers1[0], 0, 0, 0, 0,0);
		albumList.add(a);
		//}


      /*a = new Album("Hello", 11, covers[8]);
      albumList.add(a);

      a = new Album("Greatest Hits", 17, covers[9]);
      albumList.add(a);*/
		compundAdapter.notifyDataSetChanged();


	}

	/**
	 * RecyclerView item decoration - give equal margin around grid item
	 */
	public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

		private int spanCount;
		private int spacing;
		private boolean includeEdge;

		public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
			this.spanCount = spanCount;
			this.spacing = spacing;
			this.includeEdge = includeEdge;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int position = parent.getChildAdapterPosition(view); // item position
			int column = position % spanCount; // item column

			if (includeEdge) {
				outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
				outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

				if (position < spanCount) { // top edge
					outRect.top = spacing;
				}
				outRect.bottom = spacing; // item bottom
			} else {
				outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
				outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
				if (position >= spanCount) {
					outRect.top = spacing; // item top
				}
			}
		}
	}

	/**
	 * Converting dp to pixel
	 */
	private int dpToPx(int dp) {
		Resources r = getResources();
		return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
	}




	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}







	// method to get the right URL to use in the intent

	public String getFacebookPageURL(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			int versionCode = packageManager.getPackageInfo(
					"com.facebook.katana", 0).versionCode;
			if (versionCode >= 3002850) { // newer versions of fb app
				return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
			} else { // older versions of fb app
				return "fb://page/" + FACEBOOK_PAGE_ID;
			}
		} catch (PackageManager.NameNotFoundException e) {
			return FACEBOOK_URL; // normal web url
		}
	}

	public void openTwitter(Context context) {

		try {
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
		} catch (Exception e) {
			// startActivity(new Intent(Intent.ACTION_VIEW,
			// Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://twitter.com/" + twitter_user_name)));
		}

	}

	// call functions start here



	private void shareApp(){

		try {
			// v.startAnimation(shake);
			// final String appPackageName = "com.bses.bsesapp&hl=en";

			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_SUBJECT, "Rajasthan MSU Application");
			String sAux = "\nHi! I am using the Rajasthan's MSU App, check this out!\n\n";
			sAux = sAux + "https://play.google.com/store/apps/details?id=com.risl.bhamashah&hl=en \n\n";
			i.putExtra(Intent.EXTRA_TEXT, sAux);

      /* final String appPackageName = "nicmnre.nicspvapplication&hl=en"; // getPackageName() from Context or Activity object
         try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
         } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
         }
         */
			startActivity(Intent.createChooser(i, "choose one"));
		} catch(Exception e) {
			//e.toString();
		}


	}



	private void facebook(View v){
		//v.startAnimation(shake);

		Intent facebookIntent = new Intent(
				Intent.ACTION_VIEW);
		String facebookUrl = getFacebookPageURL(getActivity());
		facebookIntent.setData(Uri.parse(facebookUrl));
		startActivity(facebookIntent);
	}

	private void twitter(View v){
		//	v.startAnimation(shake);

		openTwitter(getActivity());
	}

	// Extend the Callback class
   /*ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
      //and in your imlpementaion of
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
         // get the viewHolder's and target's positions in your adapter data, swap them
         Collections.swap(albumList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
         // and notify the adapter that its dataset has changed
         compundAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
         return true;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
         //TODO
      }

      //defines the enabled move directions in each state (idle, swiping, dragging).
      @Override
      public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
         return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
               ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
      }
   };*/

}