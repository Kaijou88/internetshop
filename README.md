# Internet Shop

![Header Image](src/images/cat_logo_internetshop.jpg)

# Context
[Project's purpose](#purpose)

[Project's structure](#structure)

[For developer](#developer)

[Author](#author)

# <a name="purpose"></a>Project's purpose
The internet shop is a simple version of a real one. 
This internet shop has the following functionality:
- Registration of new user with USER role only, login and logout;
- User with ADMIN role can add new products and delete them;
- User with USER role can add products from list of products into the bucket and place an order;
- User can see own order history;
- User with ADMIN role can see list of all users and delete them;
- User with ADMIN and USER roles can see the order history of all users and delete them. 

# <a name="structure"></a>Project's structure
- Java 11
- Maven
- Travis
- javax.servlet 3.1.0
- javax.jstl 1.2
- mysql-connector-java 8.0.20
- log4j 1.2.17

# <a name="developer"></a>For developer
Open the project in your IDE as a maven project.

Add Java SDK 11 in project structure.

Configure Tomcat:
- Add artifact;
- Add Java SDK 11.

To run the project correctly first you need to do the following steps:
- change the path to the *.log file in src\main\resources\log4j.properties;
- configure src\main\java\com\project\internetshop\util\ConnectionUtil.java with the correct username and password;
- create a scheme and all tables in MySQL. Use src\main\resources\init_db.sql for this purpose.

Run the project.

The login page - http://localhost:8080/login is the first page that will be open.

Go to "Inject test data into DB" to inject into DB the following users:
- Admin with login - "admin" and password - "qwerty". Admin has just one role - ADMIN;
- Superadmin with login - "superadmin" and paasword "qwerty". Superadmin has USER and ADMIN roles;
- Customer with login "customer" and password - "1234". User has one role - USER;

After login go to "Inject test products into DB" to inject products into DB and to test the other functions.

# <a name="author"></a>Author
[Maryna Franchuk](https://github.com/Kaijou88)
