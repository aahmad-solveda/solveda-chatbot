/*******************************************************************************
 * Copyright 2019 https://www.solveda.com/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.solveda.watsonchatbot.messages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.solveda.watsonchatbot.commons.models.IMessage;

/**
 * Component for displaying list of messages
 */
public class MessagesList extends RecyclerView {
    private MessagesListStyle messagesListStyle;
    LinearLayoutManager layoutManager;
    public MessagesList(Context context) {
        super(context);
    }

    public MessagesList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
    }

    public MessagesList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseStyle(context, attrs);
    }

    /**
     * Don't use this method for setting your adapter, otherwise exception will by thrown.
     *
     */
    @Override
    public void setAdapter(Adapter adapter) {
         throw new IllegalArgumentException("You can't set adapter to MessagesList. Use #setAdapter(MessagesListAdapter) instead.");
    }

    public void setAdapter(MessageAdapter adapter) {
       // throw new IllegalArgumentException("You can't set adapter to MessagesList. Use #setAdapter(MessagesListAdapter) instead.");
        boolean reverseLayout=true;
        SimpleItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

       // layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, reverseLayout);

        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,reverseLayout);

        setItemAnimator(itemAnimator);
        setLayoutManager(layoutManager);
        adapter.setLayoutManager(layoutManager);
        adapter.setStyle(messagesListStyle);

        addOnScrollListener(new RecyclerScrollMoreListener(layoutManager, adapter));

        super.setAdapter(adapter);
    }

    @Nullable
    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }
    /**
     * Sets adapter for MessagesList
     *
     * @param adapter   Adapter. Must extend MessagesListAdapter
     * @param <MESSAGE> Message model class
     */
   /* public <MESSAGE extends IMessage>
    void setAdapter(MessagesListAdapter<MESSAGE> adapter) {
        setAdapter(adapter, true);
    }

    *//**
     * Sets adapter for MessagesList
     *

     *//*
    public <MESSAGE extends IMessage>
    void setAdapter(MessagesListAdapter<MESSAGE> adapter, boolean reverseLayout) {
        SimpleItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, reverseLayout);

        setItemAnimator(itemAnimator);
        setLayoutManager(layoutManager);
        adapter.setLayoutManager(layoutManager);
        adapter.setStyle(messagesListStyle);

        addOnScrollListener(new RecyclerScrollMoreListener(layoutManager, adapter));
        super.setAdapter(adapter);
    }*/

    @SuppressWarnings("ResourceType")
    private void parseStyle(Context context, AttributeSet attrs) {
        messagesListStyle = MessagesListStyle.parse(context, attrs);
    }


    public interface ItemClick{
        void onImage(String imageUrl);
        void onVideo(String video, String thumbUrl);
        void onLikeClick(Object data);
        void onDislikeClick(Object data);
        void onBuyNowClick(IMessageData data);
        void onProductBenefitsClick(IMessageData data);
        void onProductIngredientsClick(IMessageData data);
        void onProductHowToUseClick(IMessageData data);
    }
}
