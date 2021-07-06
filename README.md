# Boots Bootique

## Description
A dazzling new shop, the _Boots Bootique_, is set to open their flagship store on Spring Street this spring. However, they've got no way to manage all their inventory just yet! The store owns a vast collection of boots of all different sizes, types, materials, and quantities. Help the shop create their inventorying system in time for their shop's opening.

## Objective
For this project, we won't have to touch the code in the frontend. All that we'll do is use Spring Data JPA to complete the functionality for the Boots API so that the frontend can:

- **C**reate new boots
- **R**ead and search the boots in the inventory
- **U**pdate the number of any given boot in the inventory
- **D**elete boots from the inventory

The controller has already been implemented for all the endpoints that the frontend depends on. All you'll need to do is add in the correct calls to your repository interface methods.


## Thumbnail

![Spring Boots Thumbnail](./thumbnail.jpg)

## Tasks

### TG1: Boot Up!

#### 1 (explore workspace, connect to database)
In the browser, you'll see that we have a frontend prepared already. We'll be using this frontend to interact with our Spring Boot Boots API. If you try to use any of the buttons on the screen right now, you'll get an error, as the API does not have that functionality implemented just yet.

To get started, add a property to the **application.properties** file so that we can connect to a file-based H2 database located at `~/boots.db`.

**Hint**:
The property you add should look like:

```
spring.datasource.url=jdbc:h2:file:~/boots.db
```

#### 2 (create pojo for boot entity)
Take a look at the `Boot` class. As of now, it has no fields or Spring Data annotations. This class is used by the `BootRepository`.

The fields that our `Boot` class requires are:

- an `Integer` `id`
- a `BootType` `type`
- a `Float` `size`
- an `Integer` `quantity`
- a `String` `material`

Add each of these fields to the `Boot` class, and make them `private`.

**Hint**:
You'll notice that one of these fields has a type that is not built into Java! The `BootType` is an **enum**, which is a special kind of class used for when there are only a few different kinds of something and we want the set of values to be constrained. Take a look at **src/main/java/com/codecademy/boots/enums/BootType.java** to see what the `BootType` enum looks like and what the possible values are.

#### 3 (add spring data jpa annotations to entity)
To ensure that Spring Data JPA can associate our `Boot` class with the underlying `"BOOTS"` table, we'll need to add all the required annotations.

Add the annotations to each of the fields from the previous step so that:

- The class is marked as an `Entity`
- The class is associated with the underlying `"BOOTS"` table
- The `id` field is marked as an `Id` column
- The `id` field is a `GeneratedValue` with the `GenerationType.IDENTITY` strategy
- The `type` field is associated with a column named `"TYPE"`
- The `size` field is associated with a column named `"SIZE"`
- The `quantity` field is associated with a column named `"QUANTITY"`
- The `material` field is associated with a column named `"MATERIAL"`

Lastly, add one more annotation to the `type` field:

```java
@Enumerated(EnumType.STRING)
```

This is used to ensure that Spring Data JPA knows that a `BootType` is just a `String`.

**Hint**:
The annotations that you should use are `@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`, and `@Column(name = "COLUMN_NAME")`

#### 4 (add getters and setters to entity)

Remember, in order for Spring Data JPA to be able to convert between entries in the `"BOOTS"` table and instances of the `Boot` class, you'll have to add getters and setters for each field.

Add a getter and setter for each of the five fields defined in the `Boot` class.

**Hint**:

The methods you have to define are:

- `public Integer getId()`
- `public void setId(Integer id)`
- `public BootType getType()`
- `public void setType(BootType type)`
- `public Float getSize()`
- `public void setSize(Float size)`
- `public Integer getQuantity()`
- `public void setQuantity(Integer quantity)`
- `public String getMaterial()`
- `public void setMaterial(String material)`

#### 5 (create repository interface and link to controller)
Now that the `Boot` entity is ready to use, it's time to update the `BootRepository` interface to turn it into a `CrudRepository` over the `Boot` class.

