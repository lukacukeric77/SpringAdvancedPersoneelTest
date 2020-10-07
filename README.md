Intention:
To show basic Spring Security and custom Validation Annotations

Description:
Simple personel user interface that is split in 2 parts:
-Detailed personel pages, available on login
-Job list page, freely available

Detailed personel pages: after login, user is presented with CEO's employee page. On that page, there are list of subordinates (hyperlinks) that links to their pages and so on.
There are 2 additional links:
- opslaag - which is used for increasing/decreasing salary
- rijksregisternummer - used for changing the person's employee number

Rijksregisternummer:
Protected by custom validation annotation, can be changed only if new number matches these conditions:
- must have 11 digits
- first 2 digits are last 2 digits of that employee's birth year
- 2nd 2 digits are 2 digits of that employee's birth month
- 3rd 2 digits are 2 digits of that employee's birth date
- 3 "random" digits
- last 2 digits are confirmation digits. They must equal to 97-(first 9 digits modulo 97). However, if birth year of the employee is minimum 2000, you must add 2 in front the first 9 digits BEFORE you do the modulo.   

Technical details:
- IDE with Java and Spring 
- MySQL database
- username is email of the employee, 
- password is always "zorro"
- Database is NOT available, because it is not mine. However, you can recreate it based on my classes. 
