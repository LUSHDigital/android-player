# Lush Player TV/Mobile

| Branch        | Status        |
| ------------- |:-------------:|
| `develop`     | [![Build Status](https://www.bitrise.io/app/5d4e19c069f33be7/status.svg?token=5heq39U7s-QsSuHzShb53w&branch=develop)](https://www.bitrise.io/app/5d4e19c069f33be7) |
| `master`      | [![Build Status](https://www.bitrise.io/app/5d4e19c069f33be7/status.svg?token=5heq39U7s-QsSuHzShb53w&branch=master)](https://www.bitrise.io/app/5d4e19c069f33be7) |

Codebase for the Lush Player app, allowing fans to browse and play Lush TV and radio media from its various channels, and to playback live Lush TV.

The API and model objects for interacting with the Lush servers are in the `lib/` submodule. 
Documentation can be found [here]("lib/README.md").

## Setup

* You will need to supply a Brightcove account ID and policy key in the corresponding fields in `lib/src/main/res/values/strings.xml`
* Run with `./gradlew assembleDebug`

## Screenshots

![Main menu](.//screenshots/main.png "Main menu")

![Channels](.//screenshots/channels.png "Channels")

![Media details](./screenshots/details.png "Media details")