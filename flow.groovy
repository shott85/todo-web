node('linux') {

	stage 'build'
		sh 'env' //print all available env vars
		git url: 'https://github.com/shott85/todo-web', branch: 'develop', poll: 'true'
		env.PATH="${tool 'M3'}/bin:${env.PATH}"
		sh 'mvn clean package'
		archive 'target/*.war'

	stage 'integration-test' 
		sh 'mvn verify'
		//step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml', healthScaleFactor: 1.0])
}

stage 'quality-and-functional-test'

	parallel(qualityTest: {
    	node('linux') {
    		echo 'sonar scan'
        	// sh 'mvn sonar:sonar'
    	}
    }, functionalTest: {
    	echo 'selenium test'
        // build 'sauce-labs-test'
    })

    try {
        checkpoint('Testing Complete')
    } catch (NoSuchMethodError _) {
        echo 'Checkpoint feature available in Jenkins Enterprise by CloudBees.'
    }


stage 'approval'
	input 'Do you approve deployment to production?'

stage 'production'
	echo 'mvn cargo:deploy'
	// sh 'puppet apply manifest.pp'

