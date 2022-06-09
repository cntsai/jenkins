# Jenkins Docker

```bash
# edit files
# vim terraform/terraform.tfvars
# vim ansible/inventory
# vim Makefile (change image tag)

# create a spot instance on AWS
make apply

# create a jenkins
make

# push jenkins image
docker push "${image}"

# deploy jenkins on AWS
make deploy
```

### Demo

The following commands show how to handle failover and upgrade Jenkins.

```
# create a AWS instance
$ make apply

# restore Jenkins
$ make deploy

# login to EC2 instance to check
# 1. backup jobs is ready (crontab -l)
# 2. check jenkins folder is existed
# 3. open browser "http://${HOST}:8080" to check Jenkins is ready

# prepare a new Jenkins
$ make migrate

# open browser "http://${HOST}:8081" to check the new Jenkins is ready

# shutdown the old Jenkins
# 1. disable all jobs in the old Jenkins
# 2. set prepare to shutdown
$ docker stop ${old_jenkins}

# copy old artifact to the new Jenkins
# ssh login to the remote server
$ cp -r jenkins/jobs jenkins-backup/

# restart the new Jenkins
$ docker restart ${new_jenkins}

# open the new jenkins "http://${HOST}:8081"
```

### Reference

1. [How To Automate Jenkins Setup with Docker and Jenkins Configuration as Code](https://www.digitalocean.com/community/tutorials/how-to-automate-jenkins-setup-with-docker-and-jenkins-configuration-as-code)
2. [Docker jenkins](https://hub.docker.com/_/jenkins)
3. [How to get a list of installed Jenkins plugins with name and version pair](https://stackoverflow.com/q/9815273)
4. [cloudbees/jenkins-scripts](https://github.com/cloudbees/jenkins-scripts)
5. [ScriptApproval Document](https://javadoc.jenkins.io/plugin/script-security/org/jenkinsci/plugins/scriptsecurity/scripts/ScriptApproval.html)
6. [seed job asks for script approval in jenkins](https://stackoverflow.com/questions/43699190/seed-job-asks-for-script-approval-in-jenkins)
7. [Ticketfly/jenkins-docker-examples](https://github.com/Ticketfly/jenkins-docker-examples)
