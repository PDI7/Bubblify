# Bubblify

## Introduction

TODO: Add brief introduction.

TODO: Add image that helps to understand the project.
This could be an architectural diagram or a screenshot of the application.

## Architecture Overview (optional)

```mermaid
graph TD
  subgraph bubblify
    subgraph common
      StringExt.kt
    end

    subgraph data
    end

    subgraph model
      Activity.kt
      ActivityIcon.kt
      Group.kt
      Reference.kt
      Resource.kt
      User.kt
      UserGroup.kt
      UserGroupState.kt
    end

    subgraph service
      AccountService.kt
      StorageService.kt
      subgraph modul
        FirebaseModule.kt
        ServiceModule.kt
      end
    end

    subgraph ui
      subgraph theme
        Color.kt
        Theme.kt
        Type.kt
      end

      subgraph view
        AddActivitiesPage.kt
        AddMembersPage.kt
        BubblePage.kt
        GroupSettingsPage.kt
        HomePage.kt
        LoginPage.kt
        MorePage.kt
        ProfilePage.kt
        SetActivityPage.kt
        SignUpPage.kt

        

        subgraph common
          ButtonComposable.kt
          ModifierExt.kt
          NavigationComposable.kt
          TextFieldComposable.kt
          TitleComposable.kt
        end
      end
    end

    subgraph viewmodel
      AddActivitiesViewModel.kt
      AddMembersViewModel.kt
      BubbleViewModel.kt
      GroupSettingsViewModel.kt
      HomeViewModel.kt
      LoginViewModel.kt
      MoreViewModel.kt
      ProfileViewModel.kt
      SetActivityViewModel.kt
      SignUpViewModel.kt

      subgraph state
        GroupState.kt
        LoginUiState.kt
        SignUpUiState.kt
      end
    end

    subgraph links
      BubblifyApp.kt
      BubblifyHiltApp.kt
      MainActivity.kt
      MainState.kt

      StorageService.kt --> AddActivitiesViewModel.kt
      StorageService.kt --> AddMembersViewModel.kt
      StorageService.kt --> BubbleViewModel.kt
      StorageService.kt --> GroupSettingsViewModel.kt
      StorageService.kt --> HomeViewModel.kt
      StorageService.kt --> LoginViewModel.kt
      StorageService.kt --> MoreViewModel.kt
      StorageService.kt --> ProfileViewModel.kt
      StorageService.kt --> SetActivityViewModel.kt
      StorageService.kt --> SignUpViewModel.kt
      AccountService.kt -->|uses| StorageService.kt
      AddActivitiesPage.kt -->|uses| AddActivitiesViewModel.kt
        AddMembersPage.kt -->|uses| AddMembersViewModel.kt
        BubblePage.kt -->|uses| BubbleViewModel.kt
        GroupSettingsPage.kt -->|uses| GroupSettingsViewModel.kt
        HomePage.kt -->|uses| HomeViewModel.kt
        LoginPage.kt -->|uses| LoginViewModel.kt
        MorePage.kt -->|uses| MoreViewModel.kt
        ProfilePage.kt -->|uses| ProfileViewModel.kt
        SetActivityPage.kt -->|uses| SetActivityViewModel.kt
        SignUpPage.kt -->|uses| SignUpViewModel.kt
    end
    
  end
  subgraph firebase
    FirebaseDatabase
  end
  FirebaseModule.kt -->|connects to| FirebaseDatabase
  FirebaseModule.kt -->|provides| AccountService.kt
  FirebaseModule.kt -->|provides| StorageService.kt

```
### Folder Structure
Folder structure of the project
```
bubblify/
├───app/
│   ├───build/
│   └───src/
│       ├───androidTest/      
│       ├───main/
│       │   ├───java/com/example/
│       │   │   └───bubblify/
│       │   │       │   BubblifyApp.kt        <-- Routes/NavigationHost
│       │   │       │   BubblifyHiltApp.kt    <-- ??
│       │   │       │   MainActivity.kt       <-- Main file that be launched
│       │   │       │   MainState.kt          <-- ??
│       │   │       │
│       │   │       ├───common/           <-- Checking fields input
│       │   │       │
│       │   │       ├───data/            
│       │   │       ├───model/            <-- All the data layer
│       │   │       │
│       │   │       ├───service/          <-- service to interact with database (get/delete...)
│       │   │       │
│       │   │       ├───ui/               <-- all the defaults color, ui elements...
│       │   │       │   └───theme/
│       │   │       │
│       │   │       ├───view/             <-- all the view's (UI part)
│       │   │       │   └───common/       <-- some componants that can be used in View's
│       │   │       │
│       │   │       └───viewmodel/        <-- all the ViewModels (Fetching methods synchronized with the View)
│       │   │           └───state/
│       │   └───res/
│       └───test/
├───build/
├───gradle/
└───resources/
```

## How to Use

### Prerequisites

- Checkout repo
- Open project in Android Studio
- Have access to the [Bubblify Firebase Project](https://console.firebase.google.com/u/0/project/bubblify-226b3)

### Gradle Build

> .\gradlew build

### Test

> .\gradlew app:testReleaseUnitTest

or
> .\gradlew app:testDebugUnitTest

### Setup Firebase connection

- Download the `google-services.json` file from the [Firebase settings](https://console.firebase.google.com/u/0/project/bubblify-226b3/settings/general/android:com.example.bubblify)
- Add it in the `/app` folder. 

![project-structure-with-google-service-json.png](resources/project-structure-with-google-service-json.png)

### Run

The app can only be run on an android device.
You can run it on an Android emulator or personal android device.

- Go to the "Device Manager"
- Select a virtual or physical device
- Run the app with the blue arrow on top in Android Studio or press "Shift + F10"

![run-app-android-studio.png](resources/run-app-android-studio.png)


For further information consult the docs:
https://developer.android.com/studio/run

## License

Copyright (c) 2023 Bubblify

This work is licensed under [MIT License](./LICENSE).
