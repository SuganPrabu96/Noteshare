package noteshare.noteshare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FileUpload extends ActionBarActivity {

    private final String fileServerURL = "http://noteshare.in";
    private Button btnFileUpload;
    private TextView uploadFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        btnFileUpload = (Button) findViewById(R.id.buttonUploadFile);
        uploadFileName = (TextView) findViewById(R.id.uploadFile_fileName);

        final Intent intent = getIntent();
        final String action = intent.getAction();
        final String type = intent.getType();

       // Log.i("action",action);
       // Log.i("type",type);

        uploadFileName.setText(intent.getExtras().getString("fileName"));

        btnFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type!=null && action.equals(Intent.ACTION_SEND)){
                    Log.i("fileLocation",intent.getExtras().getString("filePath"));
                    new FileUploadAsync().execute(new File(String.valueOf(intent.getExtras().getString("filePath"))));
                }
                Log.i("fileLocation",intent.getExtras().getString("filePath"));
                new FileUploadAsync().execute(new File(String.valueOf(intent.getExtras().getString("filePath"))));
            }
        });
    }



    private class FileUploadAsync extends AsyncTask<File,Void,Void>{

        private boolean fileUploadSuccess = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(File... f){

            Log.i("FileUploadAsync","Inside doInBackground");

            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(fileServerURL);

                try {

                    InputStreamEntity entity = new InputStreamEntity(new FileInputStream(String.valueOf(f[0])),-1);
                    entity.setContentType("binary/octet-stream");
                    entity.setChunked(true);

                    httpPost.setEntity(entity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    String response = EntityUtils.toString(httpResponse.getEntity());

                    Log.i("response", response);

                    if(response.equals("awesome"))
                        fileUploadSuccess=true;
                    else
                        fileUploadSuccess=false;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            Log.i("fileUploadAsync","Inside onPostExecute");
            Log.i("fileUploadSuccess", String.valueOf(fileUploadSuccess));

            if(fileUploadSuccess){
                Toast.makeText(getApplicationContext(),"File uploaded successfully",Toast.LENGTH_SHORT).show();
            }

            else if(!fileUploadSuccess){
                Toast.makeText(getApplicationContext(),"File not uploaded",Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_upload, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
