# orika-spring-boot-starter
Spring Boot Starter for Orika Mapper

# Dependencies
- Orika 1.5.4

# Usage
## Add dependency

```xml
<dependency>
    <groupId>com.github.mengyongfeng</groupId>
    <artifactId>orika-spring-boot-starter</artifactId>
    <version>1.5.4</version>
</dependency>
```

## Basic Usage
```java
@RestController
public class Demo {
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("test")
	  public DtoB test1() {
		    DtoA dtoA = new DtoA();
		    dtoA.setName = "name123";
		    return mapperFacade.map(dtoA, DtoB.class);
	  }
}
```
## Custom Mapper
```java
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class ABCusomMapper extends CustomMapper<DtoA, DtoB> {
    @Override
    public void mapAtoB(DtoA dtoA, DtoB dtoB, MappingContext context) {
        super.mapAtoB(dtoA, dtoB, context);
        dtoA.setSex(dtoB.getGender()));
    }
    @Override
    public void mapBtoA(DtoA dtoA, DtoB dtoB, MappingContext context) {
        super.mapBtoA(dtoA, dtoB, context);
        dtoA.setGender(dtoB.getSex()));
    }
}
```
## Custom Converter
```java
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class ObjectToObjectConverter extends CustomConverter<Object, Object> {
    @Override
    public Object convert(Object object, Type<?> type, MappingContext mappingContext) {
        return object;
    }
}
```
## Using MappingContext 
```java
@RestController
public class Demo

    @Autowired
    MappingContextFactory mappingContextFactory;
    
    @Autowired
    private MapperFacade mapperFacade;

	@GetMapping("test")
	public DtoB test1() {
		DtoA dtoA = new DtoA();
		dtoA.setName = "name123";

        MappingContext mpc = mappingContextFactory.getContext();
        mpc.setProperty("key1", "value1");
		return mapperFacade.map(dtoA, DtoB.class, mpc);
	}

```
