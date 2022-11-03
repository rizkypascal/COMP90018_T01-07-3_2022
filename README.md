# Unimelb H1 Hopper 

## Project Description
This is an Android project of Group T01/07-3 for COMP90018 Semester 2 2022.
Unimelb H1 Hopper is designed to boost the motivation and confidence level of the Unimelb students in accomplishing their studies and getting to know better about the assessment process in Unimelb. The first release focuses on Engineering and IT faculty.

**Project Demonstration Video:** https://youtu.be/MCzcQqkgXfE

## Project Structure

```
.
├── AndroidManifest.xml
├── ic_launcher-playstore.png
├── ic_username-playstore.png
├── java
│   └── com
│       └── example
│           └── android
│               └── gameapplication
│                   ├── GameActivity.java
│                   ├── GameContext.java
│                   ├── GameFragment.java
│                   ├── GameToolsFragment.java
│                   ├── GameToolsSelectionFragment.java
│                   ├── LoginAdapter.java
│                   ├── LoginTabFragment.java
│                   ├── MainActivity.java
│                   ├── SelectBeforeGameStartFragment.java
│                   ├── SelectWeekFragment.java
│                   ├── SignupTabFragment.java
│                   ├── UnionFragment.java
│                   ├── UserAfterLoginFragment.java
│                   ├── UserFragment.java
│                   ├── broadcast_receivers
│                   │   └── GameToolsBroadcastReceiver.java
│                   ├── databases
│                   │   └── Database.java
│                   ├── game_tools
│                   │   ├── BindingUtils.java
│                   │   ├── ClearMonsters.java
│                   │   ├── FlyItems.java
│                   │   ├── GameTools.java
│                   │   ├── GameToolsAdapter.java
│                   │   ├── GameToolsClickListener.java
│                   │   ├── GameToolsName.java
│                   │   └── SelectedGameToolsAdapter.java
│                   ├── games
│                   │   ├── Board.java
│                   │   ├── BombEffect.java
│                   │   ├── Bullet.java
│                   │   ├── CollisionUtils.java
│                   │   ├── FakeBoard.java
│                   │   ├── FireworkEffect.java
│                   │   ├── Jumper.java
│                   │   ├── Monster.java
│                   │   ├── MonsterType.java
│                   │   ├── OneTimeBoard.java
│                   │   ├── SpringBoard.java
│                   │   ├── StaticBoard.java
│                   │   └── Status.java
│                   ├── list_contents
│                   │   ├── ListAdapter.java
│                   │   └── ListTuple.java
│                   ├── placeholders
│                   │   └── PlaceholderContent.java
│                   └── sensors
│                       ├── LightMessage.java
│                       ├── LightSensor.java
│                       ├── OrientationMessage.java
│                       └── OrientationSensor.java
└── res
    ├── drawable
    │   ├── app_icon.png
    │   ├── bomb.gif
    │   ├── border.xml
    │   ├── border_white_color.xml
    │   ├── bottom_nav_background.xml
    │   ├── bottom_nav_color_selector.xml
    │   ├── circle_blue.xml
    │   ├── copter.gif
    │   ├── edit_text_bg.xml
    │   ├── fireworks.gif
    │   ├── ic_baseline_account_circle_24.xml
    │   ├── ic_baseline_games_24.xml
    │   ├── ic_baseline_security_24.xml
    │   ├── ic_baseline_verified_user_24.xml
    │   ├── ic_game.xml
    │   ├── ic_in_game_tools.xml
    │   ├── ic_launcher_background.xml
    │   ├── ic_union.xml
    │   ├── ic_user.xml
    │   ├── jumper1.png
    │   ├── main_background.xml
    │   ├── main_bg.png
    │   ├── meme.png
    │   ├── reborn.gif
    │   ├── rocket.gif
    │   ├── rounded_textview.xml
    │   ├── spinner_shape.xml
    │   ├── undersea.png
    │   └── vie_bg.xml
    ├── drawable-v24
    │   ├── background.png
    │   ├── background_dark.png
    │   ├── basic_board.jpg
    │   ├── basic_board_dark.jpg
    │   ├── book_board.png
    │   ├── book_board_dark.png
    │   ├── border_dark.xml
    │   ├── bottom_nav_background_night.xml
    │   ├── bottom_nav_color_selector_night.xml
    │   ├── bullet.png
    │   ├── exam.png
    │   ├── homework.png
    │   ├── ic_launcher_foreground.xml
    │   ├── jumperone.gif
    │   ├── jumperone_copter.gif
    │   ├── jumperone_rocket.gif
    │   ├── quiz.png
    │   ├── signup.jpg
    │   └── test.png
    ├── font
    │   └── games_scribble.ttf
    ├── layout
    │   ├── activity_game.xml
    │   ├── activity_main.xml
    │   ├── content_main.xml
    │   ├── dropdown.xml
    │   ├── fragment_game.xml
    │   ├── fragment_game_tools.xml
    │   ├── fragment_game_tools_selection.xml
    │   ├── fragment_log_in_tab.xml
    │   ├── fragment_select_week.xml
    │   ├── fragment_signup_tab.xml
    │   ├── fragment_union.xml
    │   ├── fragment_user.xml
    │   ├── fragment_user_after_login.xml
    │   ├── fragment_user_after_login_.xml
    │   ├── list_instance.xml
    │   ├── list_item.xml
    │   ├── list_selected_item.xml
    │   └── spinner_item.xml
    ├── menu
    │   ├── bottom_nav_menu.xml
    │   └── menu_main.xml
    ├── mipmap-hdpi
    │   ├── ic_launcher.png
    │   ├── ic_launcher_foreground.png
    │   └── ic_launcher_round.png
    ├── mipmap-mdpi
    │   ├── ic_launcher.png
    │   ├── ic_launcher_foreground.png
    │   └── ic_launcher_round.png
    ├── mipmap-xhdpi
    │   ├── ic_launcher.png
    │   ├── ic_launcher_foreground.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxhdpi
    │   ├── ic_launcher.png
    │   ├── ic_launcher_foreground.png
    │   └── ic_launcher_round.png
    ├── mipmap-xxxhdpi
    │   ├── ic_launcher.png
    │   ├── ic_launcher_foreground.png
    │   └── ic_launcher_round.png
    ├── navigation
    │   └── nav_graph.xml
    ├── raw
    │   ├── bomb.gif
    │   ├── erro.mp3
    │   ├── fireworks.gif
    │   ├── vista.mp3
    │   ├── winxp.mp3
    │   └── winxpshutdown.mp3
    ├── values
    │   ├── colors.xml
    │   ├── dimens.xml
    │   ├── integers.xml
    │   ├── strings.xml
    │   └── themes.xml
    ├── values-de
    │   └── strings.xml
    ├── values-fr
    │   └── strings.xml
    ├── values-in
    │   └── strings.xml
    ├── values-ja-rJP
    │   └── strings.xml
    ├── values-land
    │   └── dimens.xml
    ├── values-night
    │   └── themes.xml
    ├── values-w1240dp
    │   └── dimens.xml
    ├── values-w600dp
    │   └── dimens.xml
    ├── values-zh-rCN
    │   └── strings.xml
    └── xml
        ├── backup_rules.xml
        └── data_extraction_rules.xml
```

