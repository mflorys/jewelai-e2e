# Database Cleanup Strategy

This package contains the logic for deterministic database cleanup for E2E tests.

## Design
- **Isolation**: Each test run generates a unique `testRunId` (UUID).
- **Cleanup**: The `DbCleaner` removes all data associated with this `testRunId` after the test completes.
- **Safety**: Cleanup is restricted to local/dev environments.

## Schema Requirements (Proposal)
Since the E2E tests need to clean up their own data, the database schema MUST support isolation by `testRunId`.

**Required Changes:**
1.  Add a `test_run_id` column (UUID) to all entities created during tests (e.g., `users`, `projects`).
2.  Ensure this column is indexed for performance.
3.  Ensure the backend application propagates the `testRunId` (e.g., from a request header) to the database entities.

## Usage
- `BaseTest` handles the lifecycle: generates `testRunId`, initializes `DbCleaner`, and calls `cleanup()`.
- Tests extend `BaseTest`.

## Configuration
Database connection details are read from `config.properties`.
