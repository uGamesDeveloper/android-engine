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

При использовании с собственными хранилищами значения хранятся в HasMap<PrefsLibrary, SharedPreferences>.

Создайте enum ваших хранилищ реазивовывающий PrefsLibrary
```java
enum Lib implements PrefsLibrary {
  Settings,
  Other
}
```
Используйте все методы с указанием вашего хранилища. Если вы не укажите хранилище, то чтение и запись будут происходить в стандартное хранилище

```java
Prefs.initWithOtherLibrary(getContext(), Lib.values());
Prefs.setInt("int1", 20, Lib.Settings);
Prefs.save(Lib.Settings);

if(Prefs.hasKey("int1", Lib.Settings) {
  int int1 = Prefs.getInt("int1, Lib.Settings);
  Prefs.deleteKey("int1", Lib.Settings);
}
```

# Refs

#### Ref<> - обёртка для передачи и возвращения инициализированного значения через параметры методов.

Инициализация происходит до передачи в функцию. Все изменения, произошедшие в функции, будут отражены вне функции.

```java
 void mainFunction() {
   Integer value = 1;
   Ref<Integer> integer = new Ref<>(value);
   
   Log.e("tag", "Log: " + integer.value.toString());    //Log: 1
   
   toChange(integer);
   Log.e("tag", "Log: " + integer.value.toString());    //Log: 100
 }
 
 void toChange(Ref integer) {
   integer.value = 100;
 }
```

#### Out<> -  обёртка для возвращения не инициализированного значения через параметры методов.

Установка значения может происходить после передачи ссылки в параметр метода. Все изменения будут отражены вне функции.

```java
void mainFunction() {
  Out<Integer> integer = new Out<>();
  toChange(integer);
  Log.e("tag", "Log: " + integer.value.toString());    //Log: 100
}

void toChange(Out<Integer> integer) {
  integer.value = 100;
}
```






