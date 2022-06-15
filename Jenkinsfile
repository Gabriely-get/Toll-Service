pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    environment {
        CI = True
        ARTIFACTORY_ACCESS_TOKEN = credentials('artifactory-access-token')
    }
    stages {
        stage('test') {
            sh './gradlew test'
        }
        stage('build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('upload to artifactory') {
            agent {
                docker {
                    image 'releases-docker.jfrog.io/jfrog/jfrog-cli-v2:2.2.0'
                    reuseNode true
                }
            }
            steps {
                sh 'jfrog rt upload https://192.168.1.230:8082/artifactory/ --access-token ${ARTIFACTORY_ACCESS_TOKEN} build/libs/Toll-1.0.jar generic-local/'
            }
        }
    }
}