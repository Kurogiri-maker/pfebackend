pipeline {
    agent any
  environment {
        MYSQL_HOST = 'mysql'
        MYSQL_PORT = '3306'
        MYSQL_DATABASE = 'tuto'
        MYSQL_USER = 'root'
        MYSQL_PASSWORD = ''
    }
    stages {
        stage('Checkout') {
            steps{
                checkout scmGit(
                    branches: [[name: 'iheb']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://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@github.com/Kurogiri-maker/pfebackend.git']])
            }
        }
        stage('Build') {
            // some block
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -X'
            }
        }
        stage('Deploy') {
            steps {
                sh "java -version"
                sh "docker build -t talancdz ."
            }
        }
    }
}