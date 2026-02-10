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
                bat 'mvn clean verify'
            }
            
        }



         stage('SonarQube Analysis') {
    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    steps {
        withSonarQubeEnv('SonarQube') {
            bat """
                mvn sonar:sonar ^
                -Dsonar.projectKey=coiffeur ^
                -Dsonar.projectName="Coiffeur Project" ^
                -Dsonar.host.url=http://localhost:9000 ^
                -Dsonar.token=%SONAR_TOKEN% ^
                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
                -Dsonar.junit.reportPaths=target/surefire-reports ^
                -Dsonar.java.coveragePlugin=jacoco 
            """
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