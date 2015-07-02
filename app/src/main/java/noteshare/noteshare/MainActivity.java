package noteshare.noteshare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.List;

import NavigationDrawer.NavDrawerItem;
import NavigationDrawer.NavDrawerListAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import util.data;


public class MainActivity extends ActionBarActivity {

    //private Button btnTakePhoto, btnChooseExisting;
    private ProfilePictureView facebookProfileIcon;
    private CircleImageView googleProfileIcon;
    private ImageView profileIcon;
    private TextView profileIconText;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavDrawerListAdapter navDrawerListAdapter;
    private String modeOfLogin = "App"; //TODO edit this

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        getSupportActionBar().hide();

        facebookProfileIcon = (ProfilePictureView) findViewById(R.id.profilepic_facebook);
        profileIconText = (TextView) findViewById(R.id.profilepic_name);
        facebookProfileIcon.setCropped(true);
        profileIcon = (ImageView) findViewById(R.id.profilepic);
        googleProfileIcon = (CircleImageView) findViewById(R.id.profilepic_google);

        if (modeOfLogin.equals("Facebook")) {
            profileIcon.setVisibility(View.INVISIBLE);
            googleProfileIcon.setVisibility((View.INVISIBLE));
            facebookProfileIcon.setVisibility(View.VISIBLE);
        } else if (modeOfLogin.equals("Google")) {
            //googleProfileIcon.setImageBitmap(LoginActivity.bmImage);
            //profileIconText.setText(LoginActivity.profileText);
            profileIcon.setVisibility(View.INVISIBLE);
            facebookProfileIcon.setVisibility(View.INVISIBLE);
            googleProfileIcon.setVisibility(View.VISIBLE);

        } else {
            facebookProfileIcon.setVisibility(View.INVISIBLE);
            googleProfileIcon.setVisibility(View.INVISIBLE);
            profileIcon.setVisibility(View.VISIBLE);
           // profileIconText.setText(LoginActivity.prefs.getString("Email", ""));
        }

        List<NavDrawerItem> datalist = data.getNavDrawerItems();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_container, new MyProfileFragment());
        fragmentTransaction.commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(), datalist);

        drawerList.setAdapter(navDrawerListAdapter);
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,       /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //       setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //      setTitle(mTitle);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navDrawerListAdapter.setSelectedItem(0);

        /*btnTakePhoto = (Button) findViewById(R.id.button_new_photo);
        btnChooseExisting = (Button) findViewById(R.id.button_file_browser);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TakePhoto.class));
            }
        });

        btnChooseExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FileBrowser.class));
            }
        });*/
    }

    private static void openDrawer(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            System.out.println(position);
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        // Insert the fragment by replacing any existing fragment
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment f = fragmentManager.findFragmentById(R.id.frame_container);
        //if(fragmentTransaction.r))
        if (position != 0) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(0, 0, 0);
            getSupportFragmentManager().findFragmentById(R.id.frame_container)
                    .getView()
                    .setLayoutParams(lp);
        }

        navDrawerListAdapter.setSelectedItem(position);

        if (position == 0) {
            fragmentTransaction.replace(R.id.frame_container, new MyProfileFragment(), "MyProfileFragment");
        } else if (position == 1) {
            fragmentTransaction.replace(R.id.frame_container, new MyUploadsFragment(), "MyUploadsFragment");
        } else if (position == 2) {
            fragmentTransaction.replace(R.id.frame_container, new MyDownloadsFragment(), "MyDownloadsFragment");
        } else if (position == 3) {
            final AlertDialog.Builder logoutAlert = new AlertDialog.Builder(MainActivity.this);

            logoutAlert.setCancelable(false);

            logoutAlert.setMessage("Are you sure");
            logoutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(final DialogInterface dialog, int which) {

                    //TODO logout
                };

            });
            logoutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            logoutAlert.create().show();

            if (f instanceof MyProfileFragment) {
                fragmentTransaction.replace(R.id.frame_container, new MyProfileFragment());

            } else if (f instanceof MyUploadsFragment) {
                fragmentTransaction.replace(R.id.frame_container, new MyUploadsFragment());

            } else if (f instanceof MyDownloadsFragment) {
                fragmentTransaction.replace(R.id.frame_container, new MyDownloadsFragment());

            }
        }

        fragmentTransaction.commitAllowingStateLoss();
        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        if (position != 3) {
            for (int i = 0; i < drawerList.getChildCount(); i++)
                drawerList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.NavigationBarUnselectedItem));
            drawerList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.NavigationBarSelectedItem));

        } else
            for (int i = 0; i < drawerList.getChildCount(); i++)
                drawerList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.NavigationBarUnselectedItem));
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public static class MyProfileFragment extends Fragment {

        public MyProfileFragment() {
        }

        private ImageView drawer, notifBell;
        private TextView uploadsTV, downloadsTV, creditsTV, followersTV, followingTV;
        private BadgeView badge;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

            drawer = (ImageView) rootView.findViewById(R.id.ic_drawer);
            notifBell = (ImageView) rootView.findViewById(R.id.my_page_notif);
            badge = new BadgeView(rootView.getContext(), notifBell);

            badge.setText("1");
            badge.setBadgePosition(badge.POSITION_TOP_RIGHT);
            badge.show();

            drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.openDrawer();
                    Log.d("Clicked", "True");
                }
            });

            return rootView;
        }

    }

    public static class MyUploadsFragment extends Fragment {

        public MyUploadsFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_my_uploads, container, false);


            return rootView;
        }

    }

    public static class MyDownloadsFragment extends Fragment {

        public MyDownloadsFragment() {
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_my_downloads, container, false);


            return rootView;
        }

    }

}
