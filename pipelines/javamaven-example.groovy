// Example - Jenkins Pipeline for Java Maven

pipeline {
    agent any
    environment {
        ARTIFACTORY_URL = credentials('ARTIFACTORY_URL')
        ARTIFACTORY_USERNAME = credentials('ARTIFACTORY_USERNAME')
        ARTIFACTORY_PASSWORD = credentials('ARTIFACTORY_PASSWORD')
        FORTIFY_USERNAME = credentials('FORTIFY_USERNAME')
        FORTIFY_PASSWORD = credentials('FORTIFY_PASSWORD')
        SONAR_URL = credentials('SONAR_URL')
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Fortify') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'FORTIFY', usernameVariable: 'FORTIFY_USERNAME', passwordVariable: 'FORTIFY_PASSWORD')]) {
                    sh 'sourceanalyzer -b MyApp -clean'
                    sh 'mvn clean package'
                    sh 'sourceanalyzer -b MyApp -Xmx4G -debug -verbose -logfile fortify.log mvn fortify:translate fortify:scan'
                }
            }
        }
        stage('Artifactory') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'ARTIFACTORY', usernameVariable: 'ARTIFACTORY_USERNAME', passwordVariable: 'ARTIFACTORY_PASSWORD')]) {
                    sh 'mvn deploy'
                }
            }
        }
    }
}
