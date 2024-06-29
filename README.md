# guru_deploy

This project functions similarly to Jenkins but is much simpler. It allows developers to focus solely on coding while it handles the deployment process. The project takes the developer's code and deploys it to the server, ensuring long-term service.

If you prefer not to use Jenkins, this tool offers an alternative. It monitors your GitHub repository for new commits, compares the latest commit with the current version, and pulls the latest changes to the specified location. You don't need to manually initiate the cloning process; you can set an interval for how often the tool should check for updates, with a minimum interval of one minute.

Currently, it supports deploying Java applications as JAR files. We are working on adding support for WAR files to assist developers who want to deploy their applications in that format as well.


So let's get started. To use the application, install it on your computer and run it as a standard Java application. It runs as a JAR file and has two endpoints:

Register Your Application: This endpoint is located at the root path and uses the POST method. Here, you register your application.

Check for Updates and Deploy: This endpoint is located at /clone. After registering your application, use this endpoint to start checking for the latest changes and comparing them to the saved versions. It will then build the updated version and copy the JAR file to the location specified during registration.

Once set up, the application will automatically deploy updates, allowing you to focus solely on coding. Enjoy the benefits of seamless deployment with our tool!
