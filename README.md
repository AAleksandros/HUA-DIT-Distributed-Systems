docker run --name DS_Blood_Donations \
-e POSTGRES_PASSWORD=Kondocker123!@# \
-e POSTGRES_USER=postgres \
-e POSTGRES_DB=DS_Blood_Donations \
-p 5432:5432 \
-d postgres
