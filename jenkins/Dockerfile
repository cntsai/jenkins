FROM jenkins/jenkins

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
ENV CASC_JENKINS_CONFIG /var/jenkins_home/casc.yaml

COPY jobs/ /usr/share/jenkins/ref/jobs/
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
COPY casc.yaml /var/jenkins_home/casc.yaml
COPY groovy/ /usr/share/jenkins/ref/init.groovy.d/
COPY nodes/ /usr/share/jenkins/ref/nodes
