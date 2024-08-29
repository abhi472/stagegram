<!-- ABOUT THE PROJECT -->
# About The Project
<b>A Kotlin-based Android App with MVVM Architecture and Offline-First Approach</b>

This application prioritizes a seamless user experience by leveraging a combination of modern development techniques:

<b>MVVM Architecture:</b>

* Clean separation of concerns for maintainability and testing.

<b>Offline-First Approach:</b>

* Prioritizes local data stored in a Room database, ensuring functionality even when offline.
* Network calls are made only when necessary, optimizing resource usage.

<b>Data Management:</b>

* MainUseCase handles domain logic.
* MainRepository abstracts data access, promoting code reusability.

<b>Image Handling:</b>

* Efficiently stores profile pictures using standard image formats due to their smaller size.
* Employs Coil for effective caching of story images, improving performance and user experience.

<b>Current Scope:</b>

* Supports image formats exclusively for story content.

<b>Future Considerations:</b>

While the current implementation provides a strong foundation, there's room for further enhancements:

* Real-time Data Sync: Implementing an update service to subscribe to Firestore changes would keep the local database synchronized, offering a more up-to-date experience.
* Video Support: Expanding story content to include videos would diversify user expression and engagement.
* Accessibility: Ensuring the app caters to users with disabilities by adhering to accessibility best practices.

<b>Test Cases:</b>

* DatabaseTest.kt to test DB read and write.
* MainViewModelTest.kt tests viewmodel and usecase





