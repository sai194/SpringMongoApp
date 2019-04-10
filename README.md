# SpringMongoApp
SpringMongoApp

run mongo image
docker run -d -p 27000:27017 --name mongo mongo
https://hub.docker.com/_/mongo

to access from another docker image use
spring.data.mongodb.uri: mongodb://mongo:27000/test

else use
spring.data.mongodb.uri: mongodb://localhost:27000/test