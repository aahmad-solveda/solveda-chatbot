package com.solveda.watsonchatbot.messages;

import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solveda.watsonchatbot.R;
import com.solveda.watsonchatbot.controls.waitingdots.DotsTextView;
import com.solveda.watsonchatbot.utils.RoundedImageView;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class MessageAdapter<MESSAGE extends IMessageData> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerScrollMoreListener.OnLoadMoreListener
{

    private static final int TYPE_TEXT_INCOMING=1;
    private static final int TYPE_TEXT_OUTGOING=2;
    private static final int TYPE_IMAGE_INCOMING=3;
   // private static final int TYPE_IMAGE_OUTGOING=4;
    private static final int TYPE_VIDEO_INCOMING=5;
   // private static final int TYPE_VIDEO_OUTGOING=6;
    private static final int TYPE_PRODUCT=8;
    private static final int TYPE_DOTS_VIEW=7;

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
        if(item==null)
        {
            return TYPE_DOTS_VIEW;
        }
        else if(item instanceof MessageData)
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
                else if(data.getMessageType() ==MessageData.TYPE_PRODUCT)
                {
                    return TYPE_PRODUCT;
                }
            }
            else
            {
                if(data.getMessageType()==MessageData.TYPE_TEXT)
                {
                    return TYPE_TEXT_OUTGOING;
                }
                /*else if(data.getMessageType()==MessageData.TYPE_IMAGE)
                {
                    return TYPE_IMAGE_OUTGOING;
                }
                else if(data.getMessageType()==MessageData.TYPE_VIDEO)
                {
                    return TYPE_VIDEO_OUTGOING;
                }*/
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
            holder.applyStyle(messagesListStyle,itemClick);
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
        else if(i==TYPE_PRODUCT)
        {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_detail, viewGroup, false);
            ProductInfoViewHolder holder=new ProductInfoViewHolder(v);
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
        else if(i==TYPE_DOTS_VIEW)
        {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waiting_dots, null);
            TypingDotsHolder dotsHolder = new TypingDotsHolder(view);
            DotsTextView dots = dotsHolder.dotsTextView;
            dots.start();
            dotsHolder.applyStyle(messagesListStyle);
            return dotsHolder;
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
        else if(viewHolder instanceof ProductInfoViewHolder)
        {
            ((ProductInfoViewHolder) viewHolder).onBindData((MessageData)items.get(i));
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
        if(items.size()>0 && items.get(0)==null)
        {
            items.add(1,message);
            notifyItemRangeInserted(1,1);
        }
        else {

            items.add(0, message);
            notifyItemRangeInserted(0,1);
            if(!message.isBotMessage()) {
                items.add(0, null);
                notifyItemRangeInserted(0,1);
            }
        }

        if (layoutManager != null && scroll) {
            layoutManager.scrollToPosition(0);
        }
    }
    public void removeDots()
    {
        //items.remove(0);
        //notifyItemRemoved(0);
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i)==null) {
                items.remove(i);
                notifyItemRemoved(i);
            }
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
        ImageView messageUserAvatar,btnLike,btnDislike;
        TextView messageText,messageTime;
        ViewGroup bubble;
        public IncomingTextMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
            bubble = itemView.findViewById(R.id.bubble);
            btnLike = itemView.findViewById(R.id.btn_like);
            btnDislike = itemView.findViewById(R.id.btn_dislike);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(Html.fromHtml(data.getMessage()));
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
          /*  if(bubble!=null)
                bubble.setSelected(true);*/
        }
        public void applyStyle(MessagesListStyle style, final MessagesList.ItemClick itemClick)
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
            if(btnLike!=null)
            {
                btnLike.setColorFilter(style.getLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onLikeClick(v.getTag());
                        }
                    }
                });
            }
            if(btnDislike!=null)
            {
                btnDislike.setColorFilter(style.getDisLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onDislikeClick(v.getTag());
                        }
                    }
                });
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
        ImageView messageUserAvatar,image,btnLike,btnDislike;
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
            btnLike = itemView.findViewById(R.id.btn_like);
            btnDislike = itemView.findViewById(R.id.btn_dislike);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(Html.fromHtml(data.getMessage()));
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
            if(image!=null) {
                //Picasso.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
                Glide.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
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
            if(btnLike!=null)
            {
                btnLike.setColorFilter(style.getLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onLikeClick(v.getTag());
                        }
                    }
                });
            }
            if(btnDislike!=null)
            {
                btnDislike.setColorFilter(style.getDisLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onDislikeClick(v.getTag());
                        }
                    }
                });
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
        ImageView messageUserAvatar,image,imagePlay,btnLike,btnDislike;
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
            btnLike = itemView.findViewById(R.id.btn_like);
            btnDislike = itemView.findViewById(R.id.btn_dislike);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(data.getMessage());
            if(messageTime!=null)
                messageTime.setText(data.getDateTime());
            if(image!=null) {
                //Picasso.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
               // Picasso.with(image.getContext()).load("http://techslides.com/demos/sample-videos/small.mp4").into(image);
                long thumb = getLayoutPosition()*1000;
                RequestOptions options = new RequestOptions().frame(thumb);
                Glide.with(image.getContext()).load("http://techslides.com/demos/sample-videos/small.mp4").apply(options).into(image);
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
            if(btnLike!=null)
            {
                btnLike.setColorFilter(style.getLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onLikeClick(v.getTag());
                        }
                    }
                });
            }
            if(btnDislike!=null)
            {
                btnDislike.setColorFilter(style.getDisLikeButtonColor(), android.graphics.PorterDuff.Mode.SRC_IN);
                btnDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itemClick!=null)
                        {
                            itemClick.onDislikeClick(v.getTag());
                        }
                    }
                });
            }
            //zfgsdfg

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


    public static class ProductInfoViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView messageText;
        Button btnInfo,btn1,btn2,btn3,btn4;
        ViewGroup bubble;
        public ProductInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            messageText = itemView.findViewById(R.id.messageText);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btn1 = itemView.findViewById(R.id.btn1);
            btn2 = itemView.findViewById(R.id.btn2);
            btn3 = itemView.findViewById(R.id.btn3);
            btn4 = itemView.findViewById(R.id.btn4);
            bubble = itemView.findViewById(R.id.bubble);
        }
        public void onBindData(IMessageData data)
        {
            if(messageText!=null)
                messageText.setText(Html.fromHtml(data.getMessage()));
            if(image!=null) {
                //Picasso.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
                Glide.with(image.getContext()).load("https://www.messengerpeople.com/wp-content/uploads/2018/08/whatsapp-chatbots-whatsapp-bot-messenger-bot.png").into(image);
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
            if(bubble!=null)
            {
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
            if(btnInfo!=null)
            {
                btnInfo.setTextColor(style.getProductInfoBtnTextColor());
                btnInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getProductInfoBtnTextSize());
                btnInfo.setTypeface(btnInfo.getTypeface(), style.getProductInfoBtnTextStyle());
                if(style.getProductInfoBtnTextFont()!=-1)
                    btnInfo.setTypeface(ResourcesCompat.getFont(btnInfo.getContext(),style.getProductInfoBtnTextFont()));
                if(style.getProductInfoBtnBackground()!=-1)
                    btnInfo.setBackgroundResource(style.getProductInfoBtnBackground());
            }
            else if(btn1!=null)
            {
                btn1.setTextColor(style.getProductOthersBtnTextColor());
                btn1.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getProductOthersBtnTextSize());
                btn1.setTypeface(btn1.getTypeface(), style.getProductOthersBtnTextStyle());
                if(style.getProductInfoBtnTextFont()!=-1)
                    btn1.setTypeface(ResourcesCompat.getFont(btn1.getContext(),style.getProductOthersBtnTextFont()));
                if(style.getProductOthersBtnBackground()!=-1)
                    btn1.setBackgroundResource(style.getProductOthersBtnBackground());
            }
            else if(btn2!=null)
            {
                btn2.setTextColor(style.getProductOthersBtnTextColor());
                btn2.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getProductOthersBtnTextSize());
                btn2.setTypeface(btn2.getTypeface(), style.getProductOthersBtnTextStyle());
                if(style.getProductInfoBtnTextFont()!=-1)
                    btn2.setTypeface(ResourcesCompat.getFont(btn2.getContext(),style.getProductOthersBtnTextFont()));
                if(style.getProductOthersBtnBackground()!=-1)
                    btn2.setBackgroundResource(style.getProductOthersBtnBackground());
            }
            else if(btn3!=null)
            {
                btn3.setTextColor(style.getProductOthersBtnTextColor());
                btn3.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getProductOthersBtnTextSize());
                btn3.setTypeface(btn3.getTypeface(), style.getProductOthersBtnTextStyle());
                if(style.getProductInfoBtnTextFont()!=-1)
                    btn3.setTypeface(ResourcesCompat.getFont(btn3.getContext(),style.getProductOthersBtnTextFont()));
                if(style.getProductOthersBtnBackground()!=-1)
                    btn3.setBackgroundResource(style.getProductOthersBtnBackground());
            }
            else if(btn4!=null)
            {
                btn4.setTextColor(style.getProductOthersBtnTextColor());
                btn4.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getProductOthersBtnTextSize());
                btn4.setTypeface(btn4.getTypeface(), style.getProductOthersBtnTextStyle());
                if(style.getProductInfoBtnTextFont()!=-1)
                    btn4.setTypeface(ResourcesCompat.getFont(btn4.getContext(),style.getProductOthersBtnTextFont()));
                if(style.getProductOthersBtnBackground()!=-1)
                    btn4.setBackgroundResource(style.getProductOthersBtnBackground());
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


    public static class TypingDotsHolder extends RecyclerView.ViewHolder
    {
        public DotsTextView dotsTextView;
        public ImageView messageUserAvatar;
        public ViewGroup loadContainer;
        public TypingDotsHolder(@NonNull View itemView) {
            super(itemView);
            dotsTextView = itemView.findViewById(R.id.dots);
            messageUserAvatar = itemView.findViewById(R.id.messageUserAvatar);
            loadContainer = itemView.findViewById(R.id.loadContainer);
        }
        public void start()
        {
            dotsTextView.start();
        }
        public void applyStyle(MessagesListStyle style)
        {
            if(loadContainer!=null)
                ViewCompat.setBackground(loadContainer, style.getIncomingBubbleDrawable());
            if(dotsTextView!=null)
            {
                dotsTextView.setTextColor(style.getIncomingTextColor());
            }
            if(messageUserAvatar!=null && style.getIncomingAvatar()!=-1)
            {
                messageUserAvatar.setImageResource(style.getIncomingAvatar());
            }
        }
    }


}
