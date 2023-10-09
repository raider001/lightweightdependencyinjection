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

## Interface
```
public interface SimpleInterface {

}
```

## SimpleClass
```
public class SimpleClass implements SimpleInterface {

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

    // When multiple constructors are defined, the DI constructor needs to be annotated.
    @DI
    public ComplexClass(SimpleClass simpleClass, DiClass diClass) {
        
    }

    public ComplexClass(SimpleClass simpleClass) {
        
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
      // Adding a class to the DI library.
      di.add(new SimpleClass());

      // Using an interface as a reference
      di.add(SimpleInterface.class, new SimpleClass());

      // Injecting a simple class with no dependencies.
      di.inject(DiClass.class);

      // Injecting a complex class with dependencies previously added.
      di.inject(ComplexClass.class);
    }
 }
 ```
