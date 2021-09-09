# Exploring Mars Challenge

## Decisions taken
Since probes are valuable resources, any action that could damage or put a probe in an unknown state is considered forbidden. 
The covered cases are:
- **Collisions**
    1. Creating a new probe in a position already occupied by another probe;
    2. Taking a *MOVE* action to a position already occupied by another probe.
- **"Falling" from the map**
    1. Creating a new probe in a position out of the map bounds;
    2. Taking a *MOVE* action to a position out of the map bounds;
    3. Updating the map to a size that can't contain one or more existing probes in their current positions.

## Extra
The version at the `main` branch depends heavily on database constraints to deal with concurrent calls and performs poorly when a probe is updated with thousands of *MOVE* instructions (because every *MOVE* instruction generates an update command on the *probes* table).
To address that problem, I have developed a caching mechanism for accessing maps on operations that can change the map state. With that, many threads can hold a reference to the same instance of a map and make synchronized *MOVE* operations in memory.
I decided to commit this solution to a different branch (`feature/map-cache`) because, even tough the performance got much better, the code of the service layer became less clear in this version.

## API documentation
An interactive documentation is automatically generated with [Swagger UI](https://swagger.io/tools/swagger-ui/) once the application is running.

## Compilation and testing
```
./mvnw package
```

## Running on Docker
```
docker build -t exploring-mars . 
docker run -dp 3000:3000 exploring-mars
```
