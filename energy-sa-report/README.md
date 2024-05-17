mkdir target/energy-sa-report
java -Djarmode=layertools -jar target/energy-sa-report.jar extract --destination target/energy-sa-report
docker build -t report-api .