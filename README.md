# Overview
A Project in Spring-Boot in server Side running plagiarism engine using Javaparser for parsing, AWS RDS for Database and AWS S3 for File storage with ReactJS in client side. 



Rahul Verma <verma.rah@husky.neu.edu>,

# Installaiton Help:

## You will need to install these to you system :
  - Node.js and npm  [here](https://www.npmjs.com/get-npm).(check `with node -v and npm -v`)
  - Java SDK (1.8 )[here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)(check with `java -version`)
  - Maven [here](https://maven.apache.org/download.cgi) (check with `mvn --version`)
### Pre Install instructions (For better understanding read InstallationGuide.pdf):
  - Enable JPA annotation preprocessing for your editor (Eclipse/IntelliJ): follow guidelines from https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html#_usage_within_the_ide
  - Please request credentials file for connecting to AWS S3 from the team
  - Please request credentials and instructions for connecting to  AWS RDS MySQL database from the team
  - Create the following folders in your local machines (be aware of the upper and lower case characters):
  - tempAWSDownload : inside user home directory
  - tempAWSDownload/Reports
  - Please contact for APTED jar dependency file.

## How To run the Spring Boot Server and React Application

### To Run Client/UI go to ` ./phaseC/Application/client` and do the following to start REACT_APP:
  - npm install
  - Change the main index.html location and entry index.js files
      - open file  `./node_modules/react-scripts/config/paths.js`
      - Around line `37` and `54` change the following configuration (changes at 2 places):
        - `appHtml: resolveApp('index.html'),` => `appHtml: resolveApp('public/index.html'),`

  - `npm start`: We get the React Web-pack up an running. (to Run Client)
  -  `npm test`: To test Render Test Cases. 

### To run server go to `./phaseC/Application/server` and do the following steps in order. (Note: you will need maven for that)
- `mvn install:install-file -Dfile=R:\MSD\Project\APTED.jar -DgroupId='org.ted.compare' -DartifactId=apted -Dversion='1.0' -Dpackaging=jar` where R:\MSD\Project\APTED.jar will be replaced with the apted jar file location, do not change anything else. (NOTE: replace the quotes in `'org.ted.compare'` and change it to `org.ted.compare` if the build is not successfull)
- `mvn install -DskipTests` : To Install the project without Test
- `mvn spring-boot:run` : To Run the server ( You will see `Spring` written in termainal)
- `mvn test`: To Test Server Cases. 

Note: Logger works fine when jar is built through the IDE, through maven, logger logs in console


Our file structure will look like this:

    - client
      - node_modules
        - ...various modules...
      - public
        - index.html
      - src
        - components
           - .... various components and Tests.....
        - index.js  
      - pom.xml
      - .gitignore
      - package.json
      - procfile
    - Server
      - src
        - main
          - server.plagiarism
        - test
          - server.plagiarism  
    - README.md
