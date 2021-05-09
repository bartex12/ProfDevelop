import org.gradle.api.JavaVersion

object Config{
   const val application_id = "geekbrains.ru.translator"
   const val compile_sdk = 30
   const val min_sdk = 21
   const val target_sdk = 30
   val java_version = JavaVersion.VERSION_1_8

}

object Releases {
   const val version_code = 1
   const val version_name = "1.0"
}

object Modules {
   const val app = ":app"
   const val core = ":core"
   const val model = ":model"
   const val repository = ":repository"
   const val utils = ":utils"
 //Features
   const val historyScreen = ":historyScreen"
}

object Versions {
     //Kotlin
   const val core = "1.3.2"
   const val stdlib = "1.4.21"
   const val coroutinesCore = "1.4.1"
   const val coroutinesAndroid = "1.4.1"

//   //Tools
//   const val multidex = "1.0.3"

   //Design
   const val appcompat = "1.2.0"
   const val material = "1.3.0"

   //Retrofit
   const val retrofit = "2.9.0"
   const val converterGson = "2.7.1"
   const val interceptor = "3.12.1"
   const val adapterCoroutines = "0.9.2"
   //Recyclerview
   const val recyclerview = "1.2.0"
   //Swiperefreshlayout
   const val swiperefreshlayout = "1.1.0"

   //Koin
   const val koinAndroid = "2.0.1"
   const val koinViewModel = "2.0.1"

   //Picasso
   const val picasso = "2.71828"

   //Room
   const val roomKtx = "2.4.0-alpha01"
   const val runtime = "2.4.0-alpha01"
   const val roomCompiler = "2.4.0-alpha01"

   //Test
   const val jUnit = "4.13.1"
   const val runner = "1.3.0"
   const val espressoCore = "3.3.0"
}

object Kotlin {
   const val core = "androidx.core:core-ktx:${Versions.core}"
   const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
   const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
   const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

//object Tools {
//   const val multidex = "com.android.support:multidex:${Versions.multidex}"
//}

object Design {
   const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
   const val material = "com.google.android.material:material:${Versions.material}"
}

object Recyclerview{
   const val recycler_view = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
}

object Swiperefreshlayout{
   const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
}

object Retrofit {
   const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
   const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
   const val adapter_coroutines =
      "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
   const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
   const val koin_android = "org.koin:koin-android:${Versions.koinAndroid}"
   const val koin_view_model = "org.koin:koin-android-viewmodel:${Versions.koinViewModel}"
}

object Picasso {
   const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Room {
   const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
   const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
   const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
   const val junit = "junit:junit:${Versions.jUnit}"
   const val runner = "androidx.test:runner:${Versions.runner}"
   const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}