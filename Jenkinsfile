pipeline {
    agent any
    environment {
        GITHUB_USER = 'roadmapp-org'
        CONTAINER_REGISTRY = "ghcr.io/${GITHUB_USER}/"
    }

    stages {
        stage('Read POM and Set Environment Variables') {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    env.ARTIFACT_ID = pom.artifactId
                    env.JAR_NAME = "${pom.artifactId}-${env.BUILD_NUMBER}"
                    env.IMAGE_NAME = "${env.CONTAINER_REGISTRY}${pom.artifactId}"
                }
            }
        }
        stage('Build Application') {
            steps {
                script {
                    echo "Performing Maven build: ${env.ARTIFACT_ID}"
                }
            }
        }
        stage('Build Container Image') {
            steps {
                script {
                    echo "Build container image: ${env.IMAGE_NAME}"
                }
            }
        }
        stage('Publishing Image') {
            steps {
                script {
                    echo "Publishing container image: ${env.CONTAINER_REGISTRY}"
                }
            }
        }
    }
}
