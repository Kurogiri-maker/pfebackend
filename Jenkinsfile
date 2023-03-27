pipeline {
    agent any
  environment {
        DOCKER_REGISTRY_USERNAME = 'kurogirixo'
        DOCKER_REGISTRY_PASSWORD = 'dckr_pat_Eg5RZq3aggg-_4n9p84hFwAyNfw'
    }
    stages {


        stage('Build') {
            // some block
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }


        stage('Unit Test') {
            steps {
                sh 'mvn test '
            }
        }

        stage('Code Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
                jacoco(execPattern: '**/target/jacoco.exec')
            }
            post {
                always {
                     publishHTML(target: [
                      allowMissing: false,
                      alwaysLinkToLastBuild: true,
                      keepAll: true,
                      reportDir: 'target/site/jacoco',
                      reportFiles: 'index.html',
                      reportName: 'Jacoco Code Coverage Report'
                    ])
                }
            }
        }
        
        stage('Scan') {
            steps {
                withSonarQubeEnv(installationName: 'sq1') { 
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
                }
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

        stage('Run Docker Image') {
            steps {
                sh '''
                    # Pull Docker image
                    docker pull kurogirixo/talancdz:latest

                    # Run Docker container and get container ID
                    container_id=$(docker run -d kurogirixo/talancdz:latest)

                    # Wait for 6 minutes
                    sleep 360

                    # Stop Docker container
                    docker stop $container_id
                '''
            }
        }


    }
}
