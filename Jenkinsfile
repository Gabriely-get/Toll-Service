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
                sh '/home/ilegra/Documents/My-Github/Toll-Service/a.sh'
            }
        }
    }
}