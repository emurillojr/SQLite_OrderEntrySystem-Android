# SQLite_OrderEntrySystem-Android
SQLite with Order Entry System <br>
Take the Intent Order Entry System or Order Entry System original and convert it to use a SQL Lite database.  <br> 
Several modifications will need to be done to the Orders class. <br>
• Add a primary key field - make it an int. <br>
• Add a price field – must be a float type. <br>
• Note, the shipping fields are to remain as integers in the database <br>
• Static for NumberOfOrders, that needs to be deleted. <br>
• Pass the all database fields to the Parcel routines <br>
Your application will have the following functions: <br>
• The save button will now save the new record to a SQL Lite database  <br>
(remove the ArrayList<Order> construct you have used in previous labs).   <br>
• You will need a getTotalOrders function that ResultsActivity will use to pass back the number of orders to the  <br>
MainActivity class to be displayed. <br>
• You will need a getAllOrders function that will return a dataset with all orders in it  <br>
(to be used in ResultsActivity to show the orders that have been placed).   <br>
This will be triggered by going to the ResultsActivity intent.   <br> <br>
On the results page, you just need to show the first name, last name, ID and price.
• You will add a search by price function for Results Activity which will return the number of orders that  <br>
are above that price point.  This will be triggered by a button on the ResultsActivity page.  <br>
• Add a getOrder(id) function that will return an order based on the primary key, this will be triggered by  <br>
a button on the MainActivity page. <br>
• There will be no update function, if the Save button is hit a second time, you will get a duplicate order.	 <br>
• The save button on the MainActivity page is to go across the screen. <br>
Other notes/hints <br>
• If you change your create table statement or want to clear your data, the easiest way is to change  <br>
your database version variable. <br>
• You do not need to deal with error handling except making sure your textboxes handle proper data. <br>
• As before, you cannot hardcode the position of the chocolate bars when setting your spinner,  <br>
your code must get to the proper position. <br>
• You need to keep the ListView we have used before (i.e. that also means using the ArrayAdapter to fill it) <br>
• The output of price needs to be in currency format  <br>
