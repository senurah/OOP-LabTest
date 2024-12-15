# Ticket Management System

This repository contains the development of a dynamic Ticket Management System project. Each feature is implemented in separate branches to provide a structured and modular approach to development. The `main` branch contains the final version of the project, integrating all the features. Below is a detailed description of each branch with links to access the files.

## Features Table

| Branch Name                  | Description                                                                                     | Access Files                                  |
|------------------------------|-------------------------------------------------------------------------------------------------|----------------------------------------------|
| [DefaultMain](https://github.com/senurah/OOP-LabTest/tree/DefaultMain)              | Initial structure of the project, setting up the foundation for the Ticket Management System. | [View Files](https://github.com/senurah/OOP-LabTest/tree/DefaultMain)         |
| [logging](https://github.com/senurah/OOP-LabTest/tree/logging)                     | Implements a logging mechanism to monitor system operations, including ticket additions and retrievals. | [View Files](https://github.com/senurah/OOP-LabTest/tree/logging)             |
| [TicketRetrievalStratergy](https://github.com/senurah/OOP-LabTest/tree/TicketRetrievalStratergy) | Adds support for ticket retrieval by priority or ID using a strategy pattern for flexibility.   | [View Files](https://github.com/senurah/OOP-LabTest/tree/TicketRetrievalStratergy) |
| [ExceptionHandling](https://github.com/senurah/OOP-LabTest/tree/ExceptionHandling) | Introduces custom exception handling (`InvalidConfigurationException`) to validate system configurations. | [View Files](https://github.com/senurah/OOP-LabTest/tree/ExceptionHandling)   |
| [EnhancingVendorBehaviour](https://github.com/senurah/OOP-LabTest/tree/EnhancingVendorBehaviour) | Extends the vendor functionality to support `FastVendor` and `SlowVendor` using polymorphism.   | [View Files](https://github.com/senurah/OOP-LabTest/tree/EnhancingVendorBehaviour) |
| [TicketStatisticsReporting](https://github.com/senurah/OOP-LabTest/tree/TicketStatisticsReporting) | Adds periodic reporting of ticket statistics, including total tickets added, removed, and current pool size. | [View Files](https://github.com/senurah/OOP-LabTest/tree/TicketStatisticsReporting) |
| [TicketPoolVisualization](https://github.com/senurah/OOP-LabTest/tree/TicketPoolVisualization) | Implements a JavaFX GUI for dynamic visualization of the ticket pool, showing real-time updates of ticket status. | [View Files](https://github.com/senurah/OOP-LabTest/tree/TicketPoolVisualization) |

## How to Use

1. Clone the repository:
   ```bash
   git clone https://github.com/senurah/OOP-LabTest.git
   cd OOP-LabTest
   ```

2. Checkout the branch you want to explore:
   ```bash
   git checkout branch-name
   ```

3. Each branch contains the implementation and related files for its feature. Use the table above to navigate to individual branches.

## Branch Descriptions

### DefaultMain
This branch serves as the starting point of the development project. It contains the initial structure of the project, including basic configurations and foundational files.

### Logging
Implements a robust logging mechanism to track and monitor operations such as ticket addition, retrieval, and system events. Logs are stored in a `logs.txt` file.

### TicketRetrievalStratergy
Introduces a flexible strategy pattern for ticket retrieval, allowing customers to retrieve tickets either by priority or by specific ID. Includes implementations for both strategies.

### ExceptionHandling
Enhances the system with a custom exception, `InvalidConfigurationException`, to handle invalid configurations. Includes validation for inputs such as ticket capacity and rates.

### EnhancingVendorBehaviour
Extends the `Vendor` class to support different types of vendors:
- `FastVendor`: Adds tickets at double the standard rate.
- `SlowVendor`: Adds tickets at half the standard rate.

### TicketStatisticsReporting
Adds a periodic statistics reporter that logs the total tickets added, removed, and currently in the pool every 5 seconds. Ensures real-time monitoring of system activity.

### TicketPoolVisualization
Implements a dynamic JavaFX GUI to visualize the ticket pool. The GUI includes:
- A `ListView` to display tickets in real time.
- Controls for starting and stopping the system.
- Input fields for configuration.

---

## Contributing
Feel free to open issues or submit pull requests for improvements or new features.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

