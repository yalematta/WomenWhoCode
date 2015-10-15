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
* [ ] On app install we will ask user if we can access their location settings
* [ ] Member will go through a personalization flow that is persisted via parse
  * [ ] Personalization flow will include 3 questions with checkbox options (you 
  can check on or many)
    * [ ] Engineering area of interest: Architecture, Security, Data Science/ 
    Analytics, Opensource, Other (will be a text box)
    * [ ] What do you hope to get out of WWCode: enhance technical skills, 
    leadership skills, career development e.g. negotiation, interviewing…, 
    friends and professional network, other (will be text box)
    * [ ] What skill-set level would you say you are currently: Advanced 
    technical 3+, Intermediate technical 1-3 years, Beginner technical under 
    1 year, other (will be text box)
* [ ] Member will be taken to a view where they can click an option to create 
an account via github oauth or with email
  * [ ] When a member clicks on sign up with email, render a view so a member can 
  sign up with email, persist locally and on the server via parse
  * [ ] When a member clicks sign up with github, take member through github 
  oauth sign up flow (https://developer.github.com/v3/oauth_authorizations/)
    * [ ] On successful log in we will push the Member data and personalization
    data to server via parse
  * member model will include a fullname, geo location, image_url, password

Timeline:
* [ ] Timeline is the first listview page/fragment the member is taken to after
log in, it will be the first tab in a 3 tab view.   
* [ ] Member will see location and interest based data:
  * [ ] The timeline will be a multitype list and fragment
  * [ ] Initial timeline details for each member will include:
  	* [ ] an inspiration quote - will be a post from the "Get Inspired" subscription
  	* [ ] notification style alerts about which subscriptions they are 
  	subscribed to, initial subscriptions include network events closest to their 
  	location, the applaud her, the code review, the blog, get inspired
* [ ] On click of an listview item will take them to the details view of that post/item. 

Subscribing:
* [ ] Member can tab to see the subscription
* [ ] Subscription will be modeled locally and pushed to parsed
    * [ ] each subscription will be a local data seed 
    (pending from Zassmin) 
      * [ ] A Subscription has title/name, description, joined_count
      * [ ] A member belongs to many subscriptions
* [ ] Subscription fragment will include a listview with subscription title and 
short details
* [ ] member can choose which subscription to join by clicking the 
subscription and being taken to a detail view
  * [ ] In the detail view the member can subscribe or unsubscribe 
  * [ ] subscriptions alerts such as, 'you just subscribed to #java, learn more 
  here' will be rendered to the timeline fragment 
* on click of a subscription will take them to a details view of that subscription
* detail view: 
  * member can see the title, description, number of people joined, post history 
  and choose to subscribe
  * Where are we doing the post model?

Events: 
* [ ] listview fragment for only event details
* [ ] on click of the event will render a view with event details
* [ ] event data will be from meetup api (w/o user oauth)
  * [ ] Event model (based on meetup data): network_id, event_date, 
        event_date, location, url, title, featured (boolean), meetup_event_id, 
        rsvp_count, rsvp_limit 

The following **optional** features are implemented:
Onboarding:
* [ ] on successful signup member will be auto subscribed to WWCode specific features
* [ ] If the members signs in via github, we will make additional calls to the api
to better learn member's interests
  * [ ] language data (https://developer.github.com/v3/repos/#list-languages)
  * [ ] Persist language data locally on a Language model that has: user_id, 
  repo, language_bytes, language. One row per language for now. 

Timeline:
* [ ] On click of an listview item's description post will take them to the 
details view of that item. 
* [ ] On click of the chat icon will take the user to the subsciption's detail 
view.  

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
* [ ] 

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

## Video Walkthrough

...coming soon...
