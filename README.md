# HOSPITAL PROJECT - BACKEND API

## Installation
### Prerequisites
- Java 17 or higher
- Maven
- MySQL

### Installation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/vctrgz/hospital_backend.git
   ```
2. Change to the project directory:
   ```sh
   cd hospital_backend
   ```
3. Set up the database:
   - Create a MySQL database named `hospital`.
   - Update the `application.properties` file with the database credentials.
4. Build the project and install dependencies:
   ```sh
   mvn clean install
   ```

### Available Endpoints
- **GET /nurse/nurses**: Get all registered nurses.
- **POST /nurse/login**: Authenticate a nurse.
  - Request body: `{ "user": "username", "password": "password" }`
- **POST /nurse/create**: Create a new nurse.
  - Request body: `{ "name": "name", "user": "email", "password": "password" }`
- **POST /nurse/update**: Update the information of an existing nurse.
  - Request body: `{ "id": "nurse_id", "newName": "new_name", "newUser": "new_user", "newPassword": "new_password" }`
- **GET /nurse/name/{name}**: Find nurse by name.
- **GET /nurse/id/{id}**: Get nurse details by ID.
- **DELETE /nurse/delete/{id}**: Delete a nurse by ID.


### a. Work distribution among the different members of the group with their own project and repository:		  
1. Information of all registered nurses **-Albert**  
2. Login validation of a nurse **-Víctor**  
3. Search for nurses by name **-Jiajiao**  
4. Performance check using Postman  
          
### b. Group work:  
1. Creation of a common project and repository  
2. Assignment of a branch to each member of the group  
   where he/she will develop his/her part again      
3. Integration in the common repository  
4. performance check using Postman  

### Specifically, the minimum functionalities it must have are:
* Login/Registration/Modification/Discharge of nurses
* A nurse must be able to
	- Register a new patient
	- Add written information about a patient
	- Modify a patient's personal data
	- Delete a patient
	- Record new values ​​of a patient's vital signs
	- See a graph of the evolution of different vital 
      constants in a given period of time

## Usage
### Running the Project
To start the application:
```sh
mvn spring-boot:run
```
The application will be available at `http://localhost:8080`.

> **It is desired to record which nurse is the author of each modification 
> and new data record**

> [!NOTE]
> * Program that allows the entry of these in an orderly and easy way.
> * Easy to use and incorporating all patient data.
> * The data referring to the vital constants are reflected in graphic format.

## License
This project is licensed under the MIT License.
