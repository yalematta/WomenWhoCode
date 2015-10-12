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
* [ ] Member will go through a personalization flow (pending assets and 
text details from WWCode HQ)
* [ ] Member will be taken to a view where they can click an option to create 
an account via github oauth or with email
  * [ ] This view will also ask a user if we can access their location settings
  * [ ] When a member clicks on sign up with email, render a view so a member can 
  sign up with email, persist locally and on the server via parse
  * [ ] When a member clicks sign up with github, take member through github 
  oauth sign up flow (https://developer.github.com/v3/oauth_authorizations/)
    * [ ] On successful log in we will push the Member data and personalization
    data to server via parse
  * member model will include a fullname, geo location, image_url, 

Timeline:
* [ ] Timeline is the first page the member is taken to after log in
* [ ] Member will see location and interest based data:
  * [ ] The timeline will be a multitype list and fragment
  * [ ] Initial timeline details for each member will include:
  	* [ ] an inspiration quote
  	* [ ] notification style alerts about which subscriptions they are 
  	subscribed to, initial subscriptions include network events closest to their 
  	location, the applaud her, the code review, the blog
  		* [ ] subscriptions will be modeled locally and pushed to parsed
  		* [ ] each subscription aside from events will be a local data seed 
      (pending from Zassmin) 
  		* [ ] event data will be from meetup api (w/o user oauth)
* [ ] Member can tab to see the timeline which is a mutlitype listview fragment 

Subscribing:
* [ ] Member can tab to see the subscription
* [ ] Subscription model includes favoriated (boolean), name, description, tag/category
* [ ] Category/tag model includes name (programming language, events, conferences, blog, job post)
* [ ] Subscription fragment will include a listview with subscription #tag and 
short details
  * [ ] each subscription will show a category on the right hand side
* [ ] member can choose which subscription to join by clicking the 
subscription and being taken to a detail view
  * [ ] In the detail view the member can subscribe or unsubscribe 
  * [ ] subscriptions alerts such as, 'you just subscribed to #java, learn more 
  here' will be rendered to the timeline fragment 
  * [ ] member can unscubscribe to subscriptions they joined

Communication:
* [ ] Member will see option to chat in the timeline view (the view this should 
be displayed in can very much be up to the design)
* [ ] Member will see a list of all the places it can currently chat in, there is 
a chat group for each subscription they are in, member can chat in the group.  

The following **optional** features are implemented:
Onboarding:
* [ ] If the members signs in via github, we will make additional calls to the api
to better learn member's interests
  * [ ] language data (https://developer.github.com/v3/repos/#list-languages)
  * [ ] Persist language data locally on a Language model that has: user_id, 
  repo, language_bytes, language. One row per language for now. 

Timeline:
* [ ] When a new subscription has a chat, timeline will show a small preview
notifcation of who's chatting and what it's about (it should be the first 50char
of the description)

Subscribing:
* [ ] Create a model for Subscription: category_id, name, description, 
followers_count
* [ ] If a member is subscribed to the subscription, member can see subscription 
chat history, by clicking the specific subscription
* [ ] Member can chat to that subscription directly from that view
* [ ] Each subscrpition will include at least one category

Communication: 
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
* [ ] 

Profile/Privacy Settings
* [ ] dynamically render drawer list based on the category names we have
* [ ] allow a member to authenticate with oauth
* [ ] Include an about WWCode fragment and make it accessible in the drawer

## Video Walkthrough

...coming soon...
