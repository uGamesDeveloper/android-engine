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
 implementation 'com.github.uGamesDeveloper:android-engine:1.1.5''
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

Создайте enum ваших хранилищ реализовывающий PrefsLibrary
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
 
 void toChange(Ref<Integer> integer) {
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

# Coroutine 

Coroutine - процедура, управляющая синхронными и асинхронными методами, которую можно остановить или прервать.

Корутина создается как объект 

```java
Coroutine coroutine = new Coroutine(c -> {

});
```

В корутине можно применять 2 метода - main и yiled.

* main метод выполняется в главном потоке. При завершении выполнения метода происходить автоматическое переключение на следу
* yiled метод может выполняться в любом потоке, на выбор пользователя. При завершении выполнения необходимо указать, что выполнение кода завершено c помощью метода complete();


Данная корутина подождет 2 секунды, после чего выведет в лог "testLog".
```java 
Coroutine coroutine = new Coroutine(c -> {
  c.yield(() -> {
    Runnable runnable = new Runnable() {
            @Override
            public void run() {
              c.complete();
            }
    };
    new Handler().postDelayed(runnable, 2000);
  });

  c.main(() -> Log.e("tag", "testLog"));
});
```

Данная корутина напечатает в лог "testLog" 20 раз с задержкой 1 секунду.

```java
Coroutine coroutine = new Coroutine(c -> {
  for (int i = 0; i < 20; i++) {
    c.yield(() -> new WaitForSeconds(1, c));
  
    c.main(() -> Log.e("tag", "testLog"));
  }
});
```

Возвращение значений из yield можно реализовать с помощью Ref или Out
```java
Coroutine coroutine = new Coroutine(c -> {
  Out<String> out = new Out<>();
  c.yield(() -> new WaitForSeconds(1, c));
  c.yield(() -> {
    out.value = "newString";
    c.complete();
  });
  c.main(() -> Log.e("tag", out.value));
});
```



* ```Coroutine.startCoroutine(coroutine, activity); ```   Запуск корутины 

* ```Coroutine.stopCoroutine(coroutine);```   Остановка корутины 

* ```Coroutine.resumeCoroutine(coroutine);```   Продолжить выполнение с остановленного места


* ```Coroutine.breakCoroutine(coroutine);```   Прервать выполнение без возможности продожить 














