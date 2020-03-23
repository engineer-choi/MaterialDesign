# MaterialDesign의 활용

> 출처 : https://android-arsenal.com/details/1/8049




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


# 2.RecyclerView 및 FloatingActionButton 적용하기  

<div>
    <center>
    <img src="https://user-images.githubusercontent.com/54485132/77143802-93448980-6ac7-11ea-9751-54129e1c0cb1.png" width="30%">
    <img src="https://user-images.githubusercontent.com/54485132/77143806-963f7a00-6ac7-11ea-81a8-23b027c5a9f8.png" width="30%">
    <img src="https://user-images.githubusercontent.com/54485132/77143807-96d81080-6ac7-11ea-9c62-efd6e3848e33.gif" width="30%">
    </center>
</div>  

## [1] FloatingActionButton 눌렀을때 보여질 리사이클러뷰 만들기  
### {1}. item_poster_menu.xml  
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.skydoves.transformationlayout.TransformationLayout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  xmlns:tools="http://schemas.android.com/tools"
  android:id="@+id/item_poster_menu_transformationLayout"
  android:layout_width="match_parent"
  android:layout_height="66dp"
  app:transformation_duration="500">

  <androidx.cardview.widget.CardView
      android:id="@+id/item_container"
      android:layout_width="match_parent"
      android:layout_height="66dp"
    android:background="@color/background800"
    android:foreground="?attr/selectableItemBackground"
    app:cardBackgroundColor="@color/background800"
      app:cardCornerRadius="0dp">


    <androidx.constraintlayout.widget.ConstraintLayout
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:foreground="?attr/selectableItemBackground"
      android:orientation="vertical"
      android:paddingStart="16dp"
      android:paddingTop="8dp"
      android:paddingEnd="16dp"
      android:paddingBottom="8dp"
      tools:ignore="UnusedAttribute">

      <com.google.android.material.imageview.ShapeableImageView
        android:id="@+id/item_poster_post"
        android:layout_width="42dp"
        android:layout_height="42dp"
        android:scaleType="center"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:shapeAppearanceOverlay="@style/CircleImageStyle"/>

      <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/item_poster_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="12dp"
        android:maxLines="1"
        android:textAppearance="@style/TextAppearance.MaterialComponents.Headline6"
        android:textColor="@color/white"
        android:textSize="16sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toEndOf="@id/item_poster_post"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="Zootopia" />

    </androidx.constraintlayout.widget.ConstraintLayout>
  </androidx.cardview.widget.CardView>
</com.skydoves.transformationlayout.TransformationLayout>
```  

### {2} style -> CircleImageStyle  
```xml
    <style name="CircleImageStyle" parent="">
        <item name="cornerFamily">rounded</item>
        <item name="cornerSize">50%</item>
    </style>
```  
##### 참고  
**- cornerFamily는 rounded 또는 cut을 사용할 수 있다.**  
**- cornerSise를 %단위로 지정하여 사용**

### {3} fragment_home.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/background">
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:padding="6dp"
        app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
        app:spanCount="2"
        tools:listitem="@layout/item_poster" />
    <com.skydoves.transformationlayout.TransformationLayout
        android:id="@+id/transformationLayout"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="24dp"
        android:layout_marginBottom="24dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:transformation_duration="450"
        app:transformation_targetView="@+id/menu_home">
        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/fab"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:backgroundTint="@color/colorPrimary"
            android:src="@drawable/ic_write"
            app:borderWidth="0dp"
            app:tint="@color/white" />
    </com.skydoves.transformationlayout.TransformationLayout>
    <View
        android:id="@+id/backgroundView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:alpha="0.5"
        android:background="@color/black"
        android:visibility="gone" />
    <androidx.cardview.widget.CardView
        android:id="@+id/menu_home"
        android:layout_width="240dp"
        android:layout_height="312dp"
        android:layout_marginEnd="30dp"
        android:layout_marginBottom="30dp"
        app:cardBackgroundColor="@color/white"
        app:cardCornerRadius="4dp"
        app:cardElevation="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/background"
            android:orientation="vertical">
            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/recyclerView_menu"
                android:layout_width="match_parent"
                android:layout_height="264dp"
                android:orientation="vertical"
                app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                tools:listitem="@layout/item_poster_menu" />
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="48dp"
                android:background="@color/colorPrimary"
                android:gravity="center">
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:drawableStart="@drawable/ic_write"
                    android:gravity="center"
                    android:paddingStart="42dp"
                    android:paddingEnd="42dp"
                    android:text="compose new"
                    android:textAllCaps="true"
                    android:textColor="@color/white"
                    android:textSize="15sp" />
            </LinearLayout>
        </LinearLayout>
    </androidx.cardview.widget.CardView>
</androidx.constraintlayout.widget.ConstraintLayout>
```  

<div>
    <center>
    <img src="https://user-images.githubusercontent.com/54485132/77144531-790bab00-6ac9-11ea-9363-a40ffcc9cdbe.png" width="100%">
    </center>
</div>  

##### 참고  
**- RecyclerView에서 app:layoutManager 속성을 이용해 Kotlin/Java코드를 이용하지 않고 지정 가능**  
**- layoutManager가 LinearLayoutManager인 경우 orientation 지정**  
**- layoutManager가 GridLayoutManager인 경우 app:spanCount 속성으로 Grid 개수 설정 가능**  
**- tools:listItem = "" 을 이용하여 preview모드에서 리사이클러뷰 적용된 상태를 확인 가능**    

