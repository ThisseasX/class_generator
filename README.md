# class_generator
A tool that generates files from predefined templates, based on given names and file extension.

# Usage:

1) Download the `class_generator.jar` file from this page.
2) Open the command line.
3) Execute the jar with `java -jar class_generator.jar [parameters]` (see below).

# Parameters

First parameter: File extension - e.g. .java, .cs.  
Rest parameters: File names to be generated - e.g. Customer, Sale.

You can also execute with parameter `help` to view basic instructions.

e.g. `java -jar class_generator.jar help`

# Sample

Sample input: `java -jar class_generator.jar .cs Customer`  
Sample output: 
```
C:\Users\User\Desktop\ClassGeneratorFiles
└───Timestamp
    ├───Impl
    │       CustomerRepositoryNHibernate.cs
    │
    ├───Interface
    │       ICustomerRepository.cs
    │
    └───Test
            CustomerRepositoryTest.cs
```

```
public class CustomerRepositoryNHibernate : GenericRepository<int, Customer> : IRepository<Customer>
{
	public CustomerRepositoryNHibernate()
	{
		base();
	}
}
```

Thank you for using Class Generator.
