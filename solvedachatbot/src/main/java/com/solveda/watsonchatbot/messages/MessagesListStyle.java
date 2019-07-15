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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.solveda.watsonchatbot.R;
import com.solveda.watsonchatbot.commons.Style;

/**
 * Style for MessagesListStyle customization by xml attributes
 */
@SuppressWarnings("WeakerAccess")
public class MessagesListStyle extends Style {

    private int textAutoLinkMask;
    private int incomingTextLinkColor;
    private int outcomingTextLinkColor;

    private int incomingAvatarWidth;
    private int incomingAvatarHeight;

    private  int incomingAvatar;
    private int playButton;

    private int incomingBubbleDrawable;
    private int incomingDefaultBubbleColor;
    private int incomingDefaultBubblePressedColor;
    private int incomingDefaultBubbleSelectedColor;

    private int incomingImageOverlayDrawable;
    private int incomingDefaultImageOverlayPressedColor;
    private int incomingDefaultImageOverlaySelectedColor;

    private int incomingDefaultBubblePaddingLeft;
    private int incomingDefaultBubblePaddingRight;
    private int incomingDefaultBubblePaddingTop;
    private int incomingDefaultBubblePaddingBottom;

    private int incomingTextColor;
    private int incomingTextSize;
    private int incomingTextStyle;
    private int incomingTextFont;

    private int incomingTimeTextColor;
    private int incomingTimeTextSize;
    private int incomingTimeTextStyle;
    private int incomingTimeTextFont;

    private int incomingImageTimeTextColor;
    private int incomingImageTimeTextSize;
    private int incomingImageTimeTextStyle;
    private int incomingImageTimeTextFont;

    private int outcomingBubbleDrawable;
    private int outcomingDefaultBubbleColor;
    private int outcomingDefaultBubblePressedColor;
    private int outcomingDefaultBubbleSelectedColor;

    private int outcomingImageOverlayDrawable;
    private int outcomingDefaultImageOverlayPressedColor;
    private int outcomingDefaultImageOverlaySelectedColor;

    private int outcomingDefaultBubblePaddingLeft;
    private int outcomingDefaultBubblePaddingRight;
    private int outcomingDefaultBubblePaddingTop;
    private int outcomingDefaultBubblePaddingBottom;

    private int outcomingTextColor;
    private int outcomingTextSize;
    private int outcomingTextStyle;
    private int outcomingTextFont;

    private int outcomingTimeTextColor;
    private int outcomingTimeTextSize;
    private int outcomingTimeTextStyle;
    private int outcomingTimeTextFont;

    private int outcomingImageTimeTextColor;
    private int outcomingImageTimeTextSize;
    private int outcomingImageTimeTextStyle;
    private int outcomingImageTimeTextFont;

    private int dateHeaderPadding;
    private String dateHeaderFormat;
    private int dateHeaderTextColor;
    private int dateHeaderTextSize;
    private int dateHeaderTextStyle;

