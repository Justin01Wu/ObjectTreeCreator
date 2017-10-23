# ObjectTreeCreator

## What is it?
This util automatically walk through the class tree on Java reflection and create an object tree with defaul values

## How does it do?
Because Java has so many types of class, this code only covers most common Java class, like 
1. all primitive types and its wrapper
2. List, Set and Map
3. Array
4. and so on

## How to use it

```
	@Test
	public void testIntegerCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();		
		Integer result = generator.generate(Integer.class);
		assertEquals(result, new Integer(12345));
	}
```

## Customize it

User can customized it by adding their own creator:
```
public class CustomizedBeanCreator implements BeanCreator<CustomizedBean>{
	
	@Override
	public CustomizedBean createBean() {
		CustomizedBean bean = new CustomizedBean();
		bean.setId(-2323);
		bean.setName("this bean is created by a customized creator");
		
		return bean;
	}
	
	@Override
	public Class<CustomizedBean> getClazz() {
		return CustomizedBean.class;
	}

}
```

You can also customized existing creator, like primitive type
```
public class CustomizedStringCreator implements BeanCreator <String>{
	
	@Override
	public String createBean() {
		String bean = new String("===this is customized String===");
		return bean;
	}

	@Override
	public Class<String> getClazz() {
		return String.class;
	}
}
```

## Error handling
If a class is not covered,  and it is not customized,  and it doesn't have default constructor, then the util will set it to null 

if any exception happen during those process, the value will be set to null

## Why do I create this util?
Why Do I create this util:

We had many RESTful Api On Jackson, I create a java doclet on it, But I had a difficulty to prepare sample for it
I did some research on the marketing, and didn't find a good solution for it.


Please see some discuss about it:  [RESTful sample](https://stackoverflow.com/questions/40985838/automatically-generate-restful-api-sample-json).

So I decided to create this tool to automatically create a java object tree based on its class.

## Author
Justin Wu 
