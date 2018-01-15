# Lush Player (Mobile, Tablet & TV)


| Branch        | Status        |
| ------------- |:-------------:|
| `develop`     | [![Build Status](https://www.bitrise.io/app/b238aa77ecb45abd/status.svg?token=9lXKbf8tyylEWiJFS9Ddug&branch=develop)](https://www.bitrise.io/app/b238aa77ecb45abd) |
| `master`      | [![Build Status](https://www.bitrise.io/app/b238aa77ecb45abd/status.svg?token=9lXKbf8tyylEWiJFS9Ddug&branch=master)](https://www.bitrise.io/app/b238aa77ecb45abd) |

Codebase for the Lush Player app, allowing fans to browse and play Lush TV and radio media from its various channels, and to playback live Lush TV.

## Setup

Fill in all of the credentials from `local.properties.example` and rename the file to `local.properties`.

At the time of writing this includes:
- Brightcove Account ID (string)
- Brightcove Policy ID (string)
- Analytics Enabled (true/false)
- Google Analytics Key (string, requires analytics enabled to be true)

**Do not commit `local.properties` to source control.** If you make changes to the credentials provided, please update and commit `local.properties.example` with example credentials.

#### Live videos

If you wish to add live videos, you should [follow the process outlined in this documentation.](docs/brightcove/Brightcove.md)

## Testing

### App linking

When the user requests to view a url that starts with `http://player.lush.com/tv/` or `http://player.lush.com/radio/` (or its HTTPS variants), the app is registered to handle these links. It will launch the detail page with the relevant video based on the alias in the url.

An example of this would be:

```
http://player.lush.com/tv/conversation-john-robb-alexander-hacke-and-danielle-de-picciotto
```

To test this functionality you can use the following adb command:

```
adb shell am start -W -a android.intent.action.VIEW -d "http://player.lush.com/tv/conversation-john-robb-alexander-hacke-and-danielle-de-picciotto" com.cube.lush.player
```

## Phone Screenshots

<img src=".//screenshots/phone-home.png" height="507" width="274"> <img src=".//screenshots/phone-item.png" height="507" width="274">
<img src=".//screenshots/phone-live.png" height="507" width="274"> <img src=".//screenshots/phone-channels.png" height="507" width="274">
<img src=".//screenshots/phone-events.png" height="507" width="274"> <img src=".//screenshots/phone-search.png" height="507" width="274">

## Tablet Screenshots

<img src=".//screenshots/tablet-home.png" height="443" width="619">
<img src=".//screenshots/tablet-item.png" height="443" width="619">
<img src=".//screenshots/tablet-live.png" height="443" width="619">
<img src=".//screenshots/tablet-channels.png" height="443" width="619">
<img src=".//screenshots/tablet-events.png" height="443" width="619">
<img src=".//screenshots/tablet-search.png" height="443" width="619">

## TV Screenshots

<img src=".//screenshots/tv-menu.png" height="405" width="669">
<img src=".//screenshots/tv-home.png" height="405" width="669">
<img src=".//screenshots/tv-item.png" height="405" width="669">
<img src=".//screenshots/tv-channels.png" height="405" width="669">
<img src=".//screenshots/tv-search.png" height="405" width="669">