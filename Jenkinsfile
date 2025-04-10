pipeline {
    agent any

    tools {
        // Specify the Maven tool installed in Jenkins
        maven 'Maven 3.8.6' // This should match the name you provided in the Global Tool Configuration
    }

    environment {
        // Define environment variables if needed
        DOCKER_IMAGE = 'mamatha0124/java-microservice1'
        DOCKER_REGISTRY = 'docker.io' // You can change this if you use another registry
        KUBERNETES_NAMESPACE = 'default'
        DEPLOYMENT_NAME = 'java-app'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the repository
                git (url:'https://github.com/Mamatha1206/ci-cd-javamicroservice.git',branch:'main')
            }
        }

        stage('Build') {
            steps {
                script {
                    // Run Maven build
                    sh 'mvn clean install'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t ${DOCKER_IMAGE} .'

                    // Log in to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'DOCKER_CREDENTIALS_ID', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login --username $DOCKER_USER --password-stdin'
                    }

                    // Push the Docker image to Docker Hub
                    sh 'docker push ${DOCKER_IMAGE}'
                }
            }
        }

        stage('Kubernetes Deployment') {
            steps {
                script {
                    // Deploy to Kubernetes
                    sh 'kubectl set image deployment/${DEPLOYMENT_NAME} java-app=${DOCKER_IMAGE} --namespace=${KUBERNETES_NAMESPACE}'
                    sh 'kubectl rollout status deployment/${DEPLOYMENT_NAME} --namespace=${KUBERNETES_NAMESPACE}'
                }
            }
        }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
