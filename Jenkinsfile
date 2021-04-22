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
        
        sh "echo '-------------------Asking for permissions--------------------'"
        
        sh 'ls -la ./gradlew'
        sh 'chmod +x ./gradlew'
        sh 'ls -la ./gradlew'
      }
      post {
        success {
          // Notify permissions granted
          sh "echo 'Permissions granted!!'"
        }
        failure {
          // Notify permissions denied
          sh "echo 'Oops!! Permissions denied.'"
        }
      }
    }
    stage('Compile') {
      steps {
        sh "echo '-------------------Compiling App and its dependencies--------------------'"
        
        // Compile the app and its dependencies
        sh './gradlew compileDebugSources'
      }
      post {
        success {
          // Notify compile successful
          sh "echo 'App compiled successfully.'"
        }
        failure {
          // Notify compile failed
          sh "echo 'Compile failed!!'"
        }
      }
    }
    stage('Unit tests') {
      steps {
        sh "echo '-------------------Running Unit Tests--------------------'"
        
        // Compile and run the unit tests for the app and its dependencies
        sh './gradlew testDebugUnitTest'
        
        // Rewriting previously written test results
        sh 'find . -name "TEST-*.xml" -exec touch {} \\;'

        // Analyse the test results and update the build result
        junit '**/TEST-*.xml'

      }
      post {
        success {
          // Notify tests successful
          sh "echo 'All tests passed successfully.'"
        }
        failure {
          // Notify tests failed
          sh "echo 'Some tests are failing.'"
        }
      }
    }
    stage('Build APK') {
      steps {
        sh "echo '-------------------Building APK--------------------'"
        
        // Finish building and packaging the APK
        sh './gradlew assembleDebug'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'
      }
      post {
        success {
          // Notify build successful
          sh "echo 'APK build successful'"
        }
        failure {
          // Notify build failed
          sh "echo 'APK build failed.'"
        }
      }
    }
    stage('Lint Check') {
      steps {
        sh "echo '-------------------Running Lint checks--------------------'"
        
        // Run Lint and analyse the results
        sh './gradlew lintDebug'
        
      }
      post {
        success {
          // Notify lint checks successful
          sh "echo 'Lint checks successful.'"
        }
        failure {
          // Notify lint checks failed
          sh "echo 'Lint checks failed.'"
        }
      }
    }
    stage('Deploy') {
      
      steps {
        sh "echo '-------------------Deploying APK--------------------'"
        
        // Build the app in release mode
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
