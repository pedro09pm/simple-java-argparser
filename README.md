

<div style="text-align: justify">

<div style="text-align: center">

# SIMPLE JAVA ARGPARSER

</div>

Lisenced under **MIT**, created by **Pedro Marín Sanchis**.

**READ LICENSE BEFORE USE.**

---

### DOWNLOAD

Head to the [releases](https://github.com/pedro09pm/simple-java-argparser/releases) page of this repository, where you'll find all the different versions.

### HOW TO USE

First of all, before parsing anything, **you must create your Argument objects**. This can be done by using the **constructor**:

```java
Argument(String[] acceptedNames, ArgumentValue[] argumentValues)
```

When creating a new argument for future evaluation we have to specify it's available ways of being referred to, for example:
An argument for client could be written as "--client" or "-client" or even "--cli".

```java
Argument rectangleArgument = new Argument(
        new String[]{"--rectangle"},
        new ArgumentValue[]{
                new ArgumentValue(ArgumentValueType.INTEGER),
                new ArgumentValue(ArgumentValueType.INTEGER)
                }
        );
```

Secondly we have to specify what, if any, argument values will be passed to this argument.

#### Argument values/types
Argument values/types are the arguments we pass to an argument (Recursive, I know...). Here's an example:
```
java -jar myApplication.jar --user User01 --password secret1234
```
We are using arguments "--user" and "--password" and we have specified parameters for both of them. In this case they would
be of type **String**.

Here's a list of all argument value types:

```
ArgumentValueType.INTEGER
ArgumentValueType.FLOAT 
ArgumentValueType.DOUBLE
ArgumentValueType.CHARACTER
ArgumentValueType.STRING
ArgumentValueType.BOOLEAN
ArgumentValueType.DIRECTORY
ArgumentValueType.FILE
```

To specify an ArgumentValue we must use the **constructor**:

```java
ArgumentValue value = new ArgumentValue(ArgumentValueType.INTEGER);
```

Arguments are validated by the parser automatically.

#### · ARGUMENT RESTRICTIONS

##### MUTUAL EXCLUSIVITY

Let's imagine we have created an application that has two modes, a **client** mode and a **server** mode.
We would add the "--client" and "--server" parameters.

We, however, know that both are incompatible together.
If we want to run the application client we can't tell it to also do the server, the options are **mutually exclusive**.

To make our parser aware of this restriction we must simple use the **addMutuallyExclusiveArgument()** method from the Argument class.

```java
clientArgument.addMutuallyExclusiveArgument(serverArgument);
```

##### REQUIRED ARGUMENTS

In the same way that we have arguments that cannot be combined, we can also run into the opposite situation.
There can be arguments that have to appear together. Let's imagine the following:

We have created an application where we can change the window size via parameters. We would create a "--set-window-size" parameter.
Now we can either specify that this parameter requires two integers or we could specify that other parameters have to appear
together with this one, for example: "--width" and "--height".

We use the **addRequiredArgument()** method from the Argument class.

```java
setWindowSizeArgument.addRequiredArgument(widthArgument);
setWindowSizeArgument.addRequiredArgument(heightArgument);
```

#### · ARGUMENT ACTIONS

Argument actions follow the functional interface **ArgumentAction**. To make an argument execute some code when found
we have to implement the method ourselves. We use the **setAction()** method.

```java
clientArgument.setAction(() -> {
    System.out.println("Launching Client");
    ApplicationClient.run();
});
```

#### · RECOVERING ARGUMENT VALUES

Let's say we're interested in recovering an argument value for the argument "--name".

We use the **getArgumentValue()**, remember to **cast** the return.

```java
nameArgument.setAction(() -> {
    System.out.println((String) nameArgument.getArgumentValue(0));
});
```


#### · PARSING ARGUMENTS

Finally to parse the arguments you can use the **parseArgs()** method from the ArgumentParser class.

```java
public static void main(String[] args) {
    ArgumentParser.parseArgs(args)
}
```

</div>