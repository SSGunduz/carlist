# Events Service

APIs to handle events.

## Pre-requisites
- Java 8
- SBT
- MongoDB 3.4+


## MongoDB Installation on local

- Refer - https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

## Useful Commands:

```
# start the server
$ ./gradlew clean build
```

## ENDPOINTS:
```
# GET  http://server:port/carlist/api/v1/cars/search/colors/{color}
  e.g. http://localhost:8080/carlist/api/v1/cars/search/colors/red
# POST http://server:port/carlist/api/v1/cars/files/csvs/{dealerId}
  e.g. http://localhost:8080/carlist/api/v1/cars/files/listings/32
# PUT  http://localhost:8080/upload_csv/8c3f8012-a3a0-44e8-9466-16531687b85d
  e.g. http://localhost:8080/events

```
## TODOS/IMPROVEMENTS:
```
# exception handler test
# more test implementation/scenarios could be written
# exceptions and exception handler will be written
```
