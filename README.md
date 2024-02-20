# Cryptocurrency Info App

This project is a cryptocurrency information application showcasing the use of modern Android development tools and practices. It follows Clean Architecture principles and utilizes popular libraries and frameworks.

## Key Features

- **Clean Architecture:** Organized into distinct layers for better separation of concerns and easier testing.
- **MVVM Pattern:** Implemented using ViewModel to keep UI code simple and free of business logic.
- **Jetpack Compose:** Modern declarative UI toolkit used to build the user interface.
- **Dagger Hilt:** Dependency injection to provide a scalable way to manage object creation.
- **Retrofit & Ktor:** Networking libraries used for making API calls.
- **Realm Database:** Local persistence database for storing and managing offline data.
- **Kotlin Coroutines & Flows:** For handling asynchronous tasks and real-time data streaming.
- **Unit Testing:** Robust unit tests with JUnit5, MockWebServer, fakes, and data generators.

## Architecture

This app is based on Clean Architecture guidelines, which allows for a scalable, testable, and maintainable codebase. The architecture is divided into the following layers:

- **Data Layer:** Manages application data and handles network/api calls (using Retrofit and Ktor) and database operations (using Realm).
- **Domain Layer:** Contains business logic and use cases of the application.
- **Presentation Layer:** UI implementation using Jetpack Compose and ViewModel to manage the UI-related data.

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Stan-Balabushevich/Crypto_Info_App.git
       Open the project in Android Studio or your preferred IDE.

2. Build the project. Make sure to sync all Gradle files and download all the required dependencies.

3. Run the application on an emulator or physical device.

## Testing

The application includes comprehensive unit tests to ensure the reliability and correctness of the features. These tests include:

- **Unit Tests:** Written with JUnit5 to cover critical components of the application, ensuring they function as expected under various conditions.
- **MockWebServer:** Used to mock API responses for testing network requests, allowing for reliable and consistent testing of Retrofit and Ktor integrations.
- **Fakes and Data Generators:** Utilized in place of real implementations for a more controlled and isolated testing environment, especially for testing the interactions with the Realm database.

To run the tests, navigate to the test source set and execute the tests via Android Studio or using the Gradle command line.
Dependencies

This project uses the following major dependencies:

- **Jetpack Compose:** androidx.compose.*
- **Dagger Hilt:** com.google.dagger:hilt-android
- **Retrofit:** com.squareup.retrofit2:retrofit
- **Ktor:** io.ktor:ktor-client-*
- **Realm Database:** io.realm.kotlin:library-base
- **Kotlin Coroutines:** org.jetbrains.kotlinx:kotlinx-coroutines-core
- **Kotlin Flows:** org.jetbrains.kotlinx:kotlinx-coroutines-flow
