Data
=====

MongoDB Installation for Windows
-------------------------------

- DL mongoDB for windows
- install
- add the bin folder into your path
- add your address (127.0.0.1) as MONGOHQ_URL environment variable
- by default, mongoDB is looking for a folder "/data/db/" which does not exist yet.
You can just create it or execute "mongod -f C:\path\to\your\mongodb.conf" to specify configuration of mongoDB
- in cmd, run "mongod" command to init the connection to database. It is currently pending for connection


MongoDB Test
------------

The MongoDBConnexionTest class is testing your mongoDB is well configured and reachable