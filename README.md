# COMP307 Assignment 1
My AI project is written in Java. The way the project is structered is that each folder (part1, part2, part3) are 3 different projects that will have to be opened separately (if opened in IDE). 
The three parts include the implementation of 
* K-Nearest Neighbours
* Decision Trees
* Perceptrons
Complete code has been implemented and working for all parts

## Part 1
Part 1 can be found in the source folder under the package '**part1**' all necessary files are contained within that package. When passing in the files as the arguments, please ensure that the files are placed in the root directory or specifiy the file path to the file from current folder eg. '../data/part1/_filename_' 

After running, the program will output a text file to the root directory containing the results.

The initial value of k is set to 3 but can be changed through the variable 'k' in the field located in NearestNeighbour.java

For part 1, all parts are completed except for the implementation of K-Means Clustering method.

To compile you will need to be in the directory containing jar files and type `javac *.java` to run enter `java _MainClass_ <args>`

MainClass = NearestNeighbour.java

## Part 2
Part 2 can be found in the source folder with all necessary files under the package '**part2**'. When passing in data files through arguments, ensure that all data files are placed in the root directory or specify the pathname in the argument such like './data/part2/_filename_'. 

All output for part 2 will be through the terminal/console.

To compile you will need to be in the directory containing jar files and type `javac *.java` to run enter `java _MainClass_ <args>`

MainClass = DecisionTree.java

## Part 3
Part 3 can be found in the source folder under the package '**part3**'. When passing in data through the arguments, ensure that all data files are place in the root directory or specify the filepath in the argument such like './data/part3/_filename_'.

Within the Perceptron.java class, there are multiple print statements that have been commented out, by uncommenting you will be able to see the weights at every iteration and whether the images have been guessed correctly through each iteration

All output will be to the terminal/console.

To compile you will need to be in the directory containing jar files and type `javac *.java` to run enter `java _MainClass_ <args>`

MainClass = Perceptron.java

## Extra
All programs can also be ran through an IDE such as Eclipse by running once and next to the line numbers where the main method is located, right click on the green run button and 'edit run configurations'. In the program arguments, enter the arguments and run the program that way.

