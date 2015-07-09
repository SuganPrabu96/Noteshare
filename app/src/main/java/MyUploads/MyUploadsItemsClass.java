package MyUploads;

/**
 * Created by Suganprabu on 03-07-2015.
 */
public class MyUploadsItemsClass {

    private int views, downloads;

    public MyUploadsItemsClass(int views, int downloads){

        this.views = views;
        this.downloads = downloads;

    }

    public int getViews(){ return views; }

    public int getDownloads(){ return downloads; }
}
