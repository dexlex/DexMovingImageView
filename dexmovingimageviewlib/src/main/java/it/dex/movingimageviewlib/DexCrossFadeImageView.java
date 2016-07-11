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
 * This view provides a simple way to add a transition from an image to another. It can handle images as Bitmaps, Drawables and resources.
 * <p/>
 * DexCrossFadeImageView created by Diego Grancini on 28/12/2014.
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
            setTransitionDurationMillis(a.getInt(R.styleable.DexCrossFadeImageView_dex_transition_duration_millis, transitionDurationMillis));
            setStillImageDurationMillis(a.getInt(R.styleable.DexCrossFadeImageView_dex_still_image_duration_millis, stillImageDurationMillis));
            setLoop(a.getBoolean(R.styleable.DexCrossFadeImageView_dex_loop, loop));
            int intArrayRes = a.getResourceId(R.styleable.DexCrossFadeImageView_dex_images_array, 0);
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

    /**
     * Start a slideshow using the list or the array of images previously set
     */
    public void start() {
        if (!isPlaying()) {
            setPlaying(true);
            play();
        }
    }

    /**
     * Start a slideshow using the list or the array of images previously set
     *
     * @param transitionDurationMillis duration in milliseconds of a transition
     * @param stillImageDurationMillis duration in milliseconds of a single image
     */
    public void start(int transitionDurationMillis, int stillImageDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setStillImageDurationMillis(stillImageDurationMillis);
        start();
    }

    /**
     * Pause a previously started slideshow
     */
    public void pause() {
        setPlaying(false);
    }

    /**
     * Get the set array of Drawables
     *
     * @return set array of Drawables
     */
    public Drawable[] getImageDrawables() {
        return drawables;
    }

    public void setImageDrawables(Drawable[] drawables) {
        this.drawables = drawables;
    }

    /**
     * Set a list of Drawables for a slideshow. Then call DexCrossImageView.start()
     *
     * @param drawablesList the list of Drawables to set
     */
    public void setImageDrawables(List<Drawable> drawablesList) {
        if (drawablesList != null) {
            Drawable[] drawables = new Drawable[drawablesList.size()];
            drawablesList.toArray(drawables);
            setImageDrawables(drawables);
        }
    }

    /**
     * Set an array of Bitmaps for a slideshow. Then call DexCrossImageView.start()
     *
     * @param bitmaps the list of Bitmaps to set
     */
    public void setImageBitmaps(Bitmap[] bitmaps) {
        if (bitmaps != null) {
            Drawable[] drawables = new Drawable[bitmaps.length];
            for (int i = 0; i < bitmaps.length; i++)
                drawables[i] = new BitmapDrawable(getResources(), bitmaps[i]);
            setImageDrawables(drawables);
        }
    }

    /**
     * Set a list of Bitmaps for a slideshow. Then call DexCrossImageView.start()
     *
     * @param bitmapsList the list of Bitmaps to set
     */
    public void setImageBitmaps(List<Bitmap> bitmapsList) {
        if (bitmapsList != null) {
            Bitmap[] drawables = new Bitmap[bitmapsList.size()];
            bitmapsList.toArray(drawables);
            setImageBitmaps(drawables);
        }
    }

    /**
     * Set an array of drawable integer resource references for a slideshow. Then call DexCrossImageView.start()
     *
     * @param resources the array of integer to set
     */
    public void setImageResources(int[] resources) {
        if (resources != null) {
            Drawable[] drawables = new Drawable[resources.length];
            for (int i = 0; i < resources.length; i++)
                drawables[i] = getResources().getDrawable(resources[i]);
            setImageDrawables(drawables);
        }
    }

    /**
     * Set a list of drawable integer resource references for a slideshow. Then call DexCrossImageView.start()
     *
     * @param resourcesList the list of integer to set
     */
    public void setImageResources(List<Integer> resourcesList) {
        if (resourcesList != null) {
            int[] drawables = new int[resourcesList.size()];
            for (Integer integer : resourcesList)
                drawables[resourcesList.indexOf(integer)] = integer.intValue();
            setImageResources(drawables);
        }
    }

    /**
     * Start a transition to the new image
     *
     * @param drawable the drawable to set
     */
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

    /**
     * Start a transition to the new image
     *
     * @param drawable                 the drawable to set
     * @param transitionDurationMillis the milliseconds the transition takes
     */
    public void setFadingImageDrawable(Drawable drawable, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageDrawable(drawable);
    }

    /**
     * Start a transition to the new image
     *
     * @param res the drawable resource to set
     */
    public void setFadingImageResource(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        setFadingImageBitmap(bitmap);
    }

    /**
     * Start a transition to the new image
     *
     * @param res                      the drawable resource to set
     * @param transitionDurationMillis the milliseconds the transition takes
     */
    public void setFadingImageResource(int res, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageResource(res);
    }

    /**
     * Start a transition to the new image
     *
     * @param bitmap the bitmap resource to set
     */
    public void setFadingImageBitmap(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        setFadingImageDrawable(bitmapDrawable);
    }

    /**
     * Start a transition to the new image
     *
     * @param bitmap                   the bitmap resource to set
     * @param transitionDurationMillis the milliseconds the transition takes
     */
    public void setFadingImageBitmap(Bitmap bitmap, int transitionDurationMillis) {
        setTransitionDurationMillis(transitionDurationMillis);
        setFadingImageBitmap(bitmap);
    }

    /**
     * Set the duration of a transition in milliseconds
     *
     * @param transitionDurationMillis
     */
    public void setTransitionDurationMillis(int transitionDurationMillis) {
        this.transitionDurationMillis = transitionDurationMillis;
    }

    /**
     * Set the duration of a still image in milliseconds
     *
     * @param stillImageDurationMillis
     */
    public void setStillImageDurationMillis(int stillImageDurationMillis) {
        this.stillImageDurationMillis = stillImageDurationMillis;
    }

    /**
     * Is loop for a slideshow enabled
     *
     * @return true if the loop is enabled, false otherwise
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Set if the loop for a slideshow is enabled
     *
     * @param loop Loop enabled
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Check if a slideshow id playing
     *
     * @return true if a slideshow is playing, false otherwise
     */
    public boolean isPlaying() {
        return play;
    }

    /**
     * Set if the current slideshow should be paused or restarted
     *
     * @param play the status to be set to the slideshow
     */
    public void setPlaying(boolean play) {
        this.play = play;
    }
}
