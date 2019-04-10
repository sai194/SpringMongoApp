# SpringMongoApp
SpringMongoApp

run mongo image
docker run -d -p 27000:27017 --name mongo mongo
https://hub.docker.com/_/mongo

to access from another docker image use
spring.data.mongodb.uri: mongodb://mongo:27000/test

else use
spring.data.mongodb.uri: mongodb://localhost:27000/test

Employee 
create and get
localhost:9800/employee
{
    "id": "5cae2123e99e060bd41a92f5",
    "email": "sai.yeluri@xyz.com",
    "fullName": "Sai",
    "managerEmail": "yeluri.sai@xyz.com"
}
localhost:9800/5cae2123e99e060bd41a92f5


Various query scenarios are written in QueryApp class .