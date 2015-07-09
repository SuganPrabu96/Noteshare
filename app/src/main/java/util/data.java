package util;

import java.util.Arrays;
import java.util.List;

import NavigationDrawer.NavDrawerItem;
import noteshare.noteshare.R;

public class data {
    public static NavDrawerItem[] navtitles={
            new NavDrawerItem("My Profile", R.drawable.ic_launcher),
            new NavDrawerItem("My Uploads", R.drawable.ic_launcher),
            new NavDrawerItem("My Downloads", R.drawable.ic_launcher),
            new NavDrawerItem("Search", R.drawable.ic_launcher),
            new NavDrawerItem("Logout", R.drawable.ic_launcher)
    };
    public static List<NavDrawerItem> getNavDrawerItems(){
        return Arrays.asList(navtitles);
    }
}
