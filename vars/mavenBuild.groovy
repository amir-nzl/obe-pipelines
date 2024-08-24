def call(Map config) {
    node {
        // Define and set parameters
        String gitBaseUrl = 'https://github.com/amir-nzl'
        String gitCredentialsId = 'github-token'
        String branch = 'master'

        // Tool configuration
        def mavenTool = tool name: 'Maven 3.9.8'

        try {
            stage('Checkout') {
                // Checkout code from GitHub
                checkout(
                    [$class: 'GitSCM', branches: [[name: branch]],
                    userRemoteConfigs: [[url: "${gitBaseUrl}/${config.name}", credentialsId: gitCredentialsId]]])
            }

            stage('Build') {
                withEnv(["PATH+MAVEN=${mavenTool}/bin"]) {
                    sh 'mvn clean package'
                }
            }

            stage('Test') {
                withEnv(["PATH+MAVEN=${mavenTool}/bin"]) {
                    sh 'mvn test'
                }
            }

            stage('Deploy') {
                echo 'Deploying application...'
            // Add deployment steps here
            }
        } catch (Exception ex) {
            currentBuild.result = 'FAILURE'
            echo 'Pipeline failed.'
            throw ex
        } finally {
            if (currentBuild.result == 'SUCCESS') {
                echo 'Pipeline succeeded!'
            }
            echo 'Cleaning up...'
        }
    }
}
