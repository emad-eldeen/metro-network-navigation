# Metro Network Navigation
An application that will help you better navigate the complicated metro system finding shortest and fastest route between stations.

## Technical Requirements
- reading a metro network composed of lines, stations, and transfers from a Json file
- possibility to add/remove stations
- possibility to add transfers between lines
- calculation of fastest route between two stations in terms of hobs
- calculation of shortest route between two stations in terms of cost

## How to use
After running the app, input the name of the json file that has the metro network information to load. The file should be under `resources` folder. e.g. `prage.json`.
After that, a command from the command list could be used.

## Knowledge Used
- Java collections
- Java streams
- Regex
- Exception handling
- Graph theory
- Google's [Gson](https://github.com/google/gson)
- Design patterns:
  - Behavioral: command 
  - Creational: singleton

## Commands List
- `/list`: lists the metro lines
- `/add-head <line name> <station name>`: adds a metro station at the start of specified line. e.g. `/add-head "Linka A" test`
- `/append <line name> <station name>`: adds a metro station at the end of specified line. e.g. `/append "Linka A" test`
- `/remove <line name> <station name>`: removes the specified station from the specified line. e.g. `/remove "Linka A" test`
- `/output <line name>`: prints the list of metro stations in the specified line in order. e.g. `/output "Linka A"`
- `/add-transfer <from line name> <from station name> <to line name> <to station name>`: adds a transfer between the specified lines using the specified stations. e.g. `/add-transfer "Linka A" test "Linka B" test2`
- `/fastest-route <start line> <start station> <destination station>`: calculates and shows the fastest route (in terms of numbers of stations). e.g. `/fastest-route "Linka A" test test2`
- `/route <start line> <start station> <destination station>`: calculates and shows the shortest route (in terms of cost). e.g. `/route "Linka A" test test2`
- `/help`: shows the list of the available commands
- `/exit`: terminates the program