#### TransformationLayout에 대한 설명  

- layout 설명  
<div>
    <center>
        <img src="https://user-images.githubusercontent.com/54485132/77150272-df4afa80-6ad6-11ea-97e4-3b66cfca5297.png" width="45%">
        <img src="https://user-images.githubusercontent.com/54485132/77150276-e07c2780-6ad6-11ea-81ad-2858a3e5bd00.png" width="45%">     
    </center>
</div>     


> Transform을 시작하겠다 라는 신호를 줄 레이아웃을 감싸고, target으로 변하게 될 대상을 지정한다.  
여기서는 FloatingActionButton이 Transform을 시작하겠다! 라고 신호를 줄 레이아웃이다(TransformLayout으로 감싼다)
target은 CardView이며, transform 신호가 오기 전까지 뷰에서 보이지 않게 된다.  

- 기능 입히기  

<div>
    <center>
       <img src="https://user-images.githubusercontent.com/54485132/77150278-e114be00-6ad6-11ea-9eeb-d053dcc08aab.png" width="45%">
        <img src="https://user-images.githubusercontent.com/54485132/77150279-e1ad5480-6ad6-11ea-8814-2b6e7fa61aee.png" width="45%">
    </center>
</div>     


 > TransformationLayout이 선언되어있는 Activitiy의 onCreate에 super.onCreate()가 호출 되기 전에 onTransformationStartContainer()를 호출해 주어야 한다. Fragment의 경우 Fragment가 소속되어 있는 Activity의 onCreate()에 선언해 줘야 한다(onCreateView에 하면 안됨)  
 이후 FloatingActionButton 클릭시 이벤트를 추가한다.  
 
 
# 3. CoordinatorLayout을 이용한 AppBar와 NestedScrollView 연동하기  
<div>
    <center>
       <img src="https://user-images.githubusercontent.com/54485132/77223836-8006ec00-6ba3-11ea-9e35-b1423089e945.gif" width="45%">
        <img src="https://user-images.githubusercontent.com/54485132/77223838-81d0af80-6ba3-11ea-9f2e-2b8f3cb5f031.gif" width="45%">
    </center>
</div>     

## [1] 기본 지식  

![그림1](https://user-images.githubusercontent.com/54485132/77223840-8301dc80-6ba3-11ea-893f-0282208c30ed.png)  
  
  ![그림2](https://user-images.githubusercontent.com/54485132/77223839-82694600-6ba3-11ea-9acc-f71d8d5bd0db.png)  
  ![그림3](https://user-images.githubusercontent.com/54485132/77223842-8301dc80-6ba3-11ea-843b-f6fad74bec6b.png)  
  
## [2] 위 내용을 바탕으로 activity_detail.xml 작성  

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".DetailActivity">
    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <com.google.android.material.appbar.CollapsingToolbarLayout
            app:contentScrim="@color/colorPrimary"
            app:title="타이틀입니다"
            app:layout_scrollFlags="scroll|exitUntilCollapsed"
            app:expandedTitleTextAppearance="@style/TextAppearance.MaterialComponents.Headline5"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="match_parent"
                app:layout_collapseMode="parallax"
                android:layout_height="match_parent">
                <ImageView
                    android:id="@+id/img_detailView"
                    android:layout_width="0dp"
                    android:layout_height="0dp"
                    android:scaleType="centerCrop"
                    app:layout_constraintDimensionRatio="1:1.2"
                    app:layout_constraintLeft_toLeftOf="parent"
                    app:layout_constraintRight_toRightOf="parent"
                    app:layout_constraintTop_toTopOf="parent"/>
            </androidx.constraintlayout.widget.ConstraintLayout>
            <androidx.appcompat.widget.Toolbar
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"/>
        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:padding="21dp"
        android:layout_height="match_parent"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <TextView
                android:id="@+id/tv_detail_description"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="description"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tv_detail_title" />

            <TextView
                android:id="@+id/tv_detail_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="제목"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.core.widget.NestedScrollView>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```


# 4. TransformationLayout 이용하여 인텐트에 대한 애니매이션 적용하기  

<div>
    <center>
        <img src="https://user-images.githubusercontent.com/54485132/77274266-e18c9f00-6cf8-11ea-8edd-f7175bc2400e.gif" width=40%>
    </center>
</div>

  
### [1] DetailActivity.kt  
```kotlin
class DetailActivity : AppCompatActivity() {

    companion object{
        const val parmasExtraName = "parmasExtraName"
        const val posterExtraName = "posterExtraName"
        fun startActivity(
            context : Context,
            transformationLayout: TransformationLayout,
            poster : Poster
        ) {
            if(context is Activity){
                val bundle = transformationLayout.withView(transformationLayout, poster.name)
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(parmasExtraName, transformationLayout.getParcelableParams())
                intent.putExtra(posterExtraName, poster)
                context.startActivity(intent, bundle)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(parmasExtraName))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }
    private fun init(){
        intent.getParcelableExtra<Poster>(posterExtraName)?.let{
            Glide.with(this)
                .load(it.poster)
                .into(img_detailView)
            tv_detail_title.text = it.name
            tv_detail_description.text = it.description
        }
    }
}
```  

   
- TransformationLayout의 사용에 있어 transformationLayout.getParcelableParams()를 필수로 전달해 주어야 한다.  
- intent의 대상이 되는 activity의 onCreate에서 super.onCreate가 호출 되기 전에 onTransformationEndContainer가 호출 되어야 한다.

