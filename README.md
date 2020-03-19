# MaterialDesign의 활용


- 완성된 화면

<center>
<img src="https://user-images.githubusercontent.com/54485132/77032770-b8ae9600-69e8-11ea-8d6b-1867af3525c6.gif" width="40%">
</center>


# 0. gradle 설정
**build.gradle 상단에 아래 코드 작성**

```kotlin
apply plugin: 'kotlin-kapt'
```

**이후 dependency에 아래 내용을 추가한다**

```kotlin
    implementation "com.google.android.material:material:1.2.0-alpha05"
    implementation "com.github.skydoves:transformationlayout:1.0.2"
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation "com.github.bumptech.glide:glide:4.9.0"
    kapt "com.github.bumptech.glide:compiler:4.9.0"
```


# 1. BottomNavigation & ViewPager 연동하기
## [1]. 뷰 짜기
### {1} activity_main

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appBarLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <include
            android:id="@+id/main_toolbar"
            layout="@layout/toolbar_home" />
    </com.google.android.material.appbar.AppBarLayout>

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/main_viewpager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/main_bottom_navigation"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        app:layout_constraintTop_toBottomOf="@+id/appBarLayout" />
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/main_bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:background="@color/colorPrimary"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:itemIconTint="@color/white"
        app:itemTextColor="@color/white"
        app:menu="@menu/menus"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### {2} toolbar_home.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/colorPrimary">
    <TextView
        style="@style/TextStyle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_name"
        android:maxEms="15"/>

</androidx.appcompat.widget.Toolbar>
```

### {3} menus.xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/action_one"
        android:enabled="true"
        android:icon="@drawable/ic_home"
        android:title="@string/menu_home"
        app:showAsAction="ifRoom"/>
    <item
        android:id="@+id/action_two"
        android:enabled="true"
        android:icon="@drawable/ic_library"
        android:title="@string/menu_library"
        app:showAsAction="ifRoom"/>
    <item
        android:id="@+id/action_three"
        android:enabled="true"
        android:icon="@drawable/ic_radio"
        android:title="@string/menu_radio"
        app:showAsAction="ifRoom"/>
</menu>

## [2] 프래그먼트 생성하기
- HomeFragment
- LibraryFragment
- RadioFragment


**위 세 개의 프래그먼트를 생성해준다**

## [3] MainPagerAdapter 생성하기
```kotlin
class MainPagerAdapter(fm:FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> LibraryFragment()
            else -> RadioFragment()
        }
    }
    override fun getCount() = 3
}
```

## [4] MainActivity 작성하기
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        with(main_viewpager){
            adapter = MainPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 3
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) = Unit

                override fun onPageSelected(position: Int) {
                    main_bottom_navigation.menu.getItem(position).isChecked = true
                }

            })
        }
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_one -> main_viewpager.currentItem = 0
                R.id.action_two -> main_viewpager.currentItem = 1
                R.id.action_three -> main_viewpager.currentItem = 2
            }
            true
        }
    }
}
```
<div>
    <center>
    <img src="https://user-images.githubusercontent.com/54485132/77032773-b9472c80-69e8-11ea-8e91-0f39bb201d09.png" width="40%">
    <img src="https://user-images.githubusercontent.com/54485132/77032850-f6132380-69e8-11ea-961a-779ce2ebc33f.gif" width="40%">
    </center>
</div>
