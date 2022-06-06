FROM jenkins/jenkins

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
COPY jobs/ /usr/share/jenkins/ref/jobs/
COPY groovy/ /usr/share/jenkins/ref/init.groovy.d/
