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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.solveda.watsonchatbot.messages.MessageAdapter;
import com.solveda.watsonchatbot.messages.MessageData;
import com.solveda.watsonchatbot.messages.MessageInput;
import com.solveda.watsonchatbot.messages.MessagesList;

import java.util.ArrayList;
import java.util.Locale;
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

        messageData.setBotMessage(random.nextBoolean());
        messageData.setMessageType(MessageData.TYPE_TEXT);
        messageData.setMessage(input.toString());
        messageData.setDateTime("12:10");
        adapter.addToStart(messageData,true);
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
}
