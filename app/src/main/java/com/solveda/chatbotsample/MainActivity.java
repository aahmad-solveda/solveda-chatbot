package com.solveda.chatbotsample;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.solveda.watsonchatbot.ChatBotActivity;
import com.solveda.watsonchatbot.messages.MessageData;
import com.solveda.watsonchatbot.messages.MessageInput;
import com.solveda.watsonchatbot.messages.MessagesList;

import java.util.Random;

public class MainActivity extends ChatBotActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chatbot");
        try
        {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),     PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String url = bundle.getString("watson_url");
            //Toast.makeText(this,myApiKey,Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        this.input = (MessageInput) findViewById(R.id.input);
        this.btnScrollToEnd = (FloatingActionButton)findViewById(R.id.btnScrollToEnd);
        init("http://192.168.1.2:8094/api/message");
        //init("http://ayurvedabot.eu-gb.mybluemix.net/api/message");
        findViewById(R.id.errorView).setVisibility(View.GONE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isInternetAvailable())
        {
            findViewById(R.id.errorView).setVisibility(View.VISIBLE);
            messagesList.setVisibility(View.GONE);
            input.setVisibility(View.GONE);

        }

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    Random random =new Random();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;

            MessageData messageData=new MessageData();
            messageData.setBotMessage(true);
            messageData.setMessage("Now we need to add code in our library. Create a new Class in your module and name that class Toaster Message;");
            messageData.setMessageType(MessageData.TYPE_PRODUCT);
            messageData.setDateTime("12:10");
            adapter.addToStart(messageData,true);
        }
        return super.onOptionsItemSelected(item);
    }
}
