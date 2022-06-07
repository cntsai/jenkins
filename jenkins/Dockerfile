FROM jenkins/jenkins

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false
COPY groovy/ /usr/share/jenkins/ref/init.groovy.d/
