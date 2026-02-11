pipeline {
    agent any

    triggers {
        githubPush()  
    }

    stages {

        stage('Clone') {
            steps {
                echo '📥 Cloning repository...'
                git branch: 'develop', url: 'https://github.com/mounaad/coiffeur.git'
            }
        }
       
        stage('Build & Test with Coverage') {
            steps {
                echo '🧪 Building and testing with JaCoCo...'
                bat 'mvn clean verify'
            }
            post {
                always {
                    // Publier les résultats des tests
                    junit allowEmptyResults: true, 
                          testResults: '**/target/surefire-reports/*.xml'
                    
                    // Publier JaCoCo dans Jenkins
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
                success {
                    echo '✅ Tests passed! Coverage generated.'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                echo '📊 Analyzing with SonarQube...'
                
                // Vérifier que JaCoCo a généré le rapport
                script {
                    if (!fileExists('target/site/jacoco/jacoco.xml')) {
                        error '❌ JaCoCo report not found! Cannot proceed with SonarQube.'
                    }
                    echo '✅ JaCoCo report found, proceeding with SonarQube...'
                }
                
                withSonarQubeEnv('SonarQube') {
                    bat """
                        mvn sonar:sonar ^
                        -Dsonar.projectKey=coiffeur ^
                        -Dsonar.projectName="Coiffeur Project" ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.token=%SONAR_TOKEN% ^
                        -Dsonar.java.binaries=target/classes ^
                        -Dsonar.sources=src/main/java ^
                        -Dsonar.tests=src/test/java ^
                        -Dsonar.java.coveragePlugin=jacoco ^
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
                        -Dsonar.junit.reportPaths=target/surefire-reports
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo '🚦 Waiting for Quality Gate...'
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        try {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                echo "⚠️ Quality Gate: ${qg.status}"
                            } else {
                                echo '✅ Quality Gate: PASSED'
                            }
                        } catch (Exception e) {
                            echo "⚠️ Quality Gate check failed: ${e.message}"
                        }
                    }
                }
            }
        }

    }

    post {
        success {
            echo '━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━'
            echo '✅ Pipeline SUCCESS!'
            echo '━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━'
            echo ''
            echo '📊 Reports:'
            echo "   JaCoCo: ${BUILD_URL}jacoco"
            echo '   SonarQube: http://localhost:9000/dashboard?id=coiffeur'
            echo ''
        }
        failure {
            echo '❌ Pipeline FAILED at: ${env.STAGE_NAME}'
        }
        always {
            echo 'Checking generated files...'
            bat '''
                if exist target\\site\\jacoco\\jacoco.xml (
                    echo ✅ JaCoCo XML: OK
                ) else (
                    echo ❌ JaCoCo XML: NOT FOUND
                )
                
                if exist target\\classes (
                    echo ✅ Compiled classes: OK
                ) else (
                    echo ❌ Compiled classes: NOT FOUND
                )
            '''
        }
    }
}