.PHONY: all run clean jenkins

jenkins = sifive/jenkins:latest

all: jenkins

jenkins:
	bash ./build.sh -j "jenkins" -t "$(jenkins)"

run:
	docker run -p 8080:8080 -p 50000:50000 "$(jenkins)"

clean:
	docker ps -a | grep "$(jenkins)" | awk '{print $$1}' | xargs docker rm -f
	docker rmi "$(jenkins)"