## Technologies
* Backend and Database: Firebase
* Frontend: Native Android (Java)
* Sensors: Accelerometer, Magnetometer, Light
* Additions: Localization (6 languages), Data Encryption (SHA256), Background Job Scheduler (AlarmManager & BroadcastReceiver)

## Project Build and Run
### Build Specifications
* Android Studio Dolphin | 2021.3.1
* Java SE 18
* Gradle
* Android SDK 21+

### Build and Run Instructions (Windows, Linux, and MacOS)
1. First of all, please clone this repository to your local. (Run this in PowerShell if you use Windows)
```
$ git clone git@github.com:rizkypascal/COMP90018_T01-07-3_2022.git
```
2. Open the Android Studio and open the cloned project. Wait until the Gradle project sync process is done.
3. Add the device emulator by clicking the "Tools" menu on the top, then click "Device Manager", and the Device Manager box on the right pane will show. Click "Create device" and follow the instruction.
4. Build the project by clicking the "Build" menu on the top, then click "Rebuild Project". Wait some time until the build process is done.
5. Run the project by clicking the "Run" menu on the top, then click "Run 'app'". Wait until the Gradle build is done, and the device emulator will launch your application.
6. This is an additional step. You also can run the application on your physical device by enabling USB debugging and plugging your physical device into your PC or laptop. Then run steps 4 and 5 again, and the application will be run on your physical device.

## Team Members
- Rizky Paskalis Totong
- Tianbo Shu
- Xueqing Li
- Yuhan Gu
- Runzhe Hua
- Changwen Li
