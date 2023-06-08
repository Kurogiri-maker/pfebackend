pipeline {
    agent any
  environment {
        DOCKER_REGISTRY_USERNAME = 'kurogirixo'
        DOCKER_REGISTRY_PASSWORD = 'dckr_pat_Eg5RZq3aggg-_4n9p84hFwAyNfw'
    }
    stages {





        stage('Unit Test') {
            steps {
                sh 'mvn test '
            }
        }

        stage('Code Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
                jacoco(execPattern: '**//*target/jacoco.exec')
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

        stage('Build') {
             // Build the jar file
             steps {
                sh 'mvn clean install -DskipTests'
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


        stage('Log in to Azure and Connect to AKS') {
                  steps {
                      script {
                          // Azure login
                          sh 'az account set --subscription db8a4274-6875-495d-8ae8-4567e81675e9'
                          // Set the AKS cluster credentials
                          sh 'az aks get-credentials --name cdz --resource-group talancdz1'
                      }
                  }
        }

         stage('Deploy to Kubernetes') {
                    steps {
                        script {
                            // Deploy the deployment.yaml
                            sh 'kubectl apply -f deployment/deployment-frontcdz.yml'
                            sh 'kubectl apply -f deployment/service-frontcdz.yml'
                            sh 'kubectl apply -f deployment/deployment-talancdz.yml'
                            sh 'kubectl apply -f deployment/service-talancdz.yml'
                            sh 'kubectl apply -f deployment/deployment-talanocr.yml'
                            sh 'kubectl apply -f deployment/service-talanocr.yml'
                        }
                    }
                }



        /*
        stage('Run Docker Image') {
            steps {
                sh '''
                    # Pull Docker Image
                    docker pull kurogirixo/talancdz:latest

                    # Run Docker container and get container ID
                    container_id=$(docker run -d  --name container --network talan kurogirixo/talancdz:latest)

                    # Wait for 5 minutes
                    sleep 300

                    #Test

                    # Stop Docker container
                    docker stop $container_id
                '''
            }
        } */


    }
}
