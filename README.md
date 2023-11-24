Annores
============
Annores, from Annotated Resource, provides the ability to retrieve AnnotatedStrings from
resources through simple Composable functions.


Use
-------
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


Quick Start
-------
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

License
-------

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


Attribution
-----------
* Uses [Kotlin][Kotlin] licensed under [Apache 2.0][Apache 2.0]
* Uses [Compose][Compose] licensed under [Apache 2.0][Apache 2.0]

 [Kotlin]: https://kotlinlang.org/
 [Compose]: https://developer.android.com/jetpack/compose
 [Maven Central]: https://s01.oss.sonatype.org/#nexus-search;quick~com.devbrackets.android.playlistcore
 [Apache 2.0]: https://opensource.org/licenses/Apache-2.0
