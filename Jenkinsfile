pipeline {
    agent any

    triggers {
        githubPush()  
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/mounaad/coiffeur.git'
            }
        }
       

        stage('Build & Test') {
    steps {
        echo 'Building and testing...'
        bat '''
            mvn clean verify
            echo.
            echo Checking JaCoCo report:
            dir target\\site\\jacoco\\jacoco.xml
        '''
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: '**/target/jacoco.exec'
            
            // Vérifier le contenu du rapport
            script {
                bat '''
                    echo ====== JaCoCo Report Check ======
                    if exist target\\site\\jacoco\\jacoco.xml (
                        echo ✅ JaCoCo XML exists
                        type target\\site\\jacoco\\jacoco.xml | find /c "counter"
                    ) else (
                        echo ❌ JaCoCo XML NOT FOUND
                    )
                '''
            }
        }
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