# User Information App

Overview :-
User Information App is a simple yet clean Android application built using Jetpack Compose and MVVM architecture.
It fetches user data from a public API, displays it in a list, and allows the user to view detailed information for each profile.

The project demonstrates:
-Modular architecture (app + network)
-Clean separation of concerns (UI, ViewModel, Repository, Network)
-Dependency Injection using Hilt
-Modern Android development best practices

Features :-
- Fetches data from Fake JSON API
- Displays users with name, email, company, and photo etc.
- On click → Opens a Detail screen showing full profile
- Jetpack Compose UI — no XML layouts
- MVVM pattern using ViewModel + StateFlow
- Hilt for Dependency Injection
- Retrofit + OkHttp + Gson for networking
- Coil for image loading
- Unit & UI Tests written using JUnit and Compose UI Test

  Technology :-
- Language : Kotlin
- UI : Jetpack Compose, Material 3
- Architecture :	MVVM (ViewModel + Repository + StateFlow)
- Dependency : Injection	Hilt
- Networking : Retrofit2, OkHttp3, Gson Converter
- Image : Loading	Coil
- Navigation : Navigation-Compose
- Testing : JUnit4, Mockito, Compose UI Test

  Strucure :-
  UserInformationApp/
  │
  ├── app/                         # Presentation Layer
  │   ├── di/                      # Hilt modules (AppModule, NetworkModule)
  │   ├── ui/
  │   │   ├── UserListScreen.kt    # List of users
  │   │   ├── UserDetailScreen.kt  # Detail screen for selected user
  │   │  
  │   ├── viewmodel/
  │   │   └── UserViewModel.kt     # Handles state using StateFlow
  │   ├── MainActivity.kt          # NavHost setup for navigation
  │   └── utils/                   # Common helper utilities
  │
  ├── network/                     # Data Layer
  │   ├── api/                     # Retrofit interfaces
  │   ├── client/                  # Retrofit client builder
  │   └── model/                   # Data model classes (User)
  │
  ├── build.gradle.kts             # Root gradle (with plugin aliases)
  └── settings.gradle.kts
