void call(String creds='github-token', String repository='', String branch='master') {
    stage('Fetch Maven Settings') {
        dir('dependency') {
            git(url: repository, branch: branch, credentialsId: creds)
            sh """
            ls -l
            cp maven-settings/settings.xml ${env.WORKSPACE}/settings.xml
            """
        }
    }
}
