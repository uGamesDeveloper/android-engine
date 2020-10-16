# android-engine

[![](https://jitpack.io/v/uGamesDeveloper/android-engine.svg)](https://jitpack.io/#uGamesDeveloper/android-engine)


## Gradle Зависимости


### Project
```gradle
allprojects {
 repositories {
 ...
 maven { url 'https://jitpack.io' }
 }
}
```
### App
```gradle
dependencies {
 implementation 'com.github.uGamesDeveloper:android-engine:1.1.4''
}
```

# Prefs
Singletone класс обертка для SharedPreferences и SharedPreferences.Editor

#### Стандартное использование

При стандартном использовании все значения хранятся в private SharedPreferences "UGamesSharedPreferences".
```java
 SharedPreferences sharedPreferences = getContext().getSharedPreferences("UGamesSharedPreferences", Context.MODE_PRIVATE);
```

```java
Prefs.init(getContext())
Prefs.setInt("key", 10);
Prefs.setString("keyString", "qwerty");
Prefs.save();

Prefs.getString("keyString", "defaultValue");

if(Prefs.hasKey("key")) {
 Prefs.deleteKey("key");
}

Prefs.deleteAll();

Prefs.save();
```

#### Использование с собственными хранилищами

При использовании с собвсенными хранилищами значения хранятся в HasMap<PrefsLibrary, SharedPreferences>.

Создайте enum ваших хранилищ 
```java
enum Lib implements PrefsLibrary {
  Settings,
  Other
}
```

```java
Prefs.initWithOtherLibrary(getContext(), Lib.values());
Prefs.setInt("int1", 20, Lib.Settings);
Prefs.save(Lib.Settings);

if(Prefs.hasKey("int1", Lib.Settings) {
  int int1 = Prefs.getInt("int1, Lib.Settings);
  Prefs.deleteKey("int1", Lib.Settings);
}
```

