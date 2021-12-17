## Dependency Injection - DIY DI? WHY?

**Branch name:** dependencyinjection-classroom

**AWS account:** (account number for your <Alias>ATAUnit3 account -- 
[find on Conduit](https://access.amazon.com/aws/accounts))
 
**Role:** IibsAdminAccess-DO-NOT-DELETE

**RDE workflows:**
- `rde workflow run dependencyinjection-classroom-tests`
- `rde workflow run dependencyinjection-classroom-cli`

In most software development projects, applications are developed iteratively
Accordingly, we have an updated version of the Discussion CLI we worked on in the DynamoDB
Annotations, Load, and Save lesson.

The class names are all the same, so be careful when importing in IntelliJ. Make sure
to only import classes under the `com.amazon.ata.dependencyinjection.discussion` package!

## Phase 0: Start `ada` and deploy your DynamoDB tables

Since we are continuing work on the Discussion CLI, we want to deploy new versions of the tables that we can
work with without impacting our previous lesson's work.

The new tables are:
* `DependencyInjection-Members`: containing members of the discussion app
* `DependencyInjection-Messages`: the actual messages that members have posted
* `DependencyInjection-Topics`: the list of topics up for discussion

1. Make sure `ada` is running with the credentials specified at the top of this README
   (the AWS account that ATA set up for you for this unit). [Instructions on `ada` and `aws`
   command line interfaces](https://w.amazon.com/bin/view/Amazon_Technical_Academy/Internal/HowTos/Get_AWS_Credentials_On_Laptop/).
1. Create the tables we'll be using for this activity by running the following `aws` CLI commands:
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name dependencyinjection-memberstable --template-body file://cloudformation/dependencyinjection/classroom/discussion_cli_table_members.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dependencyinjection-messagestable --template-body file://cloudformation/dependencyinjection/classroom/discussion_cli_table_messages.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dependencyinjection-topicstable --template-body file://cloudformation/dependencyinjection/classroom/discussion_cli_table_topics.yaml --capabilities CAPABILITY_IAM
   ```
1. Make sure the `aws` command runs without error.
1. Log into your AWS account on Conduit and verify that the tables exist and have
   sample data.

**GOAL:** `DependencyInjection-Members`, `DependencyInjection-Messages` and
`DependencyInjection-Topics` tables are created in your AWS Account, including sample data.

Phase 0 is complete when:
* `DependencyInjection-Members`, `DependencyInjection-Messages`, and `DependencyInjection-Topics`
  tables exist in your Unit 3 AWS account with some sample data

### Phase 1: Dagger, Dagger, Dagger
This version of the CLI comes with all the missing pieces from the DynamoDB Annotations, Load, and Save lesson.
You might even call it a "solution" of the previous lesson, and no test code debugging should be necessary :)

GOAL: Update our discussion CLI to use Dagger instead of the `get` methods in the `DiscussionCliRunner`
class, meeting the following criteria:

1. All `get*` methods constructing classes in `DiscussionCliRunner` are gone
   1. The only remaining method should be `main()`!
1. Get a fully instantiated `DiscussionCli` from a Dagger component
    1. Is IntelliJ having trouble with "symbol not found" on the Dagger component?
       1. If the concrete Dagger component class is red and doesn't recognize `create()`,
          the import is probably missing. 
       1. If IntelliJ can't add an import for the component class, remember that Dagger generates it during build.
          Create a variable for the *interface*, set it to `null`, build, and Brazil->Sync from Workspace.
1. Provide the classes needed to instantiate a `DiscussionCli` by either annotating their constructors with `@Inject`
   or creating `@Provides` methods in a module.
1. No code changes other than adding Dagger annotations in any non-Dagger classes.
1. `rde workflow run dependencyinjection-classroom-tests` continues to pass
   (NOTE: these tests pass with the starter code, so just because they pass
   does not mean you're done! You must satisfy the other criteria before this one)
   
Since Dagger is a structural, as opposed to behavioral, part of a service, it is difficult to write unit tests
that check whether it is being used. Instead, you should use `rde workflow run dependencyinjection-classroom-cli` and
manually test operations to check that your classes are all being injected correctly and your CLI is functioning
properly.

### Phase 2: I only test in prod

Once you've implemented Dagger and verified your CLI is running, let's test it out! 

Go to your terminal running the `ada` command and hit Ctrl-C to stop it.

Change your `ada` command to use the shared AWS account and role we used in DynamoDB Annotations, Load, and Save:

**AWS account:** 161358278188

**Role:** ATACurriculum-SharedParticipantDynamoDB

Restart `ada` with these settings and try running your CLI against the shared tables!

Try having chats with your teammates!

### Extensions

#### Extension 1: Inject Mocks
GOAL: Update the test classes under `classroom.discussion.dynamodb` and `classroom.discussion.handler` to use
`@InjectMocks` to instantiate the class under test, meeting the following criteria:

1. We should no longer have to instantiate each class being tested in the test
1. All tests that previously pass still pass
   (NOTE: these tests pass with the starter code, so just because they pass
   does not mean you're done! You must satisfy the other criteria before this one)

#### Extension 2: State of the union
At the moment, we have to pass the current state to each handler's `handleRequest` method in `DiscussionCli`'s
 `handleRequests` method. Instead of doing that ourselves, lets *inject* the state directly into each
  handler class using Dagger.
    1. This will require adding `DiscussionCliState` as a constructor parameter to each handler
    1. Think carefully if it should be a Singleton or not! What might happen if it isn't?
    1. This will also required updating the `DiscussionCliOperationHandler` interface so `handleRequest` doesn't
     expect a `DiscussionCliState`
  
#### Extension 3: Diving Deep
Take a look in your `annotated-generated-src/` directory and try to find the Dagger generated classes. 
Try to identify the Component and Module, and at least one Dagger-generated class instantiating one of our own 
classes that has an @Inject-annotated constructor.

[See this link for a deeper dive into how Dagger generated classes work](https://medium.com/mindorks/dagger-2-generated-code-9def1bebc44b)

#### More Extensions

At this point feel free to continue working on the discussion CLI, doing any extensions you didn't get to
in the DynamoDB Annotations, Load, and Save lesson. We've included those below, but feel free to be creative
and add any features you'd like, as long as we continue using Dagger to resolve the dependencies :)

Your fellow group members and participants making Dagger changes
are sure to appreciate any help you can provide as well!

For these, please change back to your personal AWS account, as some create new tables,
etc. You can choose any of these, no need to do them in the order listed.

#### Extension 4: Karmax5 Chameleon
1. Create a new table for MessageKarmaGifts:
    * messageKey: String "<topicName>|<timestamp" - concatenation of the topic name and the
                timestamp for the message of interest
    * memberId: String - the username of the member who gifted the karma
    * karmaPointsGiven: Number - the number of karma points given to the message
1. Create your model and annotate it
1. Create your DAO
1. Create the `GiveKarmaHandler` to handle this operation, and wire accordingly
    1. Ask for the message number (corresponds to indexes in `state.getListedMessages()`)
    1. Ask how many Karma points to give
    1. Do some validation (make sure it's a positive number)
    1. Create the Karma gift
    1. Set the next operation to be DiscussionCliOperation.VIEW_TOPIC_MESSAGES
1. More validation: make sure member doesn't give more karma points than they have
1. Accounting: Deduct karma points from the gifter. Makes it a more meaningful gesture!
1. Add points to the member who authored the message receiving the karma points.
1. Public recognition: update the display of topic messages to show karma points given
   for each message.

#### Extension 5: Oops, let me edit that

1. Allow editing an existing message (but only a member's own message). Keep a Boolean,
   `isEdited` so it can be displayed next to the message.
1. Also track the `lastEditedTime` for each message (if it has been edited).
1. Like the karma handler, you may need a new handler for this operation as well
