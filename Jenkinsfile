pipeline {
  
    agent any

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

    }
}