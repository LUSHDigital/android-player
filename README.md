# Lush Player (Mobile, Tablet & TV)


| Branch        | Status        |
| ------------- |:-------------:|
| `develop`     | [![Build Status](https://www.bitrise.io/app/5d4e19c069f33be7/status.svg?token=5heq39U7s-QsSuHzShb53w&branch=develop)](https://www.bitrise.io/app/5d4e19c069f33be7) |
| `master`      | [![Build Status](https://www.bitrise.io/app/5d4e19c069f33be7/status.svg?token=5heq39U7s-QsSuHzShb53w&branch=master)](https://www.bitrise.io/app/5d4e19c069f33be7) |

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

## Screenshots

![Main menu](.//screenshots/main.png "Main menu")

![Channels](.//screenshots/channels.png "Channels")

![Media details](./screenshots/details.png "Media details")