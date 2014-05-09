Data Replication across multiple Servers with Fault Tolerance, High Availability and Incremental Scalability
------------------------------------------------------------------------------------------------------------

This is a Client version of Data Replication System.

READ Operation
To perform any of the Read operation, the input for object name is taken from the client. A hash function is calculated on the input object that generates a value between 0 and 6. Value generated plus next two of it will be the selected as the servers for the operation. A ping message is sent to these selected servers, the first server which replies is the one selected for the Read operation.
1.	Reply from just one server is enough to perform reads.
2.	Any of the three servers should be up for the Read operation to be successful.

WRITE Operation
1.	Object name is taken as the input to calculate the hash function. The hash value that is generated is between 0 and 6 i.e Total no. of Servers in the system. The hash value and succeeding two to it are selected as the servers for the write operation.
2.	The servers are first pinged to determine if they are up.
3.	A minimum of two servers must be up for a write to be successful.
4.	All the new objects, modifications of objects happen are written to a physical file in the system.
 


