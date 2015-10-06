# Project 3 - Twitter client

 Twitter client is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: 15 hours spent in total

## User Stories

The following **required** functionality is completed:

* [1]	User can **sign in to Twitter** using OAuth login
* [2]	User can **view tweets from their home timeline**
  * [a] User is displayed the username, name, and body for each tweet
  * [b] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [c] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews). Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [3] User can **compose and post a new tweet**
  * [a] User can click a “Compose” icon in the Action Bar on the top right
  * [b] User can then enter a new tweet and post this to twitter
  * [c] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [1] User can **see a counter with total number of characters left for tweet** on compose tweet page

* [2] User can **pull down to refresh tweets timeline**

* [3] User can tap a tweet to **open a detailed tweet view**

* [4] Improve the user interface and theme the app to feel "twitter branded"
    Lots of improvements - Todo, done some

* [5] user can reply to tweet from the tweet detail view

The following **bonus** features are implemented:

* [5] Compose tweet functionality is build using modal overlay

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/rehan-0601/MySimpleTweets/blob/master/twitter_client.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Walkthrough for the 'reply to tweet from detail tweet view' feature
<img src='https://github.com/rehan-0601/MySimpleTweets/blob/master/reply_tweet.gif' title='Video Walkthrough-reply feature' width='' alt='Video Walkthrough' />


GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
