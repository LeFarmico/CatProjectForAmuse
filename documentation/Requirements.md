# Work assignment

## Description

Build a simple application that fetches cats from the fantastic cats as a service, ehm service.
https://cataas.com/#/
that is able to do some data filtering, some image manipulation and make use of some jetpack compose
ui features. We have setup the base boilerplate for the application. What is needed is to fill in
the missing pieces, described below.

**WARNING** The current example takes some time before anything shows up on screen

## Dependencies

Ktor - https://ktor.io/docs/getting-started-ktor-client.html
Jetpack Compose - https://developer.android.com/jetpack/compose
kotlinx.serialization - https://github.com/Kotlin/kotlinx.serialization
Coroutines - https://kotlinlang.org/docs/coroutines-overview.html
Coil-Kt - https://coil-kt.github.io/coil/compose/

These dependencies are already in the example project.

## Task

Currently the app is built with a very barebones MVVM/Coroutines Flow/Jetpack Compose setup. Keeping
the architecture like it is, is fine. If you have time/want to show off some extra skills, feel free
to add any frameworks/features you would like.

1. Instead of printing out the url to a cat as a text, we would rather much make use of coil-kt and
   instead draw a picture of a cat. The dimensions of the image can be set to 100x100 dp.
2. Great! We now draw cats, however, we would like every cat picture to have rounded corners, by 8dp
3. Great! We would however want to only draw cats that are of mimetype "image/jpeg" and also print a
   text to the right of the picture telling us when the cat was created!
4. We want to make it easier for the user of the application to differentiate rows, by making so
   that every cat item alternates between being colored with #000000 and #808080
5. Come up with a way to fetch new cats for the user of the application, in a way that is easy to
   use and easy to understand.
6. It currently takes some time before any results are shown in the application, make use of
   DataUIStateWrapper to display a loading spinner in CatsScreen.kt until the cats have been fetched
   from the cats endpoint. This data is available in catsState.
7. (Optional) Tell the user how many cats have currently been fetched out of the maximum amount of
   cats that should be fetched
