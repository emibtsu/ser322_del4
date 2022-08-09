# ser322_del4

## Application Description
This appication is based on a Closet database where there are pieces of clothing, items, type of clothes, brands, owners of clothes, location, and general information about the clost and its contents.
In this application you first launch with specified instructions below and then login with your DB info. 

You can create the Tables with the attached sql queries. From there you fill in the sql db login information 
(All preset with default values except password). Then you can either search, insert item, update item, or delete an item. Each allows the user to select which table they would like to access.
For example you can search for all owners, delete an existing owner, insert a new owner, or update an exsisting owner.

The search includes: selecting all owners with item/clothing information, select shirts by owner brand, select all shirts, select all pants, select all outerwear, select all clothing worn information,
select item by location, select all clothing owned, select all unowned clothing, select brand by clothing id, select clothing by clothing id

The update, insert, and delete can change owners, brands, clothing, items, pants, shirts, outerwear, owns relationship, has color relationship

## Steps to create DB
Must name schema closet (parts of the program that use meta data depend on this)
run closet_5_create.sql in workbench
run closet_5_insert.sql in workbench

## Requirements to run
Have java jdk on PC ( I am using jdk16)
The driver is included in the project (mysql-connector-java-8.0.29.jar) so no need to download seperately

## Build string (build from src)
MAC: javac -d ./bin -cp  ./libs/mysql-connector-java-8.0.29.jar  ./del4/Closet.java ./del4/JImagePanel.java ./del4/CustomSwing.java ./del4/Database.java ./del4/JListPanel.java ./del4/JActionFrame.java ./del4/JDataButton.java ./del4/JFormFrame.java ./action/IActionOption.java ./action/DDLOption.java ./action/SearchOption.java ./del4/QueryStatements.java ./del4/Table.java

WINDOWS: javac -d .\bin -cp .\libs\mysql-connector-java-8.0.29.jar .\del4\Closet.java .\del4\JImagePanel.java .\del4\CustomSwing.java .\del4\Database.java .\del4\JListPanel.java .\del4\JActionFrame.java .\del4\JDataButton.java .\del4\JFormFrame.java .\action\IActionOption.java .\action\DDLOption.java .\action\SearchOption.java .\del4\QueryStatements.java .\del4\Table.java

## Run String (run from src)
MAC: java -cp ./bin:./libs/mysql-connector-java-8.0.29.jar del4/Closet

WINDOWS: java -cp .\bin;.\libs\mysql-connector-java-8.0.29.jar del4/Closet

## Video URL

## Individual Contributions
### Deliverable 1
Emily: Create drive for team to work in, make initial draft with all the requirements/entities, communicate with team with deadlines and draft up ideas for potential domains, create initial document for team to collab in

Paul:

Paul: Participating in brainstorming for database ideas.

### Deliverable 2
Emily: Make doc for team to collab on diagram, draft initial ER diagram, draft inital entities and relationships, communicate with team, turn in final deliverable

Paul:

Paul: Contributed to initial ER diagram. 

### Deliverable 3
Emily: Make video and readme, help test create statements and add referential triggers, make insert data, draft inital select statements, commnunicate with team on deadlines and requirements, turn in final deliverable

Paul:

Paul: Created outline and made contributionis to create, insert, and select statements. Updated ER diagram per gradeer response. Constributed to schema relation daigram.

### Deliverable 4
Emily: Help with adding labels to GUI (Paul did most GUI btw), add all select statements from previous del and add corresponding methods, communicate with team, spearhead readme edits, create github repo to work in, fill out readme code/test application on windows, help create script for video, film video

Paul:

Paul: Contributed to program including GUi, basic, logic, and testing.

## Updates to ER diagram
- embedded location into item since entity attributes only consisted of item key
- Item no longer a weak entity because of first change
- completed specialization of Clothing into Shirt, Pants, and Outerwear (disjoint and total)
- fixed cardinality of Clothing has Brand relationship
- identified entity Color and its relationship with Item (M:N cardinality per initial requirements)
- eliminated year as being part of the primary key per the initial requirements (i.e., brand name is singular primary key)

## Structure of code

UI - GUI using Java Swing

JDBC Driver- runs with mysql-connector-java-8.0.29.jar, located in libs directory

Middlewear- SQL DB Server running with JDBC

DB - Run in mySQL workbench

## Description of Application Activities

For search:

For insert:

For delete:
