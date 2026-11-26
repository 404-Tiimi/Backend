# Haaga-Helia survey service (Course Ohjelmistoprojekti 1)
This project is a collaboration project in a course Ohjelmistoprojekti 1 where participants create functioning application that has possible real life use case.

Questionnaire is a survey service where users can anonymously answer surveys via shared link. In the current state the survey creation supports open and multiple choice questions.
<img width="987" height="737" alt="image" src="https://github.com/user-attachments/assets/f12ce902-234b-4bca-8149-0c8bf26aa97a" />

# Technology stack
The backend is built with java 25 and spring boot, using:

- **Spring Boot**
  - Spring MVC web pages and REST endpoints.
  - Spring Security authentication and access control.
  - Spring Data JPA database access.
- **Thymeleaf** server-rendered HTML pages.
- **PostgreSQL** storing surveys, questions, and responses.
- **Maven** dependency management, testing, and builds.
- **Docker** packaging and deploying the application.

# Application charts
## Java chart
<img width="778" height="552" alt="Javakaavioprojekti 2" src="https://github.com/user-attachments/assets/e62a6d7c-8072-4806-92cd-aebb6a7e1cd9" />

## Database UML
<img width="1302" height="612" alt="questionnaire-domain" src="https://github.com/user-attachments/assets/cc22d0b4-1f59-47d3-9583-346ec7160d8b" />

# Implemented capabilities
- Create surveys with a title and multiple questions.
- Support open-ended text questions and single-choice questions.
- Edit surveys and add, update, or remove questions.
- View survey details and list existing surveys.
- Accept anonymous responses through the REST API.
- Group answers from one submission into a response session.
- Require login to access the survey management interface.

# Usage and Authentication
*explanation to the difference between the survey management interface which requires login and the public rest api.*
*Needs to be written*

# REST API
*list of available endpoints*
*Needs to be written*

# Contribution
???

# Deployment
This projects backend and database are deployed in https://csc.fi/ 
via services
Rahti (Backend)
Pukki (Database)
