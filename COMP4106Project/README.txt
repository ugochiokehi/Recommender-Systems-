
Names: Vinisha and Ugochi Okehi
Date:2017-01-30


Note: This project uses the following tools
Tomcat server version 3.4.1
Jersey 1.17.1
Mongo Java Driver 2.11.3

Our Client is run on Postman or Google Chrome
Postman *** will be needed to check UPDATE and DELETE functionality

***To run the project in eclipse, right click on the project, Click on Run As -> Run On Server***

1.To run Application go to http://localhost:8080/COMP4601SDA/
2.To create a Document, go to http://localhost:8080/COMP4601SDA/create.html, enter in the following in this type of format
For example:

Document Id: 1 
Name: DocumentTest
Score: 100
Text: This is a test
Tags:cats:dogs:horses:hampsters:  //make sure to add colon in the end after every tag
Links:http://localhost:8080/COMP4601SDA/sda/1; //make sure to * in the end after every tag


Document Id: 2
Name: DocumentTest2
Score: 80
Text: This is a test2
Tags:cats:dogs:cats:hampsters:  //make sure to add colon in the end after every tag
Links:http://localhost:8080/COMP4601SDA/sda/1* //make sure to add * in the end after every tag


Document Id: 3
Name: DocumentTest3
Score: 60
Text: This is a test3
Tags:cats:dogs:horses:hampsters:  //make sure to add colon in the end after every tag
Links:http://localhost:8080/COMP4601SDA/sda/2*  //make sure to add semi-colon in the end after every tag


Document Id: 4
Name: DocumentTest4
Score: 100
Text: This is a test4
Tags:cats:dogs:horses:hampsters:  //make sure to add colon in the end after every tag
Links:1*2*3*  //make sure to add * in the end after every tag  

2. To search by id, ex: type -> http://localhost:8080/COMP4601SDA/sda/1

3. To see the update work, you can add create a Document with the same id with different information and submit it

4. To delete one document,Use DELETE in postman and type this url use for ex: http://localhost:8080/COMP4601SDA/sda/1

5. To delete set of documents with specific, for ex: type: http://localhost:8080/COMP4601SDA/sda/delete/cats:dogs: (the objects with these tags should be deleted)

6.  To search for documents with specific tags for ex: type http://localhost:8080/COMP4601SDA/sda/delete/cats:dogs:
    (it should return links mapping to the existing documents)

7. To view all the documents in the database, for ex: type http://localhost:8080/COMP4601SDA/sda/documents


