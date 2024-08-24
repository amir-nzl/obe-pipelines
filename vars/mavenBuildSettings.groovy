def call(String creds='github-token', String repository='', String branch='master') {
    stage('Checkout Settings') {
        dir('dependency') {
            git(url: repository, branch: branch, credentialsId: creds)
            sh """
            cp ${env.WORKSPACE}/dependency/settings.xml ${env.WORKSPACE}/settings.xml
            """
        }
    }
}
