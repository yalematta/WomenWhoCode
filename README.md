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

Onboarding
* [ ] Member will go through a personalization flow (pending assets and 
text details from WWCode HQ)
* [ ] Member can type a secret access token to continue using the app
* [ ] If Member successfully types a secret access token, member will be taken to a 
view where they can click an option to create an account via github oauth or with email
  * [ ] This view will also ask a user if we can access their location settings
  * [ ] Sign up with email will use parse as server
  * [ ] On successful log in we will push the Member data and personalization data
   to server via parse

Timeline
* [ ] Member can tab to see the timeline
* [ ] Member will see location and interest based data:
  * [ ] the list will be a frament and multi-type lsit
  * [ ] Member initial timeline details will include:
  	* [ ] an inspiration quote
  	* [ ] notification style alerts about which subscriptions they are 
  	subscribed to, initial subscriptions include network events closest to their 
  	location, the applaud her, the code review, the blog
  		* [ ] subscriptions will be modeled locally and pushed to parsed
  		* [ ] each subscription aside from events will be a local data seed (pending from Zassmin) 
  		* [ ] event data will be from meetup api (w/o user oauth)

Subscribing
* [ ] Member can tab to see the subscription
* [ ] Subscription fragement will include a listview with subscription #tag and 
short details
* [ ] member can choose which subscription to be join by clicking the 
subscription and being taken to a detail view
  * [ ] In the detail view the member can subscribe or unsubscribe 
  * [ ] subscriptions alerts such as, 'you just subscribed to #java, learn more 
  here' will be rendered to the timeline fragment 
  * [ ] member can unscubscribe to subscriptions they joined

Communication:
* [ ] Member will see option to chat in the timeline view
* [ ] Member can chat from the timeline view to a 'subscription' 
* [ ] Chat data will be persisted to parse

The following **optional** features are implemented:
Onboarding
* [ ] If Member types an incorrect access token, they can try again, or request to
be on the wait list, by adding email and fullname 
  * We will also push their personalization data with name and email to parse
* [ ] If the members signs in via github, we will make additional calls to the api
to better learn member's interests
  * [ ] language data
  * [ ] project interest

Timeline
* [ ] 

Subscribing
* [ ] Member can swipe the item on the listview to see subscribe or unsubscribe 
button depending on whether they are currently subscribed or not
* [ ] If a member is subscribed to the subscription, member can subscription 
chat history, by clicking the subscription
* [ ] Member can chat to that subscription directly from that view

Communication: 
* [ ] 1-1 direct chatting

Profile/Privacy Settings:
* [ ] Member will be able to see privacy, profile, and setting fragments from 
drawer view
* [ ] Each fragment will have a list of check box where member can choose chat restrictions 

The following **bonus** features are implemented:
Onboarding
* [ ]

Timeline
* [ ] 

Subscribing
* [ ] 

Communication
* [ ] 

Profile/Privacy Settings
* [ ] allow a member to authenticate with oauth
* [ ] Include an about WWCode fragment and make it accessible in the drawer

## Video Walkthrough

...coming soon...
