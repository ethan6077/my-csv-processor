## DB Seeding Instructions

1. copy `lockeIdsForMEPA.csv` file into the csv folder

2. Tunnel to DB:
```shell
rea-as okta resi-myrea-staging-ReadWrite -- ./fargate-bastion subnet-059a6d05a0d1fa2ea subnet-0c3a187a5844241cb sg-0605c2a23686ad9a7 -r --tunnel "5432:me-profile-copilot-db-staging-dbinstance-jubsqgpestxs.cqiycnhd4zhg.ap-southeast-2.rds.amazonaws.com:5432"
```

3. Find the staging db password in lastpass
```shell
export DB_PASSWORD=xxxx
```

4. Set the starting and ending positions for the csv file in the code, e.g.
```shell
private val START_POSITION: Int = 400000
private val END_POSITION: Int = 500000
```
- This will seed data from line `400000` to line `500000`.
- `100000` records will be created for each table
- I found `100000` is the proper size to run for one time, while getting this number too big could exhaust memory.

5. Run the app

```shell
sbt
```

```shell
run csv/lockeIdsForMEPA.csv
```

Then we can move the position, and repeat

```shell
reload
```

```shell
run csv/lockeIdsForMEPA.csv
```
