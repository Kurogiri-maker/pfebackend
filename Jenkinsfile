pipeline {
    agent any
  environment {
        MYSQL_HOST = 'mysql'
        MYSQL_PORT = '3306'
        MYSQL_DATABASE = 'tuto'
        MYSQL_USER = 'root'
        MYSQL_PASSWORD = ''
        DOCKER_REGISTRY_USERNAME = 'kurogirixo'
        DOCKER_REGISTRY_PASSWORD = 'dckr_pat_Eg5RZq3aggg-_4n9p84hFwAyNfw'
        DOCKER_REGISTRY_URL = 'https://hub.docker.com'

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
        stage('SonarQube analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=iheb'

            }
        }
        stage('Docker Login') {
            steps {
                script {
                    sh "docker login -u ${DOCKER_REGISTRY_USERNAME} -p ${DOCKER_REGISTRY_PASSWORD}"
                }
            }
        }
        stage('Build Docker image') {
            steps {
                sh "java -version"
                sh "docker build -t kurogirixo/talancdz:latest ."
            }
        }
        stage('Push to Docker Hub') {
            steps {
                sh "docker push kurogirixo/talancdz:latest"
            }
        }

    }
}