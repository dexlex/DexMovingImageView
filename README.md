# ![Logo](https://github.com/dexlex/DexMovingImageView/raw//master/ic_launcher.png) Dex Moving ImageView

DMIV aims to provide a flexible and customizable instrument for automated images moving on display. It provides scroll or time based moving. But you can create your own evaluator.

![Screenshot](https://github.com/dexlex/DexMovingImageView/raw/master/dmiv_screenshot.gif)

## Features
 * Google Calendar ImageView
 * Google NewsStand Moving ImageView
 * Structured system to create your own objects or use the ones provided
 * Flexible way to create your own effect
 * Android 1.5+ support

## Downloads
[![Demo app](https://github.com/dexlex/DexMovingImageView/blob/master/android-app-on-google-play.png)](https://play.google.com/store/apps/details?id=it.dex.movingimageview)

##References
* [Quick setup](https://github.com/dexlex/DexMovingImageView/wiki/Quinck-Setup)
* [Provided Resources](https://github.com/dexlex/DexMovingImageView/wiki/Provided-Resources)
* [Evaluators](https://github.com/dexlex/DexMovingImageView/wiki/Evaluators)
* [Values Generators](https://github.com/dexlex/DexMovingImageView/wiki/Values-Generators)
* [Drawers](https://github.com/dexlex/DexMovingImageView/wiki/Drawers)
* [FAQ](https://github.com/dexlex/DexMovingImageView/wiki/FAQ)
* [ChangeLog](https://github.com/dexlex/DexMovingImageView/wiki/ChangeLog)

## Usage

###Simple
####DexCrossFadeImageView
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

```java
DexCrossFadeImageView dexCrossFadeImageView = (DexCrossFadeImageView) view.findViewById(R.id.image);
dexCrossFadeImageView.setFadingImageResource(R.drawable.my_image);
```

####DexMovigImageView

###Advanced
####Evaluators
####Values Generators
####Drawers

## Donation
You can support the project by donating what you want using PayPal

<div class="clients">
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="hosted_button_id" value="JBJ7MKZ3U5KBY">
<input type="image" src="https://www.paypalobjects.com/en_US/GB/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal – The safer, easier way to pay online.">
<img alt="" border="0" src="https://www.paypalobjects.com/it_IT/i/scr/pixel.gif" width="1" height="1">
</form></div>


## License

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