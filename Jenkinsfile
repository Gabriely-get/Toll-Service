pipeline {
    agent { any }
    stages {
        stage('build') {
            steps {
                sh './gradlew test'
                sh './gradlew build'
            }
        }
        stage('artifact') {
            steps {
                sh './upload-artifact.sh'
            }
        }
    }
}