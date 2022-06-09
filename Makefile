.PHONY: all run clean jenkins push deploy migrate apply destroy

jenkins = crazyguitar/jenkins:latest

all: jenkins

help:
	@echo "Please use \`make <target>' where <target> is one of"
	@echo "  jenkins    to build a Jenkins image"
	@echo "  migrate    to migrate Jenkins from a backup"
	@echo "  deploy     to deploy a Jenkins service via Ansible"
	@echo "  run        to run a Jenkins service on a local machine"
	@echo "  apply      to create a spot instance on AWS"
	@echo "  destroy    to destroy a spot instance on AWS"
	@echo "  clean      to stop Jenkins and remove the old image."

jenkins:
	bash ./build.sh -j "jenkins" -t "$(jenkins)"

push:
	docker push "$(jenkins)"

deploy:
	cd ansible && ansible all -i inventory -m ping
	cd ansible && ansible-playbook -i inventory playbook.yml

run:
	docker run -p 8080:8080 -p 50000:50000 "$(jenkins)"

migrate:
	cd ansible && ansible all -i inventory -m ping
	cd ansible && ansible-playbook -i inventory migrate.yml

apply:
	cd terraform && terraform init && terraform apply

destroy:
	cd terraform && terraform destroy

clean:
	docker ps -a | grep "$(jenkins)" | awk '{print $$1}' | xargs docker rm -f
	docker rmi "$(jenkins)"
