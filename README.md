## WomenWhoCode
CodePath group project. Bring the WWCode experience mobile. 

**Problem/Opportunity:** Currently, our reach happens through expanding networks 
and our weekly Code Review. We want to be able to reach an engineer even if we 
don’t have a network in their area, more directly, and make it so they feel like 
they are apart of WWCode regardless of where they are in the world. Our goal is 
to reach 1 million engineers.  

**Product Overview:** This experience will leverage existing internal data: 
WWCode events, event streaming, resources, internal blogs, job posts, inbound 
conference data. We will also provide the ability to connect with other like 
minded engineers through in app direct messaging and event driven groups. 

We are going to find out what interests our members based on a personalization 
flow in order to feed them proper data and connect them with like minded members 
in their area and around the world. This is creating a more immediate, and 
direct way to connect regardless of where you are located.

## Member Stories

The following **required** functionality is completed:

Onboarding:
* [X] On app install we will ask user if we can access their location settings
* [X] Member will go through a personalization flow that is persisted via parse
  * [X] Personalization flow will include 3 views questions with checkbox options (you 
  can check on or many)
    * [X] Engineering area of interest: Architecture, Security, Data Science/ 
    Analytics, Opensource, Other (will be a text box)
    * [X] What do you hope to get out of WWCode: enhance technical skills, 
    leadership skills, career development e.g. negotiation, interviewing…, 
    friends and professional network, other (will be text box)
    * [X] What skill-set level would you say you are currently: Advanced 
    technical 3+, Intermediate technical 1-3 years, Beginner technical under 
    1 year, other (will be text box)
* [X] Member will be taken to a view where they can click an option to create 
an account via github oauth or with email
  * [X] When a member clicks on sign up with email, render a view so a member can 
  sign up with email, persist on the server via parse
  * member model will include a fullname, geo location, image_url, password, job description
  * personalization_details: user_id, answers (hash with key (question): values (array of answers submitted)) --- check if this is 
  supported in parse. 
* After successful account creation, take user to profile preview: render user details, 
include image, password field (we may not need this), full name, job description field. Auto display whatever we have and leave what we don't have blank for the user to fill in. 
  * [X] Add functionality so the user can upload their image
  * [X] Add functionality so the user can take a photo of themselves

Timeline:
* [X] Timeline is the first listview page/fragment the member is taken to after
they confirm profile details, it will be the first tab in a 3 tab view.  
* [X] member will see a listview of posts 
 
Posts (directly relates to timeline):
* [X] Each item in the timeline view is a post that belongs to a feature or event. 
  * [X] Persist a post model, post will include user_id, subscription_id, 
  title, details/description, awesome_count
  * [X] We will pre-seed posts for now

Subscribing (tab in mock that says features):
* [ ] Member can tab to see the feature they are subscribed to and can join
* [X] feature will be modeled locally and pushed to parsed
    * [X] each feature will be a local data seed 
    (pending from Zassmin) 
      * [X] A feature has title/name, description, joined_count
      * [X] A member belongs to many features
* [X] feature fragment will include a listview with feature attributes
* [ ] member can choose which feature to join by clicking the 
feature and being taken to a detail view
  * [ ] In the detail view the member can subscribe or unsubscribe 
* [X] on click of a feature will take them to a details view of that feature
* detail view: 
  * [X] member can see the title, description, number of people joined
  * [ ] feature detail view will also render the post history timeline for the given 
  feature

Events: 
* [X] listview fragment for only event details
* [X] on click of the event will render a view with event details
* [X] event data will be from meetup api (w/o user oauth)
  * [X] Event model (based on meetup data): network_id, event_date, 
        event_date, location, url, title, featured (boolean), meetup_event_id, 
        rsvp_count, rsvp_limit 

The following **optional** features are implemented:
Onboarding:
 * [ ] When a member clicks sign up with github, take member through github 
  oauth sign up flow (https://developer.github.com/v3/oauth_authorizations/)
    * [ ] On successful log in we will push the Member data and personalization
    data to server via parse
    * [ ] get image url from user's github profile save it to image_url
* [ ] on successful signup member will be auto subscribed to WWCode specific features
* [ ] If the members signs in via github, we will make additional calls to the api
to better learn member's interests
  * [ ] language data (https://developer.github.com/v3/repos/#list-languages)
  * [ ] Persist language data locally on a Language model that has: user_id, 
  repo, language_bytes, language. One row per language for now. 
* [ ] add a character limit to the option stuff

Timeline:
* [X] Member will see posts in priority based on location and interest based data:
  * [ ] The timeline will be a listview and fragment
  * [ ] Initial timeline details for each member will include posts (we'll prepop this based on user's personalization info and location settings)
* [ ] On click of an listview post will take them to the 
details view of that feature.

Subscribing:
* [ ] The subscription view will now a include a group chat for anyone joined 
to that subscription
* [ ] Member can chat to that subscription directly from that view

Communication:
* [ ] By clicking a subscription or event from their own list view fragments, 
the member will be taken to a detail view with that subscription, with most 
recent chat history and the ability to chat. 
* [ ] Model the ability to chat:
  * [ ] A member can write many posts to the subscriptions they are a part of
* [ ] Member will see a list of all the places it can currently chat in, there is 
a chat group for each subscription they are in, member can chat in the group.  

The following **bonus** features are implemented:
Onboarding
* [ ] Member can type a secret access token to continue using the app
* [ ] Only if a member types in a secret access token, they can go to the log
in page
* [ ] If Member types an incorrect access token, they can try again, or request to
be on the wait list, by adding email and fullname 
  * We will also push their personalization data with name and email to parse

Timeline
* [ ] Add new posts based on relevent converstations! 

Subscribing
* [ ] Member can swipe the item on the listview to see subscribe or unsubscribe 
button depending on whether they are currently subscribed or not

Communication
* [ ] 1-1 direct chatting - (this feature needs wireframing)

Profile/Privacy Settings:
* [ ] Member will be able to see privacy, profile, and setting/preferences via
a navigation drawer layout which contains a framelayout with displays the 
contents of the fragment selected. 
* [ ] Drawers list items will include an item for each subscription category: 
  * [ ] **programming language preferences**, when a member clicks, a fragment 
  will be rended and it will include list of language options the member can use 
  from: ruby, java, android, rails, C, C#, html5, objective-c, python. The 
  member will able to click and hit save at the bottom to update their language 
  preferences. 
  * [ ] **events**, when a member clicks, a fragment will be rendered 
  displaying subscription by category
  * [ ] **applaud her**, this fragment will include a form submission to post
  an applaud her nomination to server
    * [ ] form will include applauding, story, email, twitter handle,
    accomplishment_blurb, and date_of_accomplishment 
  * ...this list will grow depending on the number of categories we end up with 
* [ ] Include a fragment that allow's a member to authenticate with oauth from
other apps
* [ ] Include an about WWCode fragment and make it accessible in the drawer

## Sprint 2 Demo

![demo_wwcodeapp_take_2](https://cloud.githubusercontent.com/assets/1654151/10802450/2e76131e-7d7a-11e5-874b-0f0e12257d64.gif)

## Sprint 1 Demo

![initial_demo_wwcodeapp](https://cloud.githubusercontent.com/assets/1654151/10651944/6a353ef2-7807-11e5-9df1-dd3fd254c2e6.gif)
