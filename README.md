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

### Reference

1. [How To Automate Jenkins Setup with Docker and Jenkins Configuration as Code](https://www.digitalocean.com/community/tutorials/how-to-automate-jenkins-setup-with-docker-and-jenkins-configuration-as-code)
2. [Docker jenkins](https://hub.docker.com/_/jenkins)
3. [How to get a list of installed Jenkins plugins with name and version pair](https://stackoverflow.com/q/9815273)
