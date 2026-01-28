# JewelAI E2E (Java + Maven)

Selenium WebDriver end-to-end tests for the JewelAI frontend (`http://localhost:3000`). Uses Maven, JUnit 5, Selenium, and WebDriverManager with a Page Object Model.

## Setup
1) Install Java 17+ and Maven.
2) Configure environment variables (or JVM properties):
```
BASE_URL=http://localhost:3000
API_URL=http://localhost:8080
E2E_EMAIL=your-user@example.com
E2E_PASSWORD=your-strong-password
HEADLESS=true   # set to false to see the browser
PROJECT_PREFIX=E2E-
```
3) Install dependencies and run tests:
```
mvn test
```

## Structure
- `src/test/java/com/jewelai/e2e/utils` – config + WebDriver factory.
- `src/test/java/com/jewelai/e2e/pages` – Page Objects (`LoginPage`, `HomePage`, `NewProjectPage`).
- `src/test/java/com/jewelai/e2e/api` – minimal API client for cleanup.
- `src/test/java/com/jewelai/e2e/tests` – JUnit suites: login, project creation, and cleanup.

## Test data hygiene
- Projects created in tests are prefixed with `PROJECT_PREFIX` (default `E2E-`).
- Created ids are deleted in `@AfterAll`; a separate test clears any leftover prefixed projects via the API.

## Notes
- Requires frontend running at `BASE_URL` and backend/API at `API_URL`.
- Uses headless Chrome by default; set `HEADLESS=false` to watch the run.
- WebDriverManager handles Chromedriver download automatically.