    static MessagesListStyle parse(Context context, AttributeSet attrs) {
        MessagesListStyle style = new MessagesListStyle(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessagesList);

        style.textAutoLinkMask = typedArray.getInt(R.styleable.MessagesList_textAutoLink, 0);
        style.incomingTextLinkColor = typedArray.getColor(R.styleable.MessagesList_incomingTextLinkColor,
                style.getSystemAccentColor());
        style.outcomingTextLinkColor = typedArray.getColor(R.styleable.MessagesList_outcomingTextLinkColor,
                style.getSystemAccentColor());

        style.incomingAvatarWidth = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingAvatarWidth,
                style.getDimension(R.dimen.message_avatar_width));
        style.incomingAvatarHeight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingAvatarHeight,
                style.getDimension(R.dimen.message_avatar_height));

        style.incomingAvatar = typedArray.getResourceId(R.styleable.MessagesList_incomingAvatarIcon, -1);
        style.playButton = typedArray.getResourceId(R.styleable.MessagesList_incomingPlayBtn, -1);

        style.incomingBubbleDrawable = typedArray.getResourceId(R.styleable.MessagesList_incomingBubbleDrawable, -1);
        style.incomingDefaultBubbleColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubbleColor,
                style.getColor(R.color.white_two));
        style.incomingDefaultBubblePressedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubblePressedColor,
                style.getColor(R.color.white_two));
        style.incomingDefaultBubbleSelectedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24));

        style.incomingImageOverlayDrawable = typedArray.getResourceId(R.styleable.MessagesList_incomingImageOverlayDrawable, -1);
        style.incomingDefaultImageOverlayPressedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent));
        style.incomingDefaultImageOverlaySelectedColor = typedArray.getColor(R.styleable.MessagesList_incomingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40));

        style.incomingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left));
        style.incomingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right));
        style.incomingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top));
        style.incomingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom));
        style.incomingTextColor = typedArray.getColor(R.styleable.MessagesList_incomingTextColor,
                style.getColor(R.color.dark_grey_two));
        style.incomingTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingTextSize,
                style.getDimension(R.dimen.message_text_size));
        style.incomingTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingTextStyle, Typeface.NORMAL);
        style.incomingTextFont = typedArray.getResourceId(R.styleable.MessagesList_incomingTextFont, -1);

        style.incomingTimeTextColor = typedArray.getColor(R.styleable.MessagesList_incomingTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.incomingTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.incomingTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingTimeTextStyle, Typeface.NORMAL);
        style.incomingTimeTextFont = typedArray.getResourceId(R.styleable.MessagesList_incomingTimeTextFont, -1);

        style.incomingImageTimeTextColor = typedArray.getColor(R.styleable.MessagesList_incomingTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.incomingImageTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_incomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.incomingImageTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_incomingTimeTextStyle, Typeface.NORMAL);
        style.incomingImageTimeTextFont = typedArray.getResourceId(R.styleable.MessagesList_incomingTimeTextFont, -1);

        style.outcomingBubbleDrawable = typedArray.getResourceId(R.styleable.MessagesList_outcomingBubbleDrawable, -1);
        style.outcomingDefaultBubbleColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubbleColor,
                style.getColor(R.color.cornflower_blue_two));
        style.outcomingDefaultBubblePressedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubblePressedColor,
                style.getColor(R.color.cornflower_blue_two));
        style.outcomingDefaultBubbleSelectedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24));

        style.outcomingImageOverlayDrawable = typedArray.getResourceId(R.styleable.MessagesList_outcomingImageOverlayDrawable, -1);
        style.outcomingDefaultImageOverlayPressedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent));
        style.outcomingDefaultImageOverlaySelectedColor = typedArray.getColor(R.styleable.MessagesList_outcomingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40));

        style.outcomingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left));
        style.outcomingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right));
        style.outcomingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top));
        style.outcomingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom));
        style.outcomingTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingTextColor,
                style.getColor(R.color.white));
        style.outcomingTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingTextSize,
                style.getDimension(R.dimen.message_text_size));
        style.outcomingTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingTextStyle, Typeface.NORMAL);
        style.outcomingTextFont = typedArray.getResourceId(R.styleable.MessagesList_outcomingTextFont, -1);

        style.outcomingTimeTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingTimeTextColor,
                style.getColor(R.color.white60));
        style.outcomingTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.outcomingTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingTimeTextStyle, Typeface.NORMAL);
        style.outcomingTimeTextFont = typedArray.getResourceId(R.styleable.MessagesList_outcomingTimeTextFont, -1);

        style.outcomingImageTimeTextColor = typedArray.getColor(R.styleable.MessagesList_outcomingTimeTextColor,
                style.getColor(R.color.warm_grey_four));
        style.outcomingImageTimeTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_outcomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size));
        style.outcomingImageTimeTextStyle = typedArray.getInt(R.styleable.MessagesList_outcomingTimeTextStyle, Typeface.NORMAL);
        style.outcomingImageTimeTextFont = typedArray.getResourceId(R.styleable.MessagesList_outcomingTimeTextFont, -1);

        style.dateHeaderPadding = typedArray.getDimensionPixelSize(R.styleable.MessagesList_dateHeaderPadding,
                style.getDimension(R.dimen.message_date_header_padding));
        style.dateHeaderFormat = typedArray.getString(R.styleable.MessagesList_dateHeaderFormat);
        style.dateHeaderTextColor = typedArray.getColor(R.styleable.MessagesList_dateHeaderTextColor,
                style.getColor(R.color.warm_grey_two));
        style.dateHeaderTextSize = typedArray.getDimensionPixelSize(R.styleable.MessagesList_dateHeaderTextSize,
                style.getDimension(R.dimen.message_date_header_text_size));
        style.dateHeaderTextStyle = typedArray.getInt(R.styleable.MessagesList_dateHeaderTextStyle, Typeface.NORMAL);

        typedArray.recycle();

        return style;
    }

    private MessagesListStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Drawable getMessageSelector(@ColorInt int normalColor, @ColorInt int selectedColor,
                                        @ColorInt int pressedColor, @DrawableRes int shape) {

        Drawable drawable = DrawableCompat.wrap(getVectorDrawable(shape)).mutate();
        DrawableCompat.setTintList(
                drawable,
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_selected},
                                new int[]{android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_pressed, -android.R.attr.state_selected}
                        },
                        new int[]{selectedColor, pressedColor, normalColor}
                ));
        return drawable;
    }

    public int getTextAutoLinkMask() {
        return textAutoLinkMask;
    }

    public int getIncomingTextLinkColor() {
        return incomingTextLinkColor;
    }

    public int getOutcomingTextLinkColor() {
        return outcomingTextLinkColor;
    }

    public int getIncomingAvatarWidth() {
        return incomingAvatarWidth;
    }

    public int getIncomingAvatarHeight() {
        return incomingAvatarHeight;
    }

    public int getIncomingDefaultBubblePaddingLeft() {
        return incomingDefaultBubblePaddingLeft;
    }

    public int getIncomingDefaultBubblePaddingRight() {
        return incomingDefaultBubblePaddingRight;
    }

    public int getIncomingDefaultBubblePaddingTop() {
        return incomingDefaultBubblePaddingTop;
    }

    public int getIncomingDefaultBubblePaddingBottom() {
        return incomingDefaultBubblePaddingBottom;
    }

    public int getIncomingTextColor() {
        return incomingTextColor;
    }

    public int getIncomingTextSize() {
        return incomingTextSize;
    }

    public int getIncomingTextStyle() {
        return incomingTextStyle;
    }

    public int getIncomingTextFont() {
        return incomingTextFont;
    }

    public Drawable getOutcomingBubbleDrawable() {
        if (outcomingBubbleDrawable == -1) {
            return getMessageSelector(outcomingDefaultBubbleColor, outcomingDefaultBubbleSelectedColor,
                    outcomingDefaultBubblePressedColor, R.drawable.chatkit_shape_outcoming_message);
        } else {
            return getDrawable(outcomingBubbleDrawable);
        }
    }

    public Drawable getOutcomingImageOverlayDrawable() {
        if (outcomingImageOverlayDrawable == -1) {
            return getMessageSelector(Color.TRANSPARENT, outcomingDefaultImageOverlaySelectedColor,
                    outcomingDefaultImageOverlayPressedColor, R.drawable.chatkit_shape_outcoming_message);
        } else {
            return getDrawable(outcomingImageOverlayDrawable);
        }
    }

    public int getOutcomingDefaultBubblePaddingLeft() {
        return outcomingDefaultBubblePaddingLeft;
    }

    public int getOutcomingDefaultBubblePaddingRight() {
        return outcomingDefaultBubblePaddingRight;
    }

    public int getOutcomingDefaultBubblePaddingTop() {
        return outcomingDefaultBubblePaddingTop;
    }

    public int getOutcomingDefaultBubblePaddingBottom() {
        return outcomingDefaultBubblePaddingBottom;
    }

    public int getOutcomingTextColor() {
        return outcomingTextColor;
    }

    public int getOutcomingTextSize() {
        return outcomingTextSize;
    }

    public int getOutcomingTextStyle() {
        return outcomingTextStyle;
    }

    public int getOutcomingTextFont() {
        return outcomingTextFont;
    }

    public int getOutcomingTimeTextColor() {
        return outcomingTimeTextColor;
    }

    public int getOutcomingTimeTextSize() {
        return outcomingTimeTextSize;
    }

    public int getOutcomingTimeTextStyle() {
        return outcomingTimeTextStyle;
    }
    public int getOutcomingTimeTextFont() {
        return outcomingTimeTextFont;
    }

    public int getOutcomingImageTimeTextColor() {
        return outcomingImageTimeTextColor;
    }

    public int getOutcomingImageTimeTextSize() {
        return outcomingImageTimeTextSize;
    }

    public int getOutcomingImageTimeTextStyle() {
        return outcomingImageTimeTextStyle;
    }

    public int getOutcomingImageTimeTextFont() {
        return outcomingImageTimeTextFont;
    }

    public int getDateHeaderTextColor() {
        return dateHeaderTextColor;
    }

    public int getDateHeaderTextSize() {
        return dateHeaderTextSize;
    }

    public int getDateHeaderTextStyle() {
        return dateHeaderTextStyle;
    }

    public int getDateHeaderPadding() {
        return dateHeaderPadding;
    }

    public String getDateHeaderFormat() {
        return dateHeaderFormat;
    }

    public int getIncomingTimeTextSize() {
        return incomingTimeTextSize;
    }

    public int getIncomingTimeTextStyle() {
        return incomingTimeTextStyle;
    }

    public int getIncomingTimeTextFont() {
        return incomingTimeTextFont;
    }

    public int getIncomingTimeTextColor() {
        return incomingTimeTextColor;
    }

    public int getIncomingImageTimeTextColor() {
        return incomingImageTimeTextColor;
    }

    public int getIncomingImageTimeTextSize() {
        return incomingImageTimeTextSize;
    }

    public int getIncomingImageTimeTextStyle() {
        return incomingImageTimeTextStyle;
    }

    public int getIncomingImageTimeTextFont() {
        return incomingImageTimeTextFont;
    }

    public Drawable getIncomingBubbleDrawable() {
        if (incomingBubbleDrawable == -1) {
            return getMessageSelector(incomingDefaultBubbleColor, incomingDefaultBubbleSelectedColor,
                    incomingDefaultBubblePressedColor, R.drawable.chatkit_shape_incoming_message);
        } else {
            return getDrawable(incomingBubbleDrawable);
        }
    }

    public Drawable getIncomingImageOverlayDrawable() {
        if (incomingImageOverlayDrawable == -1) {
            return getMessageSelector(Color.TRANSPARENT, incomingDefaultImageOverlaySelectedColor,
                    incomingDefaultImageOverlayPressedColor, R.drawable.chatkit_shape_incoming_message);
        } else {
            return getDrawable(incomingImageOverlayDrawable);
        }
    }

    public int getIncomingAvatar() {
        return incomingAvatar;
    }

    public int getPlayButton() {
        return playButton;
    }
}
