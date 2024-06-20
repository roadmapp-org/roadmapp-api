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
            agent{
                docker {
                    image 'openjdk:17'
                    reuseNode true
                }
            }
            steps {
                sh 'echo Performing Maven Build: ${ARTIFACT_ID}'
                sh 'chmod +x ./mvnw' // Add this line to set executable permission
                sh './mvnw -DjarName=${JAR_NAME} clean verify'
            }
        }
        
        stage('Build Container Image') {
    	   steps {
   	       		sh 'echo Building Container Image: ${IMAGE_NAME}'
   	       		sh 'docker build --build-arg JAR_FILE=${JAR_LOCATION} -t ${IMAGE_TAG} .' 
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
