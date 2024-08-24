def buildMaven(Map config = [:]) {
    pipeline {
        agent any

        tools {
            maven 'Maven 3.9.8'
        }

        stages {
            stage('Checkout') {
                steps {
                    // Checkout code from GitHub
                    git url: config.get('gitUrl'),
                        branch: config.get('branch', 'master'),
                        credentialsId: 'github-token'
                }
            }
            stage('Build') {
                steps {
                    sh 'mvn clean package'
                }
            }
            stage('Test') {
                steps {
                    sh 'mvn test'
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying application...'
                // Add deployment steps here
                }
            }
        }

        post {
            success {
                echo 'Pipeline succeeded!'
            }
            failure {
                echo 'Pipeline failed.'
            }
            always {
                echo 'Cleaning up...'
            }
        }
    }
}
