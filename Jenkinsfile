pipeline {
  
    agent any

    tools {
        maven 'maven-3.6.3'
        jdk 'jdk15'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '30'))
    }

    triggers{
        pollSCM('H/5 * * * *')
    }


    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Sonar') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=779a91a78162e4aaed4fdace5388882d35716ccf'
            }
        }        

    }
}