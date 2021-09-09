# Exploring Mars Challenge

## Decisions taken
Since probes are valuable resources, any action that could damage or put a probe in an unknown state is considered forbidden. 
The covered cases are:
- **Colissions**
    1. Creating a new probe in a position already occupied by another probe;
    2. Taking a *MOVE* action to a position already occupied by another probe.
- **"Falling" from the map**
    1. Creating a new probe in a position out of the map bounds;
    2. Taking a *MOVE* action to a position out of the map bounds;
    3. Updating the map to a size that can't contain one or more existing probes in their current positions.

## API documentation
An interactive documentation is automatically generated with the use of [Swagger UI](https://swagger.io/tools/swagger-ui/) once the application is running.

## Compilation and testing
```
./mvnw package
```

## Running on Docker
```
docker build -t exploring-mars . 
docker run -dp 3000:3000 exploring-mars
```
