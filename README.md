# lightweightdependencyinjection

This project provides a no thrills, no surprises dependency injection framework.

It is intended to be light weight in the sense that it holds no additional dependencies on other projects.

It uses reflection to provide dependency injection capabilities in Java.

# Maven setup
```
<dependency>
  <groupId>com.kalynx</groupId>
  <artifactId>lwdi</artifactId>
  <version>1.0</version>
</dependency>
```

# Usage

## SimpleClass
```
public class SimpleClass {

}
```

## DiClass
```
public class DiClass {
  
  @DI
  public DiClass() {
  
  }
}
```

## ComplexClass
```
public class ComplexClass {

    @DI
    public ComplexClass(SimpleClass simpleClass, DiClass diClass) {
        
    }
}
```

## Main Application
```
 import DependencyInjector;
 import DependencyInjectionException;
 public class Main {
    public static void main(String[] args) {
      DependencyInjector di = new DependencyInjector();
      di.add(new SimpleClass());
      di.inject(DiClass.class);
      di.inject(ComplexClass.class);
    }
 }
 ```
