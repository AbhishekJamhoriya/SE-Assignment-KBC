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
    stage('Temp') {
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
        
        cd './app/build/test-results/testDebugUnitTest'
        touch '*.xml'
        
        cd '../../../../'
        
        // Compile and run the unit tests for the app and its dependencies
        sh './gradlew testDebugUnitTest'

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
        androidLintParser pattern: '**/lint-results-*.xml'
      }
    }
    stage('Deploy') {
      when {
        // Only execute this stage when building from the `beta` branch
        branch 'main'
      }
      steps {
        // Build the app in release mode, and sign the APK using the environment variables
        sh './gradlew assembleRelease'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'

        // Upload the APK to Google Play
        androidApkUpload googleCredentialsId: 'Google Play', apkFilesPattern: '**/*-release.apk', trackName: 'beta'
      }
      post {
        success {
          // Notify if the upload succeeded
          mail to: 'raghav.1@iitj.ac.in', subject: 'New build available!', body: 'Check it out!'
        }
      }
    }
  }
  post {
    failure {
      // Notify developer team of the failure
      mail to: 'raghav.1@iitj.ac.in', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}"
    }
  }
}
