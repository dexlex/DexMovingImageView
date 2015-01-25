# ![Logo](/assets/images/dmiv.png) Dex Moving ImageView

DMIV aims to provide a flexible and customizable instrument for automated images moving on display. It provides scroll or time based moving. But you can create your own evaluator.

![Screenshot](/assets/images/dmiv_screenshot.gif)
![Screenshot](/assets/images/home.png)
![Screenshot](/assets/images/home.png)
![Screenshot](/assets/images/grid.png)
![Screenshot](/assets/images/list.png)
![Screenshot](/assets/images/menu.png)
![Screenshot](/assets/images/scroll.png)
![Screenshot](/assets/images/move_tester.png)
![Screenshot](/assets/images/move_tester_open.png)

# Features
 * Google Calendar ImageView
 * Google NewsStand Moving ImageView
 * Structured system to create your own objects or use the ones provided
 * Flexible way to create your own effect
 * Android 1.5+ support

# Downloads
[![Demo app](assets/images/android-app-on-google-play.png)](https://play.google.com/store/apps/details?id=it.dex.dexmovingimageview)

#References
* [Quick setup](https://github.com/dexlex/DexMovingImageView/wiki/Quinck-Setup)
* [Provided Resources](https://github.com/dexlex/DexMovingImageView/wiki/Provided-Resources)
* [Evaluators](https://github.com/dexlex/DexMovingImageView/wiki/Evaluators)
* [Values Generators](https://github.com/dexlex/DexMovingImageView/wiki/Values-Generators)
* [Drawers](https://github.com/dexlex/DexMovingImageView/wiki/Drawers)
* [FAQ](https://github.com/dexlex/DexMovingImageView/wiki/FAQ)
* [ChangeLog](https://github.com/dexlex/DexMovingImageView/wiki/ChangeLog)

# Usage

##Simple
###DexCrossFadeImageView
The view has 2 parameters:
1. TransitionDurationMillis (default: 300)
2. StillImageDurationMillis (default: 3000)
They both have their own getters and setters.
You can start a transition by calling the following methods:
```java
dexCrossFadeImageView.setFadingImageResource(R.drawable.my_image);
dexCrossFadeImageView.setFadingImageDrawable(myDrawable);
dexCrossFadeImageView.setFadingImageBitmap(myBitmap);
```

```xml
<it.dex.movingimageviewlib.DexCrossFadeImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:src="@drawable/material5"
        dex:images_array="@array/images"
        dex:loop="true"
        dex:still_image_duration_millis="1000"
        dex:transition_duration_millis="500" />
```

###DexMovigImageView

You can treat it like any other ImageView, but you need to add the following:
1.Evaluator
2.ValuesGenerator
3.Drawers

####Calendar-like View
This is the simplest way to use it. Simply add the following attributes to your view:

```xml
<it.dex.movingimageviewlib.DexMovingImageView
            android:id="@+id/image"
            style="@style/DexScrollImageViewStyle"
            dex:zoom="2"
            dex:drawer="scale|translate"
            dex:evaluator="timeBased"
            dex:generator="base"
            android:layout_height="match_parent"
            android:layout_centerInParent="true" />
```

####NewsStand-like View
This is the same as above, but with different evaluator and generator:

```xml
<it.dex.movingimageviewlib.DexMovingImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/material"
        dex:evaluator="timeBased"
        dex:images_array="@array/images"
        dex:generator="zoomed"
        dex:angle="130"
        dex:loop="true"
        dex:zoom="1.65"
        dex:drawer="scale|translate"
        dex:still_image_duration_millis="3000"
        dex:transition_duration_millis="1000" />
```

##Advanced
###Evaluators
###Values Generators
###Drawers

Credits
-------

Author: Diego Grancini (dexlex84@gmail.com)

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