# CAR RENTAL APP - BY TEAM W

## Problem Statement
Build a Car Rental Backend System that enables users to book cars seamlessly while managing availability, user authentication, and rental records efficiently.

---

## Database Schema

### Cars Table
| Attribute     | Data Type  |
|--------------|-----------|
| car_id       | Long      |
| car_name     | String    |
| fuel_type    | String    |
| num_seats    | int       |
| price_per_day| double    |
| car_image    | String    |
| availability | boolean   |

### Users Table
| Attribute   | Data Type |
|------------|----------|
| user_id    | Long     |
| name       | String   |
| email      | String   |
| password   | String   |
| aadhar     | String   |
| phone      | Long     |
| profile_pic| String   |

### Booking Table
| Attribute    | Data Type  |
|-------------|-----------|
| booking_id  | Long      |
| user_id     | Long      |
| car_id      | Long      |
| from_date   | LocalDate |
| to_date     | LocalDate |
| total_amount| double    |

---

## Team Contributions

- **User Table**:
  - Agepati Jahnavi: Register/Signup
  - Nikita Nair: Login

- **Car Table**:
  - Prajwal P P: Git Integration, Add Cars
  - Shruthi: View Cars

- **Booking Table**:
  - Anna Biju: View Bookings
  - Lalith Abhiram: Bookings

---

## How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo-url.git
   ```
2. Navigate to the project directory:
   ```bash
   cd car-rental-app
   ```
3. Install dependencies:
   ```bash
   npm install / pip install -r requirements.txt (depending on tech stack)
   ```
4. Run the backend server:
   ```bash
   npm start / python app.py (depending on tech stack)
   ```
5. Access the application on `http://localhost:PORT`

---

## Contributing
Contributions are welcome! Feel free to fork the repo and submit a pull request.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

