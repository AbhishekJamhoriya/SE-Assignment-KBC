pipeline {
  agent {
    // Run on a build agent where we have the Android SDK installed
    label 'android'
  }
  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }
  stages {
    stage('Permissions') {
      // This stage is set to access some permissions in Ubuntu machine
      steps {
        sh 'ls -la ./gradlew'
        sh 'chmod +x ./gradlew'
        sh 'ls -la ./gradlew'
      }
    }
    stage('Compile') {
      steps {
        // Compile the app and its dependencies
        sh './gradlew compileDebugSources'
      }
    }
    stage('Unit test') {
      steps {
        
        // Compile and run the unit tests for the app and its dependencies
        sh './gradlew testDebugUnitTest'
        
        sh 'find . -name "TEST-*.xml" -exec touch {} \\;'

        // Analyse the test results and update the build result as appropriate
        junit '**/TEST-*.xml'

      }
    }
    stage('Build APK') {
      steps {
        // Finish building and packaging the APK
        sh './gradlew assembleDebug'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'
      }
    }
    stage('Static analysis') {
      steps {
        // Run Lint and analyse the results
        sh './gradlew lintDebug'
        
      }
    }
    stage('Deploy') {
      
      steps {
        // Build the app in release mode, and sign the APK using the environment variables
        sh './gradlew assembleRelease'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'
        
      }
      post {
        success {
          // Notify build succeeded
          sh "echo 'Congrqatulations! Build successful.'"
        }
      }
    }
  }
  post {
    failure {
      // Notify build failed
      sh "echo 'Oops! Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}'"
    }
  }
}
