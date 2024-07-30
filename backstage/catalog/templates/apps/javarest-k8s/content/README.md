**Basic rest api with curd operations + swagger + docker compose**

**Docker image build:**
```
docker build . -t ltim/restcrud
```

Once build check for the images , will look like below in your local system
```
C:\Users\gopu4>docker images
REPOSITORY                           TAG             IMAGE ID       CREATED         SIZE
ltim/restcrud                        latest          3ff4be33352f   2 days ago      463MB
```
Run the container in local:
```
docker run -p 8080:8080 ltim/restcrud
```
**Alternate way to start/stop the docker container**
````
docker-compose up
docker-compose down
````
API documentation :
Swagger URL:
```
http://localhost:8080/swagger-ui/index.html
```



