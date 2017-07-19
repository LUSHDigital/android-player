# Lush Player (Mobile, Tablet & TV)


| Branch        | Status        |
| ------------- |:-------------:|
| `develop`     | [![Build Status](https://www.bitrise.io/app/b238aa77ecb45abd/status.svg?token=9lXKbf8tyylEWiJFS9Ddug&branch=develop)](https://www.bitrise.io/app/b238aa77ecb45abd) |
| `master`      | [![Build Status](https://www.bitrise.io/app/b238aa77ecb45abd/status.svg?token=9lXKbf8tyylEWiJFS9Ddug&branch=master)](https://www.bitrise.io/app/b238aa77ecb45abd) |

Codebase for the Lush Player app, allowing fans to browse and play Lush TV and radio media from its various channels, and to playback live Lush TV.

## Setup

### Brightcove

You will need to supply a Brightcove account ID and policy key via a  `gradle.properties` file.

#### Finding/creating your `gradle.properties`

This file must exist in at least one of these places:

1. Your home directory at `~/.gradle/gradle.properties`

2. In the root of the project at `/gradle.properties`

If the file doesn't exist, create it. I personally recommend the home directory, but that is due to my own personal setup.

*Please remember that properties in `gradle.properties` in the root of the project takes priority over those set in the home directory*

#### `gradle.properties` contents

Your `gradle.properties` file should look like this:

```
# Other settings...

# Brightcove
BRIGHTCOVE_ACCOUNT_ID=123456789
BRIGHTCOVE_POLICY_KEY=hEl1OW0rld!
```

*Please note that the values for account id and policy key are dummy values to give you an idea of how it should be used*

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

<img src=".//screenshots/phone-home.png" height="507" width="274">
<img src=".//screenshots/phone-item.png" height="507" width="274">
<img src=".//screenshots/phone-live.png" height="507" width="274">
<img src=".//screenshots/phone-channels.png" height="507" width="274">
<img src=".//screenshots/phone-events.png" height="507" width="274">
<img src=".//screenshots/phone-search.png" height="507" width="274">

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