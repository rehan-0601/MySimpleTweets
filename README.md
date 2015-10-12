# Project 4 - *Twitter Client*

**Name of your app** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **15** hours spent in total

## User Stories

The following **required** functionality is completed:

* [1] The app includes **all required user stories** from Week 3 Twitter Client
* [2] User can **switch between Timeline and Mention views using tabs**
  * [a] User can view their home timeline tweets.
  * [b] User can view the recent mentions of their username.
* [3] User can navigate to **view their own profile**
  * [a] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [4] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [a] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [b] Profile view includes that user's timeline
* [5] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [1] User can view following / followers list through the profile
* [2] User can **"reply" to any tweet on their home timeline**
  * [a] The user that wrote the original tweet is automatically "@" replied in compose
* [3] User can click on a tweet to be **taken to a "detail view"** of that tweet
 

The following **bonus** features are implemented:
None

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/rehan-0601/MySimpleTweets/blob/version_advanced/reply_tweet.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

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
