# Introduction

This example shows how to take the basic [Dropwizard example][1]  and make it deployable to Heroku.

# Running the App Locally
You can now run the example with the following commands.

* Build the example

        mvn package

* Start the example using foreman (get from installing the [Heroku toolbelt][2]. This will start your project using the command in the Procfile.

        heroku local
	
* Hit the example in your browser

	`http://localhost:5000/company`
	
* Post data into the application

        curl -H "Content-Type: application/json" -X POST -d '{"fullName":"Other Person","jobTitle":"Other Title"}' http://localhost:8080/people
	 
* See the data you posted

	`http://localhost:8080/school`

