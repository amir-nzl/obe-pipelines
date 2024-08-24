void call(Map config) {
    node {
        // Define and set parameters
        String gitBaseUrl = 'https://github.com/amir-nzl'
        String gitCredentialsId = 'github-token'
        String branch = 'master'

        // Tool configuration
        String mavenTool = tool name: 'Maven 3.9.8'

        withEnv(["PATH+MAVEN=${mavenTool}/bin"]) {
            try {
                stage('Checkout') {
                    // Checkout code from GitHub
                    checkout([
                    $class: 'GitSCM',
                    branches: [[name: branch]],
                    userRemoteConfigs: [[url: "${gitBaseUrl}/${config.name}", credentialsId: gitCredentialsId]]])
                }

                stage('Build') {
                    sh 'mvn clean package'
                }

                stage('Test') {
                    sh 'mvn test'
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
}
