void call(String creds='github-token', String repository='', String branch='master') {
    dir('dependency') {
        git(url: repository, branch: branch, credentialsId: creds)
        sh """
        echo "Listing contents of the current directory:"
        ls -l
        echo "Copying settings.xml to workspace"
        cp maven-settings/settings.xml ${env.WORKSPACE}/settings.xml
        echo "Listing contents of ${env.WORKSPACE} to verify copy:"
        ls -l ${env.WORKSPACE}/settings.xml
        """
    }
}
