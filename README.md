# ![Logo](/assets/images/dmiv.png) DexMovingImageView

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-DexMovingImageView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1601)

DMIV aims to provide a flexible and customizable instrument for automated images moving on display. It provides scroll, gyroscope or time based moving. But you can create your own evaluator.

![Screenshot](/assets/images/dmiv_screenshot.gif)

# Features
 * Google Calendar ImageView
 * Google NewsStand Moving ImageView
 * Structured system to create your own objects or use the ones provided
 * Flexible way to create your own effect
 * Android 1.5+ support
 
# Upcoming Changes
 * Ken Burns Effect

# Downloads
[![Demo app](assets/images/android-app-on-google-play.png)](https://play.google.com/store/apps/details?id=it.dex.dexmovingimageview)

![Screenshot](/assets/screenshot/home.png)
![Screenshot](/assets/screenshot/grid.png)
![Screenshot](/assets/screenshot/menu.png)
![Screenshot](/assets/screenshot/scroll.png)
![Screenshot](/assets/screenshot/move_tester.png)
![Screenshot](/assets/screenshot/move_tester_open.png)



# Setup

DexMovingImageView is pushed to Maven Central as an AAR. Add the following to build.gradle.

```gradle
    dependencies {
        compile 'it.dex.dexmovingimageview:dexmovingimageviewlib:0.1.0'
    }
```

or the following using Maven:

```xml
    <dependency>
        <groupid>it.dex.dexmovingimageview</groupid>
        <artifactid>dexmovingimageviewlib</artifactid>
        <version>0.1.0</version>
        <type>aar</type>
    </dependency>
```

# Usage

##Simple

This section show you how to use the provided views without questioning how it works.

This library provides 2 views:

* **DexCrossFadeImageView**: It allows you to add a fading transition when changing the source drawable with specified transition duration in milliseconds. It also provides a way to slideshow multiple images;
* **DexMovingImageView**: It extends the DexCrossFadeImageView, so it inherits every property from it and add lots of new features.

###DexCrossFadeImageView
The view has 2 parameters:

 * TransitionDurationMillis (default: 300)
 * StillImageDurationMillis (default: 3000)

They both have their own getters and setters.
####Single Image
First of all you can set your timings, if needed. Then you can start a transition by calling the following methods:

```java
    dexCrossFadeImageView.setFadingImageResource(R.drawable.my_image);
    dexCrossFadeImageView.setFadingImageDrawable(myDrawable);
    dexCrossFadeImageView.setFadingImageBitmap(myBitmap);
```
####Multiple Images
Or you can set a List or an array of:

 * Resources (integers)
 * Drawables
 * Bitmaps

and then call the start() or start(int transitionDurationMillis, int stillImageTransitionMillis) methods.
You can do the same using the images_array attribute:

```xml
    <it.dex.movingimageviewlib.DexCrossFadeImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:images_array="@array/my_images"
        dex:loop="true"
        dex:still_image_duration_millis="1000"
        dex:transition_duration_millis="500" />
```

You can pause the transition by calling the pause() method.

###DexMovigImageView

You can treat it like any other ImageView or DexCrossFadeImageView, but you need to add the following to set the desired behaviour:

* Evaluator
* ValuesGenerator
* Drawers

The next two subsections show you how to add two popular view in your app.

####Calendar-like View
This is the simplest way to use it. Simply add the following attributes to your view:

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        dex:zoom="2.0"
        dex:evaluator="scrollBased"
        dex:generator="base"
        dex:drawer="scale|translate" />
```

Change zoom value to increase or decrease scaling: this way you can enhance the scrolling effect.

####NewsStand-like View
This is the same as above, but with different evaluator and generator:

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        dex:evaluator="timeBased"
        dex:images_array="@array/my_images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

Use the OnEventOccurred interface to listen to every change during execution. You can set a new CrossFading image or change tint or change angle, etc. The events returned are:

1. START
2. FIRST QUARTER
3. MIDDLE
4. THIRD QUARTER
5. END

```java
    @Override
    public void onEventOccurred(View view, Evaluator evaluator, Evaluator.EVENT_STATUS eventStatus, int occurrenceCount) {
        switch (eventStatus) {
            case FIRST_QUARTER:
                //Your code here:
                break;
            case THIRD_QUARTER:
                //Your code here:
                break;
            case END:
                //Your code here:
                break;
            case MIDDLE:
                //Your code here:
                break;
        }
    }
```

##Advanced
This section show you how to add any new unimplemented feature.
First af all you need to know how it works: a DexMovingImageView is composed by 3 main elements that describe how to do something:

1. **Evaluators** (only one at a time): they describe **what originates the basic values**;
2. **Values Generators** (only one at a time): they describe **how to modify the basic values** coming from evaluators:
3. **Drawers** (one or more): they describe **what property change must be applied** to the view.

Everytime the view is invalidated, the evaluator evaluate new basic values, ValuesGenerator changes them and drawer apply the new values to the view.

###Evaluators

Currently there are 4 kind of evaluator in this library:

* **Simple**: It always returns the default values, so the single property can be applied based on what drawers you use;
* **Scroll**: It returns the position of the view relative to the display (the left top corner coordinates);
* **Time**: It returns increasing values from 0 to 360, based on a speed values;
* **Gyroscope**: It returns x, y, z values from Gyroscope.

You can create your own Evaluator by extending the Evaluator class and implementing the following methods:

```java
    @Override
    protected void onCreate(View view) {
        //Your Code here
    }

    @Override
    public float evaluateX(View view) {
        //Your Code here
        return x;
    }

    @Override
    public float evaluateY(View view) {
        //Your Code here
        return y;
    }

    @Override
    protected void onDestroy(View view) {
        //Your Code here
    }
```

An evaluator has its lifecycle, so you can optimize the use of any external services or API you use: For example: for the TimeEvaluator a TimerTask is
started in the onCreate() method and it's stopped in the onDestroy() one. You don't have to worry about calling this methods because the superclass does
it for you. If you need to start or stop those APIs you used, you can call the start() and stop() methods.

The following methods are not abstract and their default implementation returns the default values set to the view:

```java
    @Override
    public float evaluateAngle(View view, float defaultAngle) {
        //Your Code here
    }

    @Override
    public float evaluateZoom(View view, float defaultZoom) {
        //Your Code here
    }
```

You should also find a way to call the OnEventOccurred implementation:

```java
    if (getOnEventOccurred() != null && isNotifyEvent()) {
        if (someClause) {
            getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.END, ++endLoopCount);
        } else if (someOtherClause) {
            getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.START, ++startLoopCount);
        } else if (someOtherClause) {
            getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.MIDDLE, ++middleLoopCount);
        } else if (someOtherClause) {
            getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.FIRST_QUARTER, ++firstQuarterLoopCount);
        } else if (someOtherClause) {
            getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.THIRD_QUARTER, ++secondQuarterLoopCount);
        }
    }
