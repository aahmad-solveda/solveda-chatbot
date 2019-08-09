# solveda-chatbot

Add Lib

1. Add this line to your dependency{} block in app build.gradle
implementation 'com.github.aahmad-solveda:solveda-chatbot:0.1.2'

2. Add this line to your allprojects->repositories{} block in project build.gradle
maven { url 'https://jitpack.io' }

3. Add Your an activty and a layout in your project
4. Extend ChatBotActivity in your activity and add below code in your activity

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

5. Add below code in your layout

<?xml version="1.0" encoding="utf-8"?>

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"

    xmlns:app="http://schemas.android.com/apk/res-auto"

    xmlns:tools="http://schemas.android.com/tools"

    android:layout_width="match_parent"

    android:layout_height="match_parent"

    app:layout_behavior="@string/appbar_scrolling_view_behavior"

    tools:context=".MainActivity"

    tools:showIn="@layout/activity_main">



    <com.solveda.watsonchatbot.messages.MessagesList

        android:id="@+id/messagesList"

        android:layout_width="match_parent"

        android:layout_height="match_parent"

        android:layout_above="@+id/input"

        app:dislikeButtonColor="#6B6B6B"

        app:textAutoLink="all"

        app:likeButtonColor="@color/colorPrimary"/>





    <android.support.design.widget.FloatingActionButton

        android:id="@+id/btnScrollToEnd"

        android:layout_width="wrap_content"

        android:layout_height="wrap_content"

        android:layout_alignBottom="@+id/messagesList"

        android:layout_alignParentEnd="true"

        android:layout_alignParentRight="true"

        android:layout_margin="10dp"

        android:alpha="0.85"

        android:onClick="scrollToEnd"

        app:backgroundTint="@color/white"

        app:fabSize="mini"

        app:srcCompat="@drawable/ic_scroll_to_end" />



    <View

        android:layout_width="match_parent"

        android:layout_height="1dp"

        android:layout_above="@+id/input"

        android:layout_marginLeft="16dp"

        android:layout_marginRight="16dp"

        android:background="@color/gray_light"/>



    <com.solveda.watsonchatbot.messages.MessageInput

        android:id="@+id/input"

        android:layout_width="match_parent"

        android:layout_height="wrap_content"

        android:layout_alignParentBottom="true"

        app:inputHint="@string/hint_enter_a_message"

        app:attachmentButtonIcon="@drawable/ic_mic"

        app:attachmentButtonDefaultIconColor="#ff0000"

        app:inputButtonDefaultIconColor="#ff0000"

        app:showAttachmentButton="true"/>



    <LinearLayout

        android:id="@+id/errorView"

        android:layout_width="match_parent"

        android:layout_height="match_parent"

        android:background="@color/white"

        android:orientation="vertical"

        android:visibility="gone"

        android:gravity="center">



        <ImageView

            android:layout_width="wrap_content"

            android:layout_height="wrap_content"

            android:alpha=".7"

            android:src="@drawable/ic_off"/>



        <TextView

            android:layout_width="wrap_content"

            android:layout_height="wrap_content"

            android:textSize="20sp"

            android:alpha=".7"

            android:layout_marginTop="16dp"

            android:textColor="@color/black"

            android:textStyle="bold"

            android:text="No Internet Connection"/>



    </LinearLayout>



</RelativeLayout>
 

Note: To Customize Chat item use below optional attribute in MessagesList

Attributes

Name

Description

app:incomingTextLinkColor

R.color.color_name used for hyperkink color in incoming message

app:outcomingTextLinkColor

R.color.color_name used for hyperkink color in user message

app:incomingAvatarWidth
app:incomingAvatarHeight

40dp or R.dimen.your_value  used to decide dimension chatbot avatar image

app: incomingAvatarIcon

R.drawable. used to change/set chatbot avatar image

app:incomingBubbleDrawable

app:incomingDefaultBubbleColor

app:incomingBubblePaddingLeft

app:incomingBubblePaddingRight

app:incomingBubblePaddingTop

app:incomingBubblePaddingBottom

All Attribute for incoming bubble

app:incomingTextColor

app:incomingTextSize

app:incomingTextFont

app:incomingTextStyle

Message text style

app:incomingTimeTextColor

app:incomingTimeTextSize

app:incomingTimeTextFont

app:incomingTimeTextStyle

Time text stytle

app:productTextColor

app:productTextSize

app:productTextFont

app:productTextStyle

Product message text style

app:productInfoBtnTextColor

app:productInfoBtnTextSize

app:productInfoBtnTextFont

app:productInfoBtnBackground

app:productInfoBtnTextStyle

Product info button style

app: productOthersBtnTextColor

app: productOthersBtnTextSize

app: productOthersBtnTextFont

app: productOthersBtnBackground

app: productOthersBtnTextStyle

Product other button style

 app: likeButtonColor

app: dislikeButtonColor

 Color ref for like btn and dislike btn

 

 Note: To Customize Chat input view use below attribute in MessagesInput

Attributes

Name

Description

app:inputButtonIcon

app:inputButtonDefaultIconColor
app:inputButtonDefaultIconPressedColor

app: inputButtonMargin

app: inputButtonWidth

app: inputButtonHeight

Input button style in right

app:attachmentButtonBackground

app:attachmentButtonDefaultBgColor

app:attachmentButtonDefaultBgPressedColor

app:attachmentButtonDefaultBgDisabledColor

app: attachmentButtonIcon

app: attachmentButtonMargin

app: attachmentButtonWidth

app: inputButtonHeight

 

Audio record button style

app: inputMaxLines

app: inputHint

app: inputText

app: inputTextSize

app: inputTextColor

app: inputHintColor

Input box text style

 

 

 

 
