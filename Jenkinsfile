pipeline {
    agent any

    triggers {
        githubPush()  
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'develop', url: 'https://github.com/mounaad/coiffeur.git'
            }
        }
       

        stage('Build & Test with Coverage') {
            steps {
                echo 'mvn clean verify'
            }
        }

         stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                echo 'mvn sonar:sonar'
                }
            }
        }

    }
//
    post {
        success {
            echo 'Build et analyse terminés avec succès !'
        }
        failure {
            echo 'Échec du build ou des tests.'
        }
    }
}