Update `BootRepository.java` so that `BootRepository` is a `CrudRepository` for the `Boot` class.

Once this is done, you can link it to the controller by adding a field `private BootRepository bootRepository` to the `BootController`, and initializing it in the constructor.

**Hint**:
For a class `Person` with an `Integer id`, a `PersonRepository` would look like:

```java
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
```

To link it to a `PersonController`, edit the `PersonController` like:

```java
public class PersonController {
  private PersonRepository personRepository;

  public PersonController(PersonRepository personRepository){
    this.personRepository = personRepository;
  }
}
```

### TG2: List All the Boots

#### 6 (add findAll call to required handler)
Now that the `BootRepository` is ready and can be used in the `BootController`, it's time to update the endpoints in the controller to use the `bootRepository` and complete the required functionality.

Start with the `getAllBoots` endpoint. Remove the code that throws the `NotImplementedException` in that method.

Then, add in the required `return` statement so that this method will yield an `Iterable` of all the boots in the database.

We've added some sample data to initialize the database with some boots. To ensure that this sample data is loaded, find the **application.properties** file and change `spring.datasource.initialization-mode=never` to `spring.datasource.initialization-mode=always`.

Validate that this worked by refreshing the UI for the Boots Bootique. You should now see a table with all the boots currently in the database!

**Hint**:
The method you'll use from the `bootRepository` here is the `findAll()` method.

### TG3: Add New Boots

#### 7 (add create functionality into create endpoint in BootController, test with form)

Now that the frontend has a table of all boots in the shop's inventory, they should be able to use the application to add a new boot. The frontend already has a form that can be used to add a new boot, but it won't work until the `addBoot` method in the controller is updated.

Remove the code that throws the `NotImplementedException` in the `addBoot` method. Replace it with a call to a method in the `bootRepository` that can save the boot sent in the request body to the database. Lastly, `return` the newly added boot.

**Hint**:
The method you'll need from the `bootRepository` is the `.save()` method, which accepts a `Boot` as an argument. This method returns the saved boot.

### TG4: Update the Inventory

#### 8 (add delete functionality into BootController, test with delete button)
The frontend additionally supports some "actions" for every row in the boots table. These actions are:
- ❌ (deleting a boot)
- ⬆️ (incrementing the quantity of a boot in the inventory)
- ⬇️ (decrementing the quantity of a boot in the inventory)

Find the `deleteBoot` method in the controller. Notice that this method accepts an `id` as a parameter. Remove the line that throws the `NotImplementedException`.

Use this `id` to find the boot we wish to delete. Remember, the method from `bootRepository` that finds an entry using its `id` will return an `Optional<Boot>`, so be sure to account for that in your code. If the boot with the supplied `id` cannot be found, simply return `null`.

Once you've found the boot to delete, call the correct repository method to delete it, and return the boot that was removed.

You can test the functionality once you are done using the ❌ action on any entry in the boot table.

**Hint**:
For a `PersonController`, the logic for a delete endpoint might look like:

```java
@DeleteMapping("/{id}")
public Person deletePerson(@PathVariable("id") Integer id) {
  Optional<Person> personToDeleteOptional = this.personRepository.findById(id);
  if (!personToDeleteOptional.isPresent()) {
    return null;
  }
  Person personToDelete = personToDeleteOptional.get();
  this.personRepository.delete(personToDelete);
  return personToDelete;
}
```

Working with `Optional`'s can be tricky, but they make error handling a lot easier.

#### 9 (add increment functionality into BootController, test with increment button)
The next action to implement for an entry in the boot inventory is the ability to increment the quantity of the boot.

Find the `incrementQuantity` method in the controller. Delete the line that throws an exception.

Next, similar to how the `deleteBoot` method worked, find the boot with the supplied `id`, and do the required checks on the `Optional<Boot>` before getting the actual `Boot` inside and storing it in a variable.

