void call(Map config) {
    node {
        // Define and set parameters
        String gitCredentialsId = 'github-token'
        String mavenSettingRepo = 'https://github.com/amir-nzl/obe-pipelines.git'
        String mavenSettingRepoBranch = 'master'

        // Tool configuration
        String mavenTool = tool name: 'Maven 3.9.8'

        withEnv(["PATH+MAVEN=${mavenTool}/bin"]) {
            withCredentials([
                usernamePassword(credentialsId: 'nexus-deploy', usernameVariable: 'MAVEN_NEXUS_USERNAME', passwordVariable: 'MAVEN_NEXUS_PASSWORD')
            ]) {
                try {
                    stage('Checkout') {
                        // check out the source code from the same repository that the Jenkinsfile resides in.
                        checkout scm
                    }

                    stage('Fetch Maven Settings') {
                        mavenBuildSettings(gitCredentialsId, mavenSettingRepo, mavenSettingRepoBranch)
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
}
