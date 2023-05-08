# ADDRESSBOOK APPLICATION

This spring boot app was made with Jdk 17. It basically loads a text file (in CSV format) from 
`/src/main/resources/addressbook/AddressBook.txt` and answer these 3 questions:

1. How many males are in the address book?
2. Who is the oldest person in the address book?
3. How many days older is Bill than Paul?

To run the application um can run as any other spring boot with the following commands:
`mvn clean install`
and then:
`mvn spring-boot:run`

When the app finishes it's starting you'll see the following answers:

`1- THERE ARE 3 MALE PEOPLE`

`2- THE OLDEST PERSON IS Wes Jackson`

`3- Bill McKnight IS 2862 DAYS OLDER THAN Paul Robinson`

![alt text](https://github.com/czbadaro/addressbook/blob/main/result.png?raw=true)