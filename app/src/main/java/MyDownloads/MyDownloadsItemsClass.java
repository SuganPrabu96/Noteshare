package MyDownloads;

/**
 * Created by Suganprabu on 03-07-2015.
 */
public class MyDownloadsItemsClass {

    private int views, downloads;

    public MyDownloadsItemsClass(int views, int downloads){

        this.views = views;
        this.downloads = downloads;

    }

    public int getViews(){ return views; }

    public int getDownloads(){ return downloads; }
}
