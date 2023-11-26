![Maven Central](https://img.shields.io/maven-central/v/com.devbrackets.android/annores)

## Annores
Annores, from **Anno**tated **Res**ource, provides the ability to retrieve `AnnotatedString`s from
resources through simple Composable functions.


# Why
Android `Resources` provides the ability to get a string that is formatted or a string that
is styled, however there are 2 issues with this:
1. When you get a styled string, it's a `Spanned` which isn't usable by Jetpack Compose
2. You can't get a string that is both formatted & styled

Annores addresses both of these issues by first converting the styled `Spanned` to an `AnnotatedString`
which is usable by Composables then extending this conversion to handle formatting


# Use
The latest version can be included from [Maven Central][Maven Central].

```gradle
repositories {
    mavenCentral()
}

dependencies {
    //...
    implementation 'com.devbrackets.android:annores:1.0.0'
}
```


# Quick Start
Annotated strings should be added to your `strings.xml` files the same way you add any other string
or plural resource, e.g.

```xml
<string name="an_annotated_string">Annotated strings can be <b>Bold</b>, <i>Italic</i>, or any custom annotation</string>
```

Then you can reference this in a Composable function via `annotatedStringResource` or 
`annotatedPluralStringResource`, e.g.
```kotlin
@Composable
fun AnAnnotatedComposable() {
  Text(
    text = annotatedStringResource(R.string.an_annotated_string)
  )
}
```

# License

    Copyright 2023 Brian Wernick

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


# Attribution
* Uses [Kotlin][Kotlin] licensed under [Apache 2.0][Apache 2.0]
* Uses [Compose][Compose] licensed under [Apache 2.0][Apache 2.0]

 [Kotlin]: https://kotlinlang.org/
 [Compose]: https://developer.android.com/jetpack/compose
 [Maven Central]: https://search.maven.org/artifact/com.devbrackets.android/annores
 [Apache 2.0]: https://opensource.org/licenses/Apache-2.0
