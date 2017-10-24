# ObjectTreeCreator

## What is it?
This util automatically walk through the class tree on Java reflection and create an object tree with defaul values

## What does it do?
Because Java has so many types of class, this code only covers most common Java class, like 
1. all primitive types and its wrapper (Integer default value will be 12345, and so on)
2. List, Set and Map (will create one element)
3. Array (will create one element)
4. String (default value is "a string")
5. enum (will use first item)
6. and so on

The process will walk throught the class tree: call default constructor and run all setter on it.

If an exception happen, the related properties will be set to null, you can customize it to avoid those exceptions.

You can see those sample below.

## How to use it

```
	@Test
	public void testIntegerCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();		
		Integer result = generator.generate(Integer.class);
		assertEquals(result, new Integer(12345));
	}
```

## Customizing it

User can customize it by adding their own creator:
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
Now you can register it :

```
	@Test
	public void testCustomizedCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExternalCreator(new CustomizedBeanCreator());
		CustomizedBean result = generator.generate(CustomizedBean.class);
		System.out.println(result);
		assertEquals(result.getId(), new Integer(-2323));
		assertEquals(result.getName(), "this bean is created by a customized creator");

	}
```


You can also customize an existing creator, like primitive type
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
If a class is not covered, it is not customized, and it doesn't have default constructor, then the util will set it to null 


If any exceptions occur during those process, the value will be set to null


It can detect nested class:
```
public class NestedBean {
	private NestedBean parent;
	public NestedBean getParent() {
		return parent;
	}
	public void setParent(NestedBean parent) {
		this.parent = parent;
	}
}
```
Here **parent** will be set to null to avoid infinite loop.

## Why do I create this util?
Why Do I create this util:

We had many RESTful API On Jackson, I created a java doclet on it to get RESTful API docs.
But I had a difficulty to prepare sample for it.

I did some research on the marketing, and didn't find a good solution for it.


Please see some discussions about it:  [RESTful sample](https://stackoverflow.com/questions/40985838/automatically-generate-restful-api-sample-json).

So I decided to create this tool to automatically create a java object tree based on its class.

## Dependency

This Jar won't depend on any Third party library except JDK 7 and above.

## Author
Justin Wu 
