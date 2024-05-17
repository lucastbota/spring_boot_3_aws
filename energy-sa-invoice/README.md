mkdir target/energy-sa-invoice
java -Djarmode=layertools -jar target/energy-sa-invoice.jar extract --destination target/energy-sa-invoice
docker build -t invoice-api .