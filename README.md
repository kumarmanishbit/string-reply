# string-reply service

The supported rules are:

- `1`: reverse the string

- `2`: encode the string via MD5 hash algorithm and display as hex


## Build project

To build the project, simply run
```
./gradlew clean build
```

## Start project

To start the project, simply run
```
./gradlew bootRun
```

Once the service started, the endpoint will be available at `localhost:8080`, so you can make request to the service endpoint

```json
GET localhost:8080/reply/helloworld

{
    message: "helloword"
}
```

few examples:

- Applying rule 1 twice 
```
GET /v2/reply/11-kbzw9ru
{
    "data": "kbzw9ru"
}
```
- Applying rule 1 and 2
```
GET /v2/reply/12-kbzw9ru
{
    "data": "5a8973b3b1fafaeaadf10e195c6e1dd4"
}
```
- Applying rule 2 twice.

```
GET /v2/reply/22-kbzw9ru
{
    "data": "e8501e64cf0a9fa45e3c25aa9e77ffd5"
}
```
