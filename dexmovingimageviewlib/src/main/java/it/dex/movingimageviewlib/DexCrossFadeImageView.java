/*
 * Copyright 2014-2015 Diego Grancini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.dex.movingimageviewlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.List;

/**
 * DexCrossFadeImageView created by Diego on 28/12/2014.
 */
public class DexCrossFadeImageView extends ImageView {
    private int transitionDurationMillis = 300;
    private int stillImageDurationMillis = 3000;
    private int currentPosition = 0;
    private boolean loop = false;
    private Drawable[] drawables;
    private boolean play = false;

    public DexCrossFadeImageView(Context context) {
        this(context, null);
    }

    public DexCrossFadeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DexCrossFadeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DexCrossFadeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DexCrossFadeImageView, 0, 0);
            setTransitionDurationMillis(a.getInt(R.styleable.DexCrossFadeImageView_transition_duration_millis, transitionDurationMillis));
            setStillImageDurationMillis(a.getInt(R.styleable.DexCrossFadeImageView_still_image_duration_millis, stillImageDurationMillis));
            setLoop(a.getBoolean(R.styleable.DexCrossFadeImageView_loop, loop));
            int intArrayRes = a.getResourceId(R.styleable.DexCrossFadeImageView_images_array, 0);
            if (intArrayRes != 0) {
                TypedArray images = getResources().obtainTypedArray(intArrayRes);
                drawables = new Drawable[images.length()];
                for (int i = 0; i < images.length(); i++)
                    drawables[i] = images.getDrawable(i);
                images.recycle();
                start();
            }
            a.recycle();
        }
    }

    private void play() {
        if (isPlaying())
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (drawables.length > currentPosition) {
                        setFadingImageDrawable(drawables[currentPosition]);
                        currentPosition++;
                        play();
                    } else {
                        currentPosition = 0;
                        if (isLoop())
                            play();
                    }
                }
            }, stillImageDurationMillis);
    }

    public void start() {
        if (!isPlaying()) {
            setPlaying(true);
            play();
        }
    }

    public void pause() {
        setPlaying(false);
    }

    public void start(int transitionDurationMillis, int stillImageDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setStillImageDurationMillis(stillImageDurationMillis);
        start();
    }

    public void setFadingImageDrawable(Drawable drawable) {
        Drawable currentDrawable = getDrawable();
        if ((currentDrawable != null) && (currentDrawable instanceof TransitionDrawable)) {
            currentDrawable = ((TransitionDrawable) currentDrawable).getDrawable(1);
        }
        if (currentDrawable != null) {
            Drawable[] arrayDrawable = new Drawable[2];
            arrayDrawable[0] = currentDrawable;
            arrayDrawable[1] = drawable;
            TransitionDrawable transitionDrawable = new TransitionDrawable(arrayDrawable);
            transitionDrawable.setCrossFadeEnabled(true);
            setImageDrawable(transitionDrawable);
            transitionDrawable.startTransition(transitionDurationMillis);
        } else {
            setImageDrawable(drawable);
        }
    }

    public void setImageDrawables(List<Drawable> drawablesList) {
        if (drawablesList != null) {
            Drawable[] drawables = new Drawable[drawablesList.size()];
            drawablesList.toArray(drawables);
            setImageDrawables(drawables);
        }
    }

    public void setImageBitmaps(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            Drawable[] drawables = new Drawable[bitmaps.length];
            for (int i = 0; i < bitmaps.length; i++)
                drawables[i] = new BitmapDrawable(getResources(), bitmaps[i]);
            setImageDrawables(drawables);
        }
    }

    public void setImageBitmaps(List<Bitmap> bitmapsList) {
        if (bitmapsList != null) {
            Bitmap[] drawables = new Bitmap[bitmapsList.size()];
            bitmapsList.toArray(drawables);
            setImageBitmaps(drawables);
        }
    }

    public void setImageResources(int[] resources) {
        if (resources != null) {
            Drawable[] drawables = new Drawable[resources.length];
            for (int i = 0; i < resources.length; i++)
                drawables[i] = getResources().getDrawable(resources[i]);
            setImageDrawables(drawables);
        }
    }

    public void setImageResources(List<Integer> resourcesList) {
        if (resourcesList != null) {
            int[] drawables = new int[resourcesList.size()];
            for (Integer integer : resourcesList)
                drawables[resourcesList.indexOf(integer)] = integer.intValue();
            setImageResources(drawables);
        }
    }

    public void setFadingImageDrawable(Drawable drawable, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageDrawable(drawable);
    }

    public void setFadingImageResource(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        setFadingImageBitmap(bitmap);
    }

    public void setFadingImageResource(int res, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageResource(res);
    }

    public void setFadingImageBitmap(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        setFadingImageDrawable(bitmapDrawable);
    }

    public void setFadingImageBitmap(Bitmap bitmap, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageBitmap(bitmap);
    }

    public void setTransitionDurationMillis(int transitionDurationMillis) {
        this.transitionDurationMillis = transitionDurationMillis;
    }

    public void setStillImageDurationMillis(int stillImageDurationMillis) {
        this.stillImageDurationMillis = stillImageDurationMillis;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public Drawable[] getImageDrawables() {
        return drawables;
    }

    public void setImageDrawables(Drawable[] drawables) {
        this.drawables = drawables;
    }

    public boolean isPlaying() {
        return play;
    }

    public void setPlaying(boolean play) {
        this.play = play;
    }
}
