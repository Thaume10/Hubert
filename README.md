This is a Java desktop application that manages a network of couriers for efficient parcel delivery. The application, built using Java Swing for its user interface, facilitates the management of couriers, delivery routes, and time windows in urban settings. It also optimizes bicycle-based delivery tours by minimizing arrival times at warehouses and ensuring timely deliveries.

# Features
  Load or Create Delivery Tours: Option to load an existing tour or create a new one with date and map selection.
  Add Delivery Requests: Interactive map for selecting delivery locations with automatic courier and time window suggestions.
  Optimal Delivery Route Calculation: Automatic calculation of optimal courier routes based on delivery points.
  Courier Management: Add new couriers to expand delivery capacities.
  Archiving: Automatically backup current tours when loading new maps.
  Customized Courier Visualization: View individual courier routes with map highlights.

# Technologies Used
  Java & Swing: For developing the desktop UI.
  Maven: For dependency management and project build automation.
  MVC Architecture: Model-View-Controller design pattern to ensure clean code separation.

# Usage
  Start a Delivery Tour: Load an existing delivery route or create a new one by specifying the city map and courier details.
  Manage Couriers and Routes: Add new couriers, assign them delivery points, and compute their optimal routes using the built-in algorithm.
  Monitor Deliveries: View detailed courier schedules, time windows, and real-time delivery progress on the map.

# Algorithmic Solutions
  Traveling Salesman Problem (TSP): The application uses both classical and adaptive dynamic programming solutions to optimize delivery routes with and without time constraints.
  Dijkstra Algorithm: To optimize shortest path calculations for new delivery points.
