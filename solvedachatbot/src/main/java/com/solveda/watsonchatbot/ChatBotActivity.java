package com.solveda.watsonchatbot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.solveda.watsonchatbot.messages.MessageAdapter;
import com.solveda.watsonchatbot.messages.MessageData;
import com.solveda.watsonchatbot.messages.MessageInput;
import com.solveda.watsonchatbot.messages.MessagesList;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ChatBotActivity extends AppCompatActivity implements
        MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        RecognitionListener,
        MessageInput.AudioListener,
        MessagesList.ItemClick
{

    String [] message = {"The real estate chatbot template is ready to go for Realtors.",
                        "Automation basics free you up to keep growing your business.",
                        "The welcome page lets new clients segment themselves by what they’re looking for (to buy, rent or sell).",
                        "You can use contact forms that let interested people let you know their phone number and email where they want to be contacted and their preference.",
                        "Or, deliver inquiry forms for buyers, sellers and renters each collect data like where they’re looking, what they’re looking for, bedrooms and budget.",
                        "“Meet the Team” pages to showcase your personality and experience and give people a way to contact the agent directly.",
                        "The lead generation chatbot template is perfect for collecting contact information in exchange for things like webinar tickets or downloads.",
                        "Whenever the chatbot gets a new contact, you’ll get an email notification so you can follow up with your fresh lead!"};

    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;

    protected MessageInput input;
    protected MessageAdapter adapter;
    protected MessagesList messagesList;
    protected FloatingActionButton btnScrollToEnd;


    public void init()
    {
        initMessageAdapter();
        input.setInputListener(this);
        //input.setAttachmentsListener(this);
        input.setAudioListener(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    finish();

                }
                else {
                    // No explanation needed; request the permission
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
            else
            {

            }
        }
    }

    private void initMessageAdapter()
    {
        adapter =new MessageAdapter();
        messagesList.setAdapter(adapter);
        adapter.setItemClick(this);
        btnScrollToEnd.setEnabled( false);
        btnScrollToEnd.hide();
        messagesList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.getItemCount()<=8 ||  messagesList.getLayoutManager().findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 5)
                {
                    btnScrollToEnd.setEnabled( false);
                    btnScrollToEnd.hide();
                }
                else {
                    btnScrollToEnd.setEnabled( true);
                    btnScrollToEnd.show();

                }
            }
        });
    }

    //-----------------------RecognitionListener-------------------------

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        //displaying the first match
        if (matches != null)
            input.setText(matches.get(0));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    //-----------------------RecognitionListener-------------------------

    Random random=new Random();
    @Override
    public boolean onSubmit(CharSequence input) {
        //Message Data
        MessageData messageData=new MessageData();

        messageData.setBotMessage(false);
        messageData.setMessageType(MessageData.TYPE_TEXT);
        messageData.setMessage(input.toString());
        messageData.setDateTime("12:10");
        adapter.addToStart(messageData,true);
        adapter.addToStart(null,true);
        messagesList.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.removeDots();
                MessageData messageData=new MessageData();
                messageData.setBotMessage(true);
                messageData.setMessageType(MessageData.TYPE_TEXT);
                messageData.setMessage(message[random.nextInt(message.length-1)]);
                messageData.setDateTime("12:10");
                adapter.addToStart(messageData,true);
            }
        },2000);
        return true;
    }

    @Override
    public void onAddAttachments() {


        MessageData messageData=new MessageData();
        messageData.setBotMessage(random.nextBoolean());
        messageData.setMessage("messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);");
        messageData.setMessageType(MessageData.TYPE_IMAGE);
        messageData.setDateTime("12:10");
        adapter.addToStart(messageData,true);


    }

    @Override
    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }
    }

    @Override
    public void stopListening() {
        Toast.makeText(this,"Stop",Toast.LENGTH_SHORT).show();
        mSpeechRecognizer.stopListening();
    }


    @Override
    public void onImage(String imageUrl) {
        Intent intent =new Intent(this,ImageActivity.class);
        intent.putExtra("url","https://i.ytimg.com/vi/RHLknisJ-Sg/maxresdefault.jpg");
        startActivity(intent);
    }

    @Override
    public void onVideo(String video, String thumbUrl) {
        Intent intent =new Intent(this,VideoActivity.class);
        intent.putExtra("url","https://i.ytimg.com/vi/RHLknisJ-Sg/maxresdefault.jpg");
        startActivity(intent);
    }

    @Override
    public void onLikeClick(Object data) {
        Toast.makeText(this,"Like Click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDislikeClick(Object data) {
        Toast.makeText(this,"Dislike Click",Toast.LENGTH_LONG).show();
    }

    public void scrollToEnd(View v)
    {
        messagesList.smoothScrollToPosition(0);
    }


    private void sendText(String text)
    {
        String url = "";
        StringRequest request =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };




       /* StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                       // Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);*/

    }


    private void sendLikeDislike(String messageId,boolean isLike) {
        String url = "";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
    }



}
