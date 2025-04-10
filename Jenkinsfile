pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK17'
    }

    environment {
        IMAGE_NAME = "mamatha0124/java-microservice1"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Mamatha1206/ci-cd-javamicroservice.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                        sh '''
                        mvn sonar:sonar \
                            -Dsonar.projectKey=anushree-java-microservice \
                            -Dsonar.projectName="Mamatha Java Microservice" \
                            -Dsonar.projectVersion=1.0 \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.tests=src/test/java \
                            -Dsonar.login=$SONAR_TOKEN
                        '''
                    }
                }
            }
        }



        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Docker Build & Push') {
            when {
                branch 'develop'
            }
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
                withDockerRegistry([credentialsId: 'DOCKER_CREEDNTIALS_ID', url: '']) {
                    sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            when {
                branch 'develop'
            }
            steps {
                sh '''
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                '''
            }
        }
    }
}
