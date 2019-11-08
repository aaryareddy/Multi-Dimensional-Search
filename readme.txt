CS 6301.001 Implementation of Data Structures and Algorithms

Long Project 4

Team Members:

Aarya Vardhan Reddy Paakaala (AXP 170019)
Harshil Hiten Malavia (HHM 180000)
Mrugapphan Kannan (MXK 170014)
Shivapraksh Sivagurunathan (SXS 180104)

Prerequisites:

Eclipse Java
Java(JDK) 8

Compiling Instructions - 

Create a new Java Project
Add all the tets files to the directory 
Create a new Package with name axp170019
Add the MDS.java, LP4Driver.Java files to the axp170019 package.
The selected test file needs to be configured as an argument in "run configurations"
Now run the file with the main function ( LP4Driver.java)
The output would be displayed in the command line.

 ---------------------------------------------------------------------------
DESCRIPTION: 
1. For each product/ item, we've made a class called Product consisting - 
   class Product { Long id, Money price, HashSet<Long> desc}
   Why used HashSet? - to avoid duplicates and fast modifications

2. We've used a TreeMap<Long, <Product>> which maps an id to it's Product.
   TreeMap<Long, <Product>> prodMap;

3. We've used a HashMap<Long, HashSet<Product>> which maps a description to a 
   set of all such products containing that description. 

   HashMap<Long, HashSet<Product>> dMap;

4. TreeMap (ProdMap) contains the product id and the reference to the product object. 
HashMap (dMap) contains the mapping of description and all the products that include the specified description.

---------------------------------------------------------------------------
RESULTS: 

+-------------------------------------------------------------------------+
| File         | Output          |   Time (mSec)     | Memory (used/avail)|
|-------------------------------------------------------------------------|
| 401          | 1448            | 10                | 1 MB / 252 MB      |
|-------------------------------------------------------------------------|
| 402          | 4142            | 6                 | 1 MB / 252 MB      |
|-------------------------------------------------------------------------|
| 403          | 11132           | 17                | 3 MB / 252 MB      |
|-------------------------------------------------------------------------|
| 404          | 52018           | 40                | 5 MB / 252 MB      |
|-------------------------------------------------------------------------|
| 405          | 16494           | 48                | 10 MB / 252 MB     |
|-------------------------------------------------------------------------|
| 406          | 19005           | 140               | 21 MB / 252 MB     |
|-------------------------------------------------------------------------|
| 407          | 489174          | 200               | 20 MB / 252 MB     |
|-------------------------------------------------------------------------|
| 420          | 1016105100      | 23000             | 1300 MB / 3987 MB  |
|-------------------------------------------------------------------------|

 Time and Memory might change, as you run the test the program on a 
different system, but they could be comparable to the above values.
