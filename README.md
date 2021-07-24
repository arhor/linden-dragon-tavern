# dnd-homebrew-app (diploma project)

## Database setup

The following steps required to configure application database:
* PostgresSQL should be available on the localhost (standard port 5432)
* the database with name `diploma_db` should exist
* the following command from the project root-directory should be run to update database state

```
gradlew :diploma-server:module-main:flywayMigrate
```

## Project setup

To run both client and server in dev-mode run the following commands in separate terminal sessions:

```
npm run --prefix diploma-client serve
```
```
gradlew :diploma-server:module-main:bootRun
```