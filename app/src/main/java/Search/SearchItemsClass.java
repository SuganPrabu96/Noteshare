package Search;

/**
 * Created by Suganprabu on 03-07-2015.
 */
public class SearchItemsClass {

    private int views, downloads;

    public SearchItemsClass(int views, int downloads){

        this.views = views;
        this.downloads = downloads;

    }

    public int getViews(){ return views; }

    public int getDownloads(){ return downloads; }
}
