pipeline {
    agent any
    environment {
        GITHUB_USER = 'tobiolea97'
        CONTAINER_REGISTRY = "ghcr.io/${GITHUB_USER}/"
        ARTIFACT_ID = readMavenPom().getArtifactId()
        JAR_NAME = "${ARTIFACT_ID}-${BUILD_NUMBER}"
        IMAGE_NAME = "${CONTAINER_REGISTRY}${ARTIFACT_ID}"
    }

    stages {
        stage('Build Application') {
            steps {
                sh 'echo Performing Maven build: ${ARTIFACT_ID}'
            }
        }
        stage('Build COntainer Image') {
            steps {
                sh 'echo Build container image: ${IMAGE_NAME}'
            }
        }
        stage('Publishing Image') {
            steps {
                sh 'echo Publishing container image: ${CONTAINER_REGISTRY}'
            }
        }
    }
}