stage 'build' {
	node('jdk7') {
		env.PATH="${tool 'mvn-3.2.2'}/bin:${env.PATH}"
		sh 'mvn clean package'
		archive 'target/*.war'
	}
}


stage 'integration-test' {
	node('jdk7') {
		sh 'mvn verify'
	}
}

stage 'quality-and-functional-test'{
	parallel(qualityTest: {
        runWithServer {url ->
        	node('jdk7') {
        		echo 'sonar scan'
            	// sh 'mvn sonar:sonar'
        	}
        }
    }, functionalTest: {
        runWithServer {url ->
        	echo 'selenium test'
            // build 'sauce-labs-test'
        }
    })
}

stage 'production' {
	// sh 'puppet apply manifest.pp'
}
