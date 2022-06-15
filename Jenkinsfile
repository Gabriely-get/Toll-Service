pipeline {
    agent { label 'linux' }
    stages {
        stage('build') {
            steps {
                sh './gradlew test'
                sh './gradlew build'
            }
        }
        stage('upload-artifact') {
            steps {

                sh './a.sh'
            }
        }
    }
}