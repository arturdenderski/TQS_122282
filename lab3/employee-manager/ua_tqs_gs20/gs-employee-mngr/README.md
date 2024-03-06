## Review Questions

a) Identify a couple of examples that use AssertJ expressive methods chaining:
- In method `givenEmployees_whenGetEmployees_thenStatus200()` in class `E_EmployeeRestControllerTemplateIT`:
  - ```java 
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
    ```
- In method `givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()` in class `A_EmployeeRepositoryTest`:
  - ```java 
     assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
     ```

b) Identify an example in which you mock the behavior of the repository (and avoid involving a
database):

- In the `setUp()` method of class `B_EmployeeService_UnitTest`. 
  ```java
  Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
  Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
  Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
  Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
  Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
  Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
   ```
mocks the couple of methods of the repository.

c) What is the difference between standard @Mock and @MockBean:
- `@Mock`: It creates a mock instance of the specified class/interface.
- `@MockBean`: Used to mock beans (Spring components) in integration tests. It creates a mock instance of the specified bean type.

d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be
used: 
- The file `application-integrationtest.properties`is used to configure properties specific to integration tests. It specifies different database configurations, such as using a separate test database instead of the main production database.

e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with
SpringBoot. Which are the main/key differences:
- C: Tests only the controller layer, mocking the service layer and not involving the database.
- D: Loads the full Spring Boot application context, testing all layers (controller, service, repository, and database) using MockMvc.
- E: Similar to D, but tests the REST API a real HTTP client (TestRestTemplate), making actual HTTP requests and verifying the responses.
