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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solveda.watsonchatbot.messages.IMessageData;
import com.solveda.watsonchatbot.messages.MessageAdapter;
import com.solveda.watsonchatbot.messages.MessageData;
import com.solveda.watsonchatbot.messages.MessageInput;
import com.solveda.watsonchatbot.messages.MessagesList;
import com.solveda.watsonchatbot.models.BotResponse;
import com.solveda.watsonchatbot.models.ChatResponse;
import com.solveda.watsonchatbot.models.ProductInfo;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
           // adapter.addToStart(null, true);
            sendText(input.toString());
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
    public void onBuyNowClick(IMessageData data) {

        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(data.getProductUrl()));
        startActivity(intent);
    }

    @Override
    public void onProductBenefitsClick(IMessageData data) {
        sendText("Benefits of "+data.getProductName());
    }

    @Override
    public void onProductIngredientsClick(IMessageData data) {
        sendText("Ingredients of "+data.getProductName());
    }

    @Override
    public void onProductHowToUseClick(IMessageData data) {
        sendText("How to use "+data.getProductName());
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
                if(error!=null && error instanceof TimeoutError)
                {
                    Toast.makeText(ChatBotActivity.this,"Network Connection timeout! Please try again later.",Toast.LENGTH_LONG).show();
                }
                canSend = true;
                adapter.removeDots();
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
            //if(response.getOutput()!=null && response.getOutput().getText()!=null )
            if(response.getOutput()!=null && response.getOutput().getChatResponses()!=null )
            {
                //for(int i=0; i<response.getOutput().getText().size();i++)
                for(int i=0; i<response.getOutput().getChatResponses().size();i++)
                {
                    if(response.getOutput().getChatResponses().get(i).getType().equals(ChatResponse.TYPE_TEXT))
                    {
                        for(int j=0; response.getOutput().getChatResponses().get(i).getText()!=null && j<response.getOutput().getChatResponses().get(i).getText().size();j++)
                        {
                            MessageData messageData=new MessageData();
                            messageData.setBotMessage(true);
                            messageData.setMessageType(MessageData.TYPE_TEXT);
                            messageData.setMessage(response.getOutput().getChatResponses().get(i).getText().get(j));
                            messageData.setDateTime(format.format(System.currentTimeMillis()));
                            adapter.addToStart(messageData,true);
                        }
                    }
                    String s= response.getOutput().getChatResponses().get(i).getType();
                    if(response.getOutput().getChatResponses().get(i).getType().equals(ChatResponse.TYPE_PRODUCT_INFO))
                    {
                        List<ProductInfo> piList =response.getOutput().getChatResponses().get(i).getProductInfo();
                        for(int j=0; response.getOutput().getChatResponses().get(i).getProductInfo()!=null && j<piList.size();j++)
                        {

                            MessageData messageData=new MessageData();
                            messageData.setBotMessage(true);
                            messageData.setId(piList.get(j).getProduct_id());
                            messageData.setProductName(piList.get(j).getProduct_name());
                            messageData.setProductPrice(piList.get(j).getProd_price());
                            messageData.setImageUrl(piList.get(j).getProd_image());
                            messageData.setProductUrl(piList.get(j).getProd_url());
                            messageData.setMessage(piList.get(j).getProd_decs());
                            messageData.setMessageType(MessageData.TYPE_PRODUCT);
                            messageData.setDateTime(format.format(System.currentTimeMillis()));
                            adapter.addToStart(messageData,true);
                        }
                    }
                }
            }

            return context;
        }
        catch (Exception ex)
        {
            adapter.removeDots();
            ex.printStackTrace();
            return new JSONObject();
        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

}
