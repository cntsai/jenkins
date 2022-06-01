.PHONY: all run clean jenkins

jenkins = crazyguitar/jenkins:latest

all: jenkins

jenkins:
	bash ./build.sh -j "jenkins" -t "$(jenkins)"

spot:
	cd terraform && terraform init && terraform apply

deploy:
	cd ansible && ansible all -i inventory -m ping
	cd ansible && ansible-playbook -i inventory playbook.yml

run:
	docker run -p 8080:8080 -p 50000:50000 "$(jenkins)"

destroy:
	cd terraform && terraform destroy

clean:
	docker ps -a | grep "$(jenkins)" | awk '{print $$1}' | xargs docker rm -f
	docker rmi "$(jenkins)"
