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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solveda.watsonchatbot.messages.MessageAdapter;
import com.solveda.watsonchatbot.messages.MessageData;
import com.solveda.watsonchatbot.messages.MessageInput;
import com.solveda.watsonchatbot.messages.MessagesList;
import com.solveda.watsonchatbot.models.BotResponse;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;

    protected MessageInput input;
    protected MessageAdapter adapter;
    protected MessagesList messagesList;
    protected FloatingActionButton btnScrollToEnd;
    String base_url;
    RequestQueue queue ;
    Gson gson;
    SimpleDateFormat format;
    JSONObject currentContext;
    boolean canSend=true;
    public void init(String base_url)
    {
        initMessageAdapter();
        input.setInputListener(this);
        //input.setAttachmentsListener(this);
        input.setAudioListener(this);
        this.base_url = base_url;
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

        queue = Volley.newRequestQueue(this);
        gson =new Gson();
        format =new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
        initChat();
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
                Log.w("LastCompletelyVisible",""+messagesList.getLayoutManager().findLastCompletelyVisibleItemPosition());
                Log.w("FirstCompletelyVisible",""+messagesList.getLayoutManager().findFirstCompletelyVisibleItemPosition());
                if (messagesList.getLayoutManager().findLastCompletelyVisibleItemPosition() <  5) {
                    btnScrollToEnd.setEnabled( false);
                    btnScrollToEnd.hide();
                }
                else {
                    btnScrollToEnd.setEnabled( true);
                    btnScrollToEnd.show();
                }
                /*if (adapter.getItemCount()<=8 ||  messagesList.getLayoutManager().findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 5)
                {
                    btnScrollToEnd.setEnabled( false);
                    btnScrollToEnd.hide();
                }
                else {
                    btnScrollToEnd.setEnabled( true);
                    btnScrollToEnd.show();

                }*/
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

        if(canSend) {

            MessageData messageData = new MessageData();
            messageData.setBotMessage(false);
            messageData.setMessageType(MessageData.TYPE_TEXT);
            messageData.setMessage(input.toString());
            messageData.setDateTime(format.format(System.currentTimeMillis()));
            adapter.addToStart(messageData, true);
            adapter.addToStart(null, true);
            sendText(input.toString());
            /*messageData.setBotMessage(false);
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
            },2000);*/
            return true;
        }
        else
        {
            return false;
        }
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

    private void initChat() {

        canSend = false;
        Log.d("WATSON_RESPONSE","API CALLING");
        JSONObject object=new JSONObject();
        try
        {
            JSONObject context = new JSONObject();
            JSONObject input = new JSONObject();
            input.put("text","");
            object.put("context",context);
            object.put("input",input);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, base_url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                canSend = true;
                Log.d("WATSON_RESPONSE",response.toString());
                currentContext = parseResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                canSend = true;
                Toast.makeText(ChatBotActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
              //  Log.d("WATSON_RESPONSE",error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }

    private void sendText(final String text)
    {
        Log.d("WATSON_RESPONSE","API CALLING");
        JSONObject object=new JSONObject();
        try
        {
            JSONObject context = new JSONObject();
            JSONObject input = new JSONObject();
            input.put("text",text);
            object.put("context",currentContext==null?new JSONObject():currentContext);
            object.put("input",input);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, base_url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                canSend = true;
                Log.d("WATSON_RESPONSE",response.toString());
                currentContext = parseResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                canSend = true;
              //  Toast.makeText(ChatBotActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
           //     Log.d("WATSON_RESPONSE",error.getLocalizedMessage());
            }
        });
        queue.add(request);
    }


    private void sendLikeDislike(String messageId,boolean isLike) {
        String url = "";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseResponse(response);
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

    private JSONObject parseResponse(String result)
    {
        try
        {
            JSONObject res=new JSONObject(result);
            JSONObject context=res.getJSONObject("context");
            BotResponse response= gson.fromJson(result, BotResponse.class);
            adapter.removeDots();
            if(response.getOutput()!=null && response.getOutput().getText()!=null )
            {
                for(int i=0; i<response.getOutput().getText().size();i++)
                {
                    MessageData messageData=new MessageData();
                    messageData.setBotMessage(true);
                    messageData.setMessageType(MessageData.TYPE_TEXT);
                    messageData.setMessage(response.getOutput().getText().get(i));
                    messageData.setDateTime(format.format(System.currentTimeMillis()));
                    adapter.addToStart(messageData,true);
                }
            }
            else if(response.getOutput()!=null && response.getOutput().getPlainText()!=null )
            {
                MessageData messageData=new MessageData();
                messageData.setBotMessage(true);
                messageData.setMessageType(MessageData.TYPE_TEXT);
                messageData.setMessage(response.getOutput().getPlainText());
                messageData.setDateTime(format.format(System.currentTimeMillis()));
                adapter.addToStart(messageData,true);
            }

            return context;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return new JSONObject();
        }
    }


}
