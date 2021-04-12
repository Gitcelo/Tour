# Day tours - group 3D
## Authors:
* Guðrún Herdís Arnarsdóttir - gha18@hi.is - gha18
* Marcelo Felix Audibert - mfa5@hi.is - Gitcelo
* Marzuk Ingi Lamsiah Svanlaugar - mil4@hi.is - Marzuklngi
* Natanel Demissew Ketema - ndk1@hi.is - natidemis

## Packages
* **vidmot**: Contains a simple search UI
* **vinnsla**: Contains the programs for the user
* **data**: Contains the database and programs that set up and do calls on the database

## Database setup
* Download the sqlite jdbc drivers from here: https://dbschema.com/jdbc-driver/Sqlite.html
* To use the drivers within an IDE put the .jar file into the IDE's classpath or run MakeDatabase.java 
in a terminal with the following command: `java -cp ${Path to folder containing the .jar file}\sqlite-jdbc-3.34.0.jar; data.MakeDatabase`.
  
* PopulateDatabase can be run with the same command as MakeDatabase except the end of the command, after the semicolon, is `data.PopulateDatabase`.
  
* Run `MakeDatabase.java`, in the package `data`, from the folder src to create the .db database file
* Run `PopulateDatabase.jave`, in the package `data`, from the folder src to populate the .db database file.

**Todo**: Klára readme-ið

**P.s. MuNa Að JaVaDoCa**