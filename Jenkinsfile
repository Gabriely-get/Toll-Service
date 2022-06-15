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
                sh 'chmod +x -R ${env.WORKSPACE}'
                sh './upload-artifact.sh'
            }
        }
    }
}