# ObjectTreeCreator

## What is it?
This util automatically create an object tree with defaul values if they are Java Bean

## How does it do?
Because Java has so many types of class, this code only covers most common Java class, like 
    all primitive types and its wrapper
    List, Set and Map
    Array
    and so on
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
