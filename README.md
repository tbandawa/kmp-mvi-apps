# <b>kmp-mvi-apps</b>
KMP Project showcasing the use of shared code with MVI architecture. Data is provided by [Art Institute of Chicago API](https://api.artic.edu/docs/#introduction)

<p align="center">
	<img src="/blob/list_view.png" width=100% height=40% alt="open Weather Screenshot">
</p>
<p align="center">
	<img src="/blob/detail_view.png" width=100% height=40% alt="open Weather Screenshot">
</p>

#### App Architecture

<p align="center">
	<img src="/blob/data_flow.png" width=100% height=40% alt="open Weather Screenshot">
</p>

This project focuses on MVI<b>(Model-View-Intent)</b> architecture with unidirectional and cylindrical flow of data.<p>
User performs an action which is an <b>Intent</b> -> the intent request for data change to <b>Model</b> -> model changes state and <b>View</b> updates.

#### Libraries and Tools
* [Kotlin](https://kotlinlang.org/)
* Android Architecture Components (Jetpack Compose, Navigation Component)
* SwiftUI
* KMP
* [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) preferences local storage
* [Coil](https://coil-kt.github.io/coil/) image loading library for Android Jetpack Compose support
* [Koin](https://insert-koin.io/) for dependency injection.
* [Jetpack Compose](https://developer.android.com/jetpack/compose) libraries.
* [Ktor](https://ktor.io/) for fetching data from the OpenWeatherMap API.
* [Timber](https://github.com/JakeWharton/timber) for logging events.
