# ObjectTreeCreator
This util automatically create an object tree with defaul values if they are Java Bean

Because Java has so many types of class, this code only covers most common Java class, like 
    all primitive types and its wrapper
    List, Set and Map
    and so on
User can customized it by adding their own creator:



If a class is not covered,  and it is not customized,  and it doesn't have default constructor, then the util will set it to null 

Why Do I create this util:

We had many RESTful Api On Jackson, I create a java doclet on it, But I had a difficulty to prepare sample for it
I did some research on the marketing, and didn't find a good solution for it.

So I decided to create this tool to automatically create a java object tree based on its class.

Justin Wu