Use one of the setter methods on the boot object to increment its quantity by one. This can be done by using the `getQuantity()` method on the instance of the boot, adding one, and using that sum in the `setQuantity` method.

Remember, changes to an object won't be persisted until the `.save()` method is called.

Lastly, return the newly updated boot. You can test out the increment functionality by clicking the ⬆️ action on any boot in the table. You should see the quantity for that entry increase by one in the boot table if it works.

**Hint**:
For a `PersonController`, a method that increments a person's age would look like:

```java
@PutMapping("/{id}/age/increment")
public Person incrementAge(@PathVariable("id") Integer id) {
  // find the person
  Optional<Person> personOptional = this.personRepository.findById(id);
  if (!personOptional.isPresent()) {
    return null;
  }
  Person person = personOptional.get();

  // this next two lines perform the update
  person.setAge(person.getAge() + 1); 
  this.personRepository.save(person);
  return person;
}
```

#### 10 (add decrement functionality into BootController, test with decrement button)
Lastly, implement functionality to decrement the quantity of a boot in the `decrementQuantity` method in the `BootController`.

This will look very similar to the code for incrementing, but this time, instead of adding one to the quantity, subtract one instead.

Test out the functionality when you are done using the ⬇️ action on any entry in the boot table.

**Hint**:
The code for this step will look very similar to the code used to increment a boot's quantity. In fact, it should only differ by a single character!

### TG5: Search for boots

#### 11 (add methods for basic search (find by single property))
To make it easier for the shop owner to check their inventory, the application should support the ability to search the boot inventory by different parameters. The frontend has a form to use for the search, but you'll have to update the `searchBoots` method as well as add more custom query methods to the `BootRepository` for it to work properly.

To start, add four method declarations to the `BootRepository` interface:
- a method to find boots by size
- a method to find boots by their material
- a method to find boots by their type
- a method that can find all boots that have more than a provided quantity

When you've added the declarations, go back to the `BootController` and add the correct calls to the `searchBoots` method as indicated by the comments. Remove the lines that throw `QueryNotSupportedException`, but only for the lines where you're replacing them with calls to your repository.

When these calls have been added, you should be able to use the search form in the frontend to perform simple searches by one field at a time.

**Hint**:
Check out the [Spring Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation) for how you can create custom queries.

#### 12 (add methods for complex search (multiple properties))
For the shop owner to be able to perform more complex searches, using multiple fields at the same time, the repository will have to include more complex custom queries.

Add as many queries as you can to the `BootRepository` to support the different search combinations described in the comments in the `searchBoots` controller method. As you add the declarations in the `BootRepository`, replace the corresponding `QueryNotSupportedException` line in the `searchBoots` method. If you get them all, there should only be a single line that throws `QueryNotSupportedException` left.

As you add the queries to the repository and use them in the controller, continue to use the search form in the frontend to test out your queries.

**Hint**:
Some examples of the more complex query methods are:

```java
public List<Boot> findByMaterialAndQuantityGreaterThan(String material, Integer minQuantity);

public List<Boot> findByMaterialAndSizeAndQuantityGreaterThan(String material, Float size, Integer minQuantity);

public List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size, Integer minQuantity);

public List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer minQuantity);
```

#### 13
Congratulations! The shop owner is all ready to handle the spring opening of the _Boot Bootique_, thanks to your hard work on their inventorying system.

Play around with the application to add more boots, search different queries, delete boots, and update their quantities. If you find any bugs, go back to the relevant instruction and check out the hint to make sure you haven't missed anything.

As a challenge, think of other enhancements you could make to the application. For example, you could add a new action that allows the shop owner to update the size of a boot in the inventory.

**Hint**:
Other enhancements could include:
- adding another action to change a boot's material
- allowing the shop owner to mark certain boots as "best sellers" and updating the search to allow that filter
- adding another entity and repository to record `Purchase`s of boots, and updating the frontend to show those also