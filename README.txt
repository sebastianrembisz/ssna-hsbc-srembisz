https://github.com/sebastianrembisz/ssna-hsbc-srembisz
https://github.com/sebastianrembisz/ssna-hsbc-srembisz.git

I. General info project
    - I tried to make it simple, so I skip some additional stuff like pagination
    - Due to time constraint I decided to write only end to end test. I didn't write any
    integration tests (I think that more tests on service levels whould increase project quality (reliability)
    or unit tests (here I almost don't write my own code, mostly integration, so testing on single class level will not increase reliability
    - still if you want I can add additional tests/features to the project
    - I didn't diveded maven project but normally
    - at list api whould be separate maven supproject so e2e tests have only access to appi not to whole project code
    - e2e test should be separate project. Right now it is treated as unit test (I even don't run it as integration test with fail-safe)
II. author env
    os: Windows10 64
    Intelij 16.3
    maven 3.3.9
    java 1.8.0_92
III start application
    1. IDE. np Intelij
    Find java class Application and start it. Tomcat should be started on port 8080
    2 from command line
        a.in project root directory (with pom.xml file) execute
        mvn package
        b. cd target
        c. java -jar ssna-0.1.0.jar
        you should notice message: that tomcat started on port 8080:
        Tomcat started on port(s): 8080 (http) with context path
    3. from ET2 Test
        a) from IDE run test SimpleSocialNetworkAppEnd2EndTest
        b) you should notice that tomcat is started on random port and you can access it till the end of all tests

IV. Usage
  1. Usage by hand:
    You can use PostMan
    a. to send your first post
    send POST REQUEST: localhost:8080/post/new
    with json body e.g:
    {
        "message":"First Post",

        "user": {
    "firstName":"Sebastian",
    "lastName": "Rembisz"
        }

    }

    you will get json response e.g:
    {
        "id": 4,
        "authorId": 1,
        "creationTime": "2019-02-24T11:56:18.076",
        "message": "First Post"
    }

    b. wall
    localhost:8080/wall/{authorId}
    e.g localhost:8080/wall/1 return messages posted for author with id 1
  2. For usage of all calls go to automated end to end test (real http requests)
    Open the class SimpleSocialNetworkAppEnd2EndTest and try to follow comments: // api for ...
    Those are places that I expose api documentation. Without additional helper code that make code more compact but hide implementation details