build:
	cd ./blogus-backend; mvn -q clean package -DskipTests
	cd ./client; npm install

run: build
	docker-compose -f docker-compose-dev.yml up --build

start:
	docker-compose -f docker-compose-dev.yml up

stop:
	docker-compose -f docker-compose-dev.yml down

clean: stop
	docker rmi `docker images -f "dangling=true" -q`