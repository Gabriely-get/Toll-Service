pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh './gradlew test'
                sh './gradlew build'
            }
        }
        stage('upload-artifact') {
            steps {
                sh 'curl -u gabriely:Ilegra21! -X PUT "http://localhost:8082/artifactory/generic-local/artifact/test.jar" -T /build/libs/Toll-1.0.jar'
            }
        }
    }
}