```

A DexMovingImageView has one only Evaluator, so you can specify the enum value or the complete class path inside xml file:

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:evaluator="timeBased"
        dex:images_array="@array/my_images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

or

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:evaluator="it.dex.movingimageview.MyEvaluator"
        dex:images_array="@array/my_images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

###Values Generators

The provided values generators are:

* **Base**: It generates x and y values;
* **Angled**: It generates the same Base values, plus Angle values;
* **ZoomedAngled**: It generates the same as Angled, but it generates zoom values too.

A Generator deals with manipulating values to return the desired one using some sort of logical pattern. To create your own Generator your class
must extend the ValuesGenerator one and implement the following methods:

```java
    @Override
    public float getX(float x) {
        //Your Code here
        return newX;
    }

    @Override
    public float getY(float y) {
        //Your Code here
        return newY;
    }

    @Override
    public float getAngle(float angle, float defaultAngle) {
        //Your Code here
        return newAngle;
    }

    @Override
    public float getZoom(float zoom, float defaultZoom) {
        //Your Code here
        return newZoom;
    }
```

A DexMovingImageView has one only ValuesGenerator, so you can specify the enum value or the complete class path inside xml file:

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:evaluator="timeBased"
        dex:images_array="@array/my_images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

or

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:evaluator="timeBased"
        dex:images_array="@array/my_images"
        dex:generator="it.dex.movingimageview.MyValuesGenerator"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

###Drawers

Three properties can be modified based on the generated values. So you can use the following:

* **Scale**: It scales the view content using "zoom" value in both width and height;
* **Translate**: It translate the view inside its container using x and y values returned by ValuesGenerator;
* **Rotate**: It rotate the view using "angle" value.

A DexMovingImageView can have one or more Drawers, so you can specify the values inside xml file:

```xml
    <it.dex.movingimageviewlib.DexMovingImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/my_image"
        dex:evaluator="timeBased"
        dex:images_array="@array/my_images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|rotate|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

Credits
-------

Author: Diego Grancini (dexlex84@gmail.com)

Any hint, suggestion, improvement or comment will be appreciated

<a href="https://plus.google.com/u/0/+DiegoGrancini/posts">
  <img alt="Follow me on Google+"
       src="/assets/images/google.png" />
</a>
<a href="https://twitter.com/DiegoGrancini">
  <img alt="Follow me on Twitter"
       src="/assets/images/twitter.png" />
</a>
<a href="http://it.linkedin.com/in/diegograncini">
      <img alt="Follow me on LinkedIn"
           src="/assets/images/linkedin.png" />
</a>
<a href="https://github.com/dexlex/DexMovingImageView">
     <img alt="Follow me on GitHub"
           src="/assets/images/github.png" />
</a>


# License

    Copyright 2014-2015 Diego Grancini

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.