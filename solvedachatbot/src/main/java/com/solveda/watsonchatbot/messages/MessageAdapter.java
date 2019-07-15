package com.solveda.watsonchatbot.messages;

import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solveda.watsonchatbot.R;
import com.solveda.watsonchatbot.utils.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MessageAdapter<MESSAGE extends IMessageData> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerScrollMoreListener.OnLoadMoreListener
{

    private static final int TYPE_TEXT_INCOMING=1;
    private static final int TYPE_TEXT_OUTGOING=2;
    private static final int TYPE_IMAGE_INCOMING=3;
    private static final int TYPE_IMAGE_OUTGOING=4;
    private static final int TYPE_VIDEO_INCOMING=5;
    private static final int TYPE_VIDEO_OUTGOING=6;

    protected List<Object> items;
    private RecyclerView.LayoutManager layoutManager;
    private MessagesListStyle messagesListStyle;
    private MessagesList.ItemClick itemClick;

    public void setItemClick(MessagesList.ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public MessageAdapter()
    {
        items =new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {

        return getViewType(items.get(position));
    }
    protected int getViewType(Object item) {
        if(item instanceof MessageData)
        {
            MessageData data = (MessageData)item;
            if(data.isBotMessage())
            {
                if(data.getMessageType()==MessageData.TYPE_TEXT)
                {
                    return TYPE_TEXT_INCOMING;
                }
                else if(data.getMessageType()==MessageData.TYPE_IMAGE)
                {
                    return TYPE_IMAGE_INCOMING;
                }
                else if(data.getMessageType()==MessageData.TYPE_VIDEO)
                {
                    return TYPE_VIDEO_INCOMING;
                }
            }
            else
            {
                if(data.getMessageType()==MessageData.TYPE_TEXT)
                {
                    return TYPE_TEXT_OUTGOING;
                }
                else if(data.getMessageType()==MessageData.TYPE_IMAGE)
                {
                    return TYPE_IMAGE_OUTGOING;
                }
                else if(data.getMessageType()==MessageData.TYPE_VIDEO)
                {
                    return TYPE_VIDEO_OUTGOING;
                }
            }
        }
        return TYPE_TEXT_OUTGOING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        if(i==TYPE_TEXT_INCOMING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_incoming_text_message, viewGroup, false);
            IncomingTextMessageViewHolder holder =new IncomingTextMessageViewHolder(v);
            holder.applyStyle(messagesListStyle);
            return holder;
        }
        else if(i==TYPE_IMAGE_INCOMING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_incoming_video_message, viewGroup, false);
            IncomingImageMessageViewHolder holder=new IncomingImageMessageViewHolder(v);
            holder.applyStyle(messagesListStyle,itemClick);
            return holder;
        }
        else if(i==TYPE_VIDEO_INCOMING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_incoming_video_message, viewGroup, false);
            IncomingVideoMessageViewHolder holder=new IncomingVideoMessageViewHolder(v);
            holder.applyStyle(messagesListStyle,itemClick);
            return holder;
        }
        else if(i==TYPE_TEXT_OUTGOING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_outcoming_text_message, viewGroup, false);
            OutGoingTextMessageViewHolder holder =new OutGoingTextMessageViewHolder(v);
            holder.applyStyle(messagesListStyle);
            return holder;
        }
        else if(i==TYPE_IMAGE_OUTGOING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_outcoming_image_message, viewGroup, false);
            OutGoingImageMessageViewHolder holder =new OutGoingImageMessageViewHolder(v);
            holder.applyStyle(messagesListStyle,itemClick);
            return holder;
        }
        else if(i==TYPE_VIDEO_OUTGOING)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_outcoming_image_message, viewGroup, false);
            OutGoingImageMessageViewHolder holder =new OutGoingImageMessageViewHolder(v);
            holder.applyStyle(messagesListStyle,itemClick);
            return holder;
        }
        else
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatkit_item_outcoming_text_message, viewGroup, false);
            OutGoingTextMessageViewHolder holder =new OutGoingTextMessageViewHolder(v);
            holder.applyStyle(messagesListStyle);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
        if(viewHolder instanceof IncomingTextMessageViewHolder)
        {
            ((IncomingTextMessageViewHolder) viewHolder).onBindData((MessageData)items.get(i));
        }
        else  if(viewHolder instanceof IncomingImageMessageViewHolder)
        {
            ((IncomingImageMessageViewHolder) viewHolder).onBindData((MessageData)items.get(i));
        }
        else  if(viewHolder instanceof IncomingVideoMessageViewHolder)
        {
            ((IncomingVideoMessageViewHolder) viewHolder).onBindData((MessageData)items.get(i));
        }
        else if(viewHolder instanceof OutGoingTextMessageViewHolder)
        {
            ((OutGoingTextMessageViewHolder) viewHolder).onBindData((MessageData)items.get(i));
        }
        else  if(viewHolder instanceof OutGoingImageMessageViewHolder)
        {
            ((OutGoingImageMessageViewHolder) viewHolder).onBindData((MessageData)items.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onLoadMore(int page, int total) {

    }

    @Override
    public int getMessagesCount() {
        return items.size();
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setStyle(MessagesListStyle style) {
        this.messagesListStyle = style;
    }


    public void addToStart(MessageData message, boolean scroll) {

       // Wrapper<MESSAGE> element = new Wrapper<>(message);
        items.add(0, message);
        notifyItemRangeInserted(0,1);
        if (layoutManager != null && scroll) {
            layoutManager.scrollToPosition(0);
        }
    }

    /*public class Wrapper<DATA> {
        public DATA item;
        public boolean isSelected;
        Wrapper(DATA item) {
            this.item = item;
        }
    }*/


    public static class IncomingTextMessageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView messageUserAvatar;
        TextView messageText,messageTime;
        ViewGroup bubble;
        public IncomingTextMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            bubble = itemView.findViewById(R.id.bubble);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style)
        {
            if (bubble != null) {
                bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
                        style.getIncomingDefaultBubblePaddingTop(),
                        style.getIncomingDefaultBubblePaddingRight(),
                        style.getIncomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(bubble, style.getIncomingBubbleDrawable());
            }
            if (messageText != null) {
                messageText.setTextColor(style.getIncomingTextColor());
                messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
                messageText.setTypeface(messageText.getTypeface(), style.getIncomingTextStyle());
                messageText.setAutoLinkMask(style.getTextAutoLinkMask());
                messageText.setLinkTextColor(style.getIncomingTextLinkColor());
                if(style.getIncomingTextFont()!=-1)
                    messageText.setTypeface(ResourcesCompat.getFont(messageText.getContext(),style.getIncomingTextFont()));
               // configureLinksBehavior(messageText);
            }
            if (messageTime != null) {
                messageTime.setTextColor(style.getIncomingTimeTextColor());
                messageTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
                messageTime.setTypeface(messageTime.getTypeface(), style.getIncomingTimeTextStyle());
                if(style.getIncomingTimeTextFont()!=-1)
                    messageTime.setTypeface(ResourcesCompat.getFont(messageTime.getContext(),style.getIncomingTimeTextFont()));
            }
            if(messageUserAvatar!=null && style.getIncomingAvatar()!=-1)
            {
                messageUserAvatar.setImageResource(style.getIncomingAvatar());
            }
            
            
        }

        private void configureLinksBehavior(TextView messageText) {
            /*messageText.setLinksClickable(false);
            messageText.setMovementMethod(new LinkMovementMethod() {
                @Override
                public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                    boolean result = false;
                    //if (!MessagesListAdapter.isSelectionModeEnabled)
                    {
                        result = super.onTouchEvent(widget, buffer, event);
                    }
                    itemView.onTouchEvent(event);
                    return result;
                }
            });*/
        }

    }




    public static class IncomingImageMessageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView messageUserAvatar,image;
        TextView messageText,messageTime;
        View  imageOverlay;
        ViewGroup bubble;
        public IncomingImageMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            image = itemView.findViewById(R.id.image);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            imageOverlay = itemView.findViewById(R.id.imageOverlay);
            bubble = itemView.findViewById(R.id.bubble);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
            if(image!=null) {
                Picasso.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
                image.setTag("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png");
            }
               // Picasso.with(image.getContext()).load("https://storage.googleapis.com/ehimages/2018/5/19/img_2910f17c8a7505a0e5f8a1d6ac287903_1526710893725_processed_original.png").into(image);
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style, final MessagesList.ItemClick itemClick)
        {
            if (image!=null && image instanceof RoundedImageView) {
                ((RoundedImageView) image).setCorners(
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                       0,
                        0
                );
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onImage(v.getTag().toString());
                        }
                    }
                });
            }
            if (bubble != null) {
                bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
                        style.getIncomingDefaultBubblePaddingTop(),
                        style.getIncomingDefaultBubblePaddingRight(),
                        style.getIncomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(bubble, style.getIncomingBubbleDrawable());
            }
            if (messageText != null) {
                messageText.setTextColor(style.getIncomingTextColor());
                messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
                messageText.setTypeface(messageText.getTypeface(), style.getIncomingTextStyle());
                messageText.setAutoLinkMask(style.getTextAutoLinkMask());
                messageText.setLinkTextColor(style.getIncomingTextLinkColor());
                if(style.getIncomingTextFont()!=-1)
                    messageText.setTypeface(ResourcesCompat.getFont(messageText.getContext(),style.getIncomingTextFont()));
                // configureLinksBehavior(messageText);
            }
            if (messageTime != null) {
                messageTime.setTextColor(style.getIncomingTimeTextColor());
                messageTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
                messageTime.setTypeface(messageTime.getTypeface(), style.getIncomingTimeTextStyle());
                if(style.getIncomingImageTimeTextFont()!=-1)
                    messageTime.setTypeface(ResourcesCompat.getFont(messageTime.getContext(),style.getIncomingImageTimeTextFont()));
            }
            if(messageUserAvatar!=null && style.getIncomingAvatar()!=-1)
            {
                messageUserAvatar.setImageResource(style.getIncomingAvatar());
            }


        }

        private void configureLinksBehavior(TextView messageText) {
            messageText.setLinksClickable(false);
            messageText.setMovementMethod(new LinkMovementMethod() {
                @Override
                public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                    boolean result = false;
                    //if (!MessagesListAdapter.isSelectionModeEnabled)
                    {
                        result = super.onTouchEvent(widget, buffer, event);
                    }
                    itemView.onTouchEvent(event);
                    return result;
                }
            });
        }

    }

    public static class IncomingVideoMessageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView messageUserAvatar,image,imagePlay;
        TextView messageText,messageTime;
        View  imageOverlay;
        ViewGroup bubble;
        public IncomingVideoMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            image = itemView.findViewById(R.id.image);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            imageOverlay = itemView.findViewById(R.id.imageOverlay);
            bubble = itemView.findViewById(R.id.bubble);
            imagePlay = itemView.findViewById(R.id.imagePlay);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
            if(image!=null) {
                Picasso.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
                image.setTag(R.string.app_name,"https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png");
                image.setTag(R.string.action_settings,"https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png");
            }
            // Picasso.with(image.getContext()).load("https://storage.googleapis.com/ehimages/2018/5/19/img_2910f17c8a7505a0e5f8a1d6ac287903_1526710893725_processed_original.png").into(image);
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style, final MessagesList.ItemClick itemClick)
        {
            if (image instanceof RoundedImageView) {
                ((RoundedImageView) image).setCorners(
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                        0,
                        0
                );
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onVideo(v.getTag(R.string.app_name).toString(),v.getTag(R.string.action_settings).toString());
                        }
                    }
                });
            }
            if (bubble != null) {
                bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
                        style.getIncomingDefaultBubblePaddingTop(),
                        style.getIncomingDefaultBubblePaddingRight(),
                        style.getIncomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(bubble, style.getIncomingBubbleDrawable());
            }
            if (messageText != null) {
                messageText.setTextColor(style.getIncomingTextColor());
                messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
                messageText.setTypeface(messageText.getTypeface(), style.getIncomingTextStyle());
                messageText.setAutoLinkMask(style.getTextAutoLinkMask());
                messageText.setLinkTextColor(style.getIncomingTextLinkColor());
                if(style.getIncomingTextFont()!=-1)
                    messageText.setTypeface(ResourcesCompat.getFont(messageText.getContext(),style.getIncomingTextFont()));
                // configureLinksBehavior(messageText);
            }
            if (messageTime != null) {
                messageTime.setTextColor(style.getIncomingTimeTextColor());
                messageTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
                messageTime.setTypeface(messageTime.getTypeface(), style.getIncomingTimeTextStyle());
                if(style.getIncomingImageTimeTextFont()!=-1)
                    messageTime.setTypeface(ResourcesCompat.getFont(messageTime.getContext(),style.getIncomingImageTimeTextFont()));
            }
            if(messageUserAvatar!=null && style.getIncomingAvatar()!=-1)
            {
                messageUserAvatar.setImageResource(style.getIncomingAvatar());
            }
            if(imagePlay!=null && style.getPlayButton()!=-1)
            {
                imagePlay.setImageResource(style.getPlayButton());
            }


        }

        private void configureLinksBehavior(TextView messageText) {
            messageText.setLinksClickable(false);
            messageText.setMovementMethod(new LinkMovementMethod() {
                @Override
                public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                    boolean result = false;
                    //if (!MessagesListAdapter.isSelectionModeEnabled)
                    {
                        result = super.onTouchEvent(widget, buffer, event);
                    }
                    itemView.onTouchEvent(event);
                    return result;
                }
            });
        }

    }



    public static class OutGoingTextMessageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView messageUserAvatar;
        TextView messageText,messageTime;
        ViewGroup bubble;
        public OutGoingTextMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            bubble = itemView.findViewById(R.id.bubble);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style)
        {
            if (bubble != null) {
                bubble.setPadding(style.getOutcomingDefaultBubblePaddingLeft(),
                        style.getOutcomingDefaultBubblePaddingTop(),
                        style.getOutcomingDefaultBubblePaddingRight(),
                        style.getOutcomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(bubble, style.getOutcomingBubbleDrawable());
            }
            if (messageText != null) {
                messageText.setTextColor(style.getOutcomingTextColor());
                messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());
                messageText.setTypeface(messageText.getTypeface(), style.getOutcomingTextStyle());
                messageText.setAutoLinkMask(style.getTextAutoLinkMask());
                messageText.setLinkTextColor(style.getIncomingTextLinkColor());
                if(style.getOutcomingTextFont()!=-1)
                    messageText.setTypeface(ResourcesCompat.getFont(messageText.getContext(),style.getOutcomingTextFont()));
                // configureLinksBehavior(messageText);
            }
            if (messageTime != null) {
                messageTime.setTextColor(style.getIncomingTimeTextColor());
                messageTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTimeTextSize());
                messageTime.setTypeface(messageTime.getTypeface(), style.getOutcomingTimeTextStyle());
                if(style.getOutcomingTimeTextFont()!=-1)
                    messageTime.setTypeface(ResourcesCompat.getFont(messageTime.getContext(),style.getOutcomingTimeTextFont()));
            }
        }

        private void configureLinksBehavior(TextView messageText) {
           /* messageText.setLinksClickable(false);
            messageText.setMovementMethod(new LinkMovementMethod() {
                @Override
                public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                    boolean result = false;
                    //if (!MessagesListAdapter.isSelectionModeEnabled)
                    {
                        result = super.onTouchEvent(widget, buffer, event);
                    }
                    itemView.onTouchEvent(event);
                    return result;
                }
            });*/
        }

    }


    public static class OutGoingImageMessageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView messageUserAvatar,image;
        TextView messageText,messageTime;
        View  imageOverlay;
        ViewGroup bubble;
        public OutGoingImageMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            image = itemView.findViewById(R.id.image);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            imageOverlay = itemView.findViewById(R.id.imageOverlay);
            bubble = itemView.findViewById(R.id.bubble);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
            if(image!=null) {
                Picasso.with(image.getContext()).load("https://storage.googleapis.com/ehimages/2018/5/19/img_2910f17c8a7505a0e5f8a1d6ac287903_1526710893725_processed_original.png").into(image);
                image.setTag("https://storage.googleapis.com/ehimages/2018/5/19/img_2910f17c8a7505a0e5f8a1d6ac287903_1526710893725_processed_original.png");
            }
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style, final MessagesList.ItemClick itemClick)
        {
            if (image instanceof RoundedImageView) {
                ((RoundedImageView) image).setCorners(
                        R.dimen.message_bubble_corners_radius,
                        R.dimen.message_bubble_corners_radius,
                        0,
                        0
                );
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onImage(v.getTag().toString());
                        }
                    }
                });
            }
            if (bubble != null) {
                bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
                        style.getIncomingDefaultBubblePaddingTop(),
                        style.getIncomingDefaultBubblePaddingRight(),
                        style.getIncomingDefaultBubblePaddingBottom());
                ViewCompat.setBackground(bubble, style.getOutcomingBubbleDrawable());
            }
            if (messageText != null) {
                messageText.setTextColor(style.getOutcomingTextColor());
                messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());
                messageText.setTypeface(messageText.getTypeface(), style.getOutcomingTextStyle());
                messageText.setAutoLinkMask(style.getTextAutoLinkMask());
                messageText.setLinkTextColor(style.getIncomingTextLinkColor());
                if(style.getOutcomingTextFont()!=-1)
                    messageText.setTypeface(ResourcesCompat.getFont(messageText.getContext(),style.getOutcomingTextFont()));
                // configureLinksBehavior(messageText);
            }
            if (messageTime != null) {
                messageTime.setTextColor(style.getIncomingTimeTextColor());
                messageTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTimeTextSize());
                messageTime.setTypeface(messageTime.getTypeface(), style.getOutcomingTimeTextStyle());
                if(style.getOutcomingImageTimeTextFont()!=-1)
                    messageTime.setTypeface(ResourcesCompat.getFont(messageTime.getContext(),style.getOutcomingImageTimeTextFont()));
            }


        }

        private void configureLinksBehavior(TextView messageText) {
            messageText.setLinksClickable(false);
            messageText.setMovementMethod(new LinkMovementMethod() {
                @Override
                public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                    boolean result = false;
                    //if (!MessagesListAdapter.isSelectionModeEnabled)
                    {
                        result = super.onTouchEvent(widget, buffer, event);
                    }
                    itemView.onTouchEvent(event);
                    return result;
                }
            });
        }

    }


}
