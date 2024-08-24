def call(Map config) {
    pipeline {
        agent any

        parameters {
            string(name: 'GIT_BASE_URL', defaultValue: 'https://github.com/amir-nzl', description: 'Base URL for the Git repository')
        }

        tools {
            maven 'Maven 3.9.8'
        }

        stages {
            stage('Checkout') {
                steps {
                    // Checkout code from GitHub
                    git url: "${params.GIT_BASE_URL}/${config.name}", branch: 'master', credentialsId: 'github-token'
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
