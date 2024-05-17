mkdir target/energy-sa-customer
java -Djarmode=layertools -jar target/energy-sa-customer.jar extract --destination target/energy-sa-customer
docker build -t customer-api .