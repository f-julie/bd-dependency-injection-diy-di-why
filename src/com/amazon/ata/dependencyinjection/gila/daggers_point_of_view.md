# [TEMPLATE] <GROUP NAME> Dagger's Point of View GILA

## GILA Roles

Read more about the roles: [GILA Roles](https://w.amazon.com/bin/view/Amazon_Technical_Academy/Internal/HowTos/GILARoles)

## Activity

This activity will explore what Dagger does to provide our objects and their dependencies.
We will try looking at things from Dagger's point of view, to help us understand what all
of our annotating, Module, and Component classes are all about.

Before you start, complete the form below to assign a role to each member. If you have 3
people, combine the **Presenter** and **Reflector**.

|Team Roles	|Team Member	|
|---	|---	|
|**Recorder**: records all answers and questions, provides copies to team and facilitator.	|	|
|**Presenter**: talks to facilitator and other teams.	|	|
|**Manager**: keeps track of time and makes sure everyone contributes appropriately.	|	|
|**Reflector**: considers how the team could work and learn more effectively.	|	|

## Phase 1: Determine the Dagger root objects (5 minutes)

|A	|Start time:	|
|---	|---	|
|(10 minutes) Phase 1: Determine Dagger root objects	|	|

Open the `LegoBuilder` class in your Snippets package at `com/amazon/ata/dependencyinjection/classroom/lego`.

1. Looking at the `main` method, which classes’ constructors are called?
    1. 
2. Which classes does `LegoBuilder` use the Dagger component to obtain instances of?
    1. 
3. Which of those answers (question 1 or question 2)  represents the root object(s) of
   the Dagger dependency graph?
    1. 

## Phase 2: Build the Dagger dependency graph (20 minutes)

|A	|Start time:	|
|---	|---	|
|(20 minutes) Phase 2: Build the Dagger dependency graph	|	|

Go to the `main` method in `LegoBuilder`. As a group, step through the method as `main`
builds up a LEGO creation. When you encounter calls to `provide*` methods of the Dagger
component, fill in the details of the classes retrieved in the template below. Under
“Dependencies”, include any dependencies managed by Dagger, even if they are not *direct*
dependencies of the root object (so if a direct dependency depends on something, include
that something).

The three root objects provided by the `LegoBuilderComponent` interface, in the order they
are first retrieved, are started below.


1. **Root Object:** `SingletonBrick`
    1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
    2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
    3. **Dependencies:**
        1. **Dependency 1:** `<replace with dependency class name>`
            1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
            2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
            3. **Depends on:** `<list dependency types, add them to root object's Dependencies list>`
        2. **Dependency 2:** ...
            1. **Provided by:** ...
            2. **Singleton?:** ...
            3. **Depends on:** ...
1. **Root Object:** `TwoBrickCombination`
    1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
    2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
    3. **Dependencies:**
        1. **Dependency:** `<replace with dependency class name>`
            1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
            2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
            3. **Depends on:** `<list dependency types, add them to root object's Dependencies list>`
        2. **Dependency:** ...
            1. **Provided by:** ...
            2. **Singleton?:** ...
            3. **Depends on:** ...
2. **Root Object:** `NBrickCombination`
    1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
    2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
    3. **Dependencies:**
        1. **Dependency:** `<replace with dependency class name>`
            1. **Provided by:** `<select one: @Inject constructor | @Provides method>`
            2. **Singleton?:** `<select one: Yes, new instance | Yes, existing instance | No>`
            3. **Depends on:** `<list dependency types, add them to root object's Dependencies list>`
        2. **Dependency:** ...
            1. **Provided by:** ...
            2. **Singleton?:** ...
            3. **Depends on:** ...

**Sanity check:** How does your dependency graph compare to a [simplified UML version we have written](https://plantuml.corp.amazon.com/plantuml/form/encoded.html#encoded=VP7Dgi9038NtynHvW6iVGAGK5SHD4NNfPko4DjusAMbKV7l7dugNMzrbEDy9EMUophpQiQbTKVkkW-rPfah5xqoQFR4t4eR9H4KiJUEGBVc0HPoVUFuLNgcSA60NyIU_K4I-UmQMhJ5yagOgvuVFiHZ2Nioa6KCk1iHW5SBSboKg16oH0t9nWL9zM_tic3Y6P2Dl622GniNrfRVrDqA3Lab8-aitn6MD9gzhXsBTNY_mbdAelj4eVSlz4UdJEvT_r__BIPA-DpJi_nl8PR6b-ErN)?
Before moving on, resolve any inconsistencies between your answers above and the provided UML diagram.

## Phase 3: Reflection (15 min)

|A	|Start time:	|
|---	|---	|
|(15 minutes) Phase 3: Reflection	|	|

Once you’ve filled in the details of the dependency graph, use what you learned
from your investigation to answer these questions:

1. Did any `provide*` method(s) have arguments? Which method(s)?
    1. 
2. Can Dagger provide arguments to `@Provides` methods using classes with `@Inject` constructors?
    1. 
3. Can Dagger provide arguments to `@Provides` methods using other `@Provides` methods?
    1. 
4. How is this similar to the way Dagger obtains objects required by `@Inject` constructors?
    1. 
5. Why is `NBrickCombination` instantiated in a `@Provides` method instead of using `@Inject`?
    1. 
6. Is `BrownBrick` a dependency of `LegoBuilder`?
    1. 
7. Is `BrownBrick` in the Dagger dependency graph?
    1. 
8. Did `LegoBuilder` use the Dagger component class to provide all of the object instances that it needed?
    1.  
9. Do you think the pattern of instantiating some objects directly and some objects via the Dagger
   component makes sense? Why or why not?
    1. 

