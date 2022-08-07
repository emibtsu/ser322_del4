# ser322_del4

## Application Description

## Steps to create DB
run closet_5_create.sql in workbench
run closet_5_insert.sql in workbench

## Requirements to run
Have java jdk on PC ( I am using jdk16)
The driver is included in the project (mysql-connector-java-8.0.29.jar) so no need to download seperately

## Build string (build from src)
MAC: javac -d ./bin -cp  ./libs/mysql-connector-java-8.0.29.jar  ./del4/Closet.java ./del4/JImagePanel.java ./del4/CustomSwing.java ./del4/Database.java ./del4/JListPanel.java ./del4/JActionFrame.java ./del4/JDataButton.java ./del4/JFormFrame.java ./action/IActionOption.java ./action/DDLOption.java ./action/SearchOption.java


WINDOWS: javac -d .\bin -cp .\libs\mysql-connector-java-8.0.29.jar Closet.java JImagePanel.java CustomSwing.java Database.java JListPanel.java Database.java JListPanel.java JActionFrame.java JDataButton.java JFormFrame.javaI ActionOption.java DDLOption.java SearchOption.java 

## Run String (run from src)
MAC: java -cp ./bin:./libs/mysql-connector-java-8.0.29.jar del4/Closet

WINDOWS: java -cp .\bin;.\libs\mysql-connector-java-8.0.29.jar Closet

## Video URL

## Other info

## Individual Contributions
### Deliverable 1
Emily: Create drive for team to work in, make initial draft with all the requirements/entities, communicate with team with deadlines and draft up ideas for potentiasl domains, create initial document for team to collab in

### Deliverable 2
Emily: Make doc for team to collab on diagram, draft initial ER diagram, draft inital entities and relationships, communicate with team

### Deliverable 3
Emily: Make video and readme, help test create statements and add referential triggers, make insert data, draft inital select statements, commnunicate with team on deadlines and requirements

### Deliverable 4
Emily: Help with adding labels to GUI (Paul did most GUI btw), add all select statements from previous del and add corresponding methods, communicate with team, spearhead readme edits, create github repo to work in

## Updates to ER diagram
- embedded location into item since entity attributes only consisted of item key
- Item no longer a weak entity because of first change
- completed specialization of Clothing into Shirt, Pants, and Outerwear (disjoint and total)
- fixed cardinality of Clothing has Brand relationship
- identified entity Color and its relationship with Item (M:N cardinality per initial requirements)
- eliminated year as being part of the primary key per the initial requirements (i.e., brand name is singular primary key)

## Structure of code

## Description of Application Activities
