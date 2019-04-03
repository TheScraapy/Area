<template>
    <v-container v-if="$store.state.isUserLoggedIn">
        <div class="text-xs-left" v-if="step > 1">
            <v-btn color="primary" dark round @click="goBack">Go Back</v-btn>
        </div>

        <div class="text-xs-center" v-if="step < 5">
            <v-card-text class="card-title font-weight-black display-1">
    			Step {{ step }} : {{ steps[step - 1]}}
    		</v-card-text>
        </div>

        <v-layout row wrap v-if="step == 1 || step == 3">
            <v-flex xs12 sm6 md4 lg4 class="testclass my-3" v-for="service in services" :key="service.title">
                <v-card flat class="card card-service text-xs-center ma-3 container" :class="service.title">
                    <v-card-text class="card-title white--text font-weight-black display-1">
    					{{ service.title }}
    				</v-card-text>
                    <img v-if="service.title == 'Youtube'" src="../assets/youtube_logo.png" class="biglogo">
                    <img v-if="service.title == 'Twitter'" src="../assets/twitter_logo.png" class="biglogo">
                    <img v-if="service.title == 'Facebook'" src="../assets/facebook_logo.png" class="biglogo">
    				<img v-if="service.title == 'Clock'" src="../assets/clock_logo.png" class="biglogo">
    		        <img v-if="service.title == 'Gmail'" src="../assets/gmail_logo.png" class="biglogo">
                    <img v-if="service.title == 'Calendar'" src="../assets/calendar_logo.png" class="biglogo">
                    <div class="text-xs-center" v-if="$store.state.isUserLoggedIn">
                        <v-btn round v-on:click="selectService(service.id, service.title, service.logged)" v-if="service.logged == true">
    				        Select
    			        </v-btn>
      			        <fb-signin-button v-if="service.title == 'Facebook' && service.logged != true" :params="fbSignInParams" @success="onFBSignInSuccess" @error="onSignInError">Log in with Facebook</fb-signin-button>
            		    <v-btn v-if="service.title == 'Youtube' && service.logged != true" @click="youtubeAuth">Login with Google</v-btn>
            			<v-btn v-if="service.title == 'Gmail' && service.logged != true" @click="gmailAuth">Login with Google</v-btn>
            			<v-btn v-if="service.title == 'Twitter' && service.logged != true" @click="twitterAuth">Login with Twitter</v-btn>
                        <v-btn v-if="service.title == 'Calendar' && service.logged != true" @click="calendarAuth">Login with Google</v-btn>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>

        <v-layout row wrap v-if="step == 2">
            <v-flex xs12 sm6 md4 lg4 class="testclass my-3" v-for="action in actions" :key="action.title">
                <v-card flat class="card text-xs-center ma-3 container" :class="serviceInClass">
                    <v-card-text class="card-title white--text font-weight-black display-1">{{ action.title }}</v-card-text>
                    <v-card-text class="card-title white--text headline">{{ action.desc }}</v-card-text>
                    <div class="text-xs-center" v-if="$store.state.isUserLoggedIn">
                        <v-text-field solo v-model="actionData[action.id]" label="number between 0 and 23" v-if="action.isConfigurable == true && serviceIn == 3 && action.id == 0"></v-text-field>
                        <v-text-field solo v-model="actionData[action.id]" label="number between 0 and 59" v-if="action.isConfigurable == true && serviceIn == 3 && action.id == 1"></v-text-field>
    			        <br>
                        <v-btn round @click="selectAction(action.id, action.title)">Select trigger</v-btn>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>

        <v-layout row wrap v-if="step == 4">
            <v-flex xs12 sm6 md4 lg4 class="testclass my-3" v-for="reaction in reactions" :key="reaction.title">
                <v-card flat class="card text-xs-center ma-3 container" :class="serviceOutClass">
                    <v-card-text class="card-title white--text font-weight-black display-1">
                        {{ reaction.title }}
                    </v-card-text>
                    <v-card-text class="card-title white--text headline">{{ reaction.desc }}</v-card-text>
                    <div class="text-xs-center" v-if="$store.state.isUserLoggedIn">
                        <v-text-field solo v-model="reactionData[reaction.id]" label="address@mail.com" v-if="reaction.isConfigurable == true && serviceOut == 4 && reaction.id == 0"></v-text-field>
                        <v-text-field solo v-model="reactionData[reaction.id]" label="number between 0 and 23" v-if="reaction.isConfigurable == true && serviceOut == 5 && reaction.id == 0"></v-text-field>
    			        <br>
                        <v-btn round @click="selectReaction(reaction.id, reaction.title)">Select reaction</v-btn>
                    </div>
                </v-card>
            </v-flex>
        </v-layout>

        <div v-if="step == 5">
            <v-card flat class="card text-xs-center ma-3 container" :class="serviceInClass">
                <v-card-text class="card-title white--text font-weight-black display-1">
                    If {{ actiondesc }} then {{ reactiondesc }}
                </v-card-text>
                <div class="text-xs-center" v-if="$store.state.isUserLoggedIn">
                    <v-btn round @click="confirmWidget()">Confirm</v-btn>
                    <v-btn round @click="cancelWidget()">Cancel</v-btn>
                </div>
            </v-card>
        </div>
    </v-container>
</template>

<script>
import WidgetService from "@/services/WidgetService";
import AuthenticationService from "@/services/AuthenticationService";

export default {
    created: function() {
        window.fbAsyncInit = function() {
          FB.init({
            appId      : '359810634746275',
            xfbml      : true,
            version    : 'v3.2'
          });
          FB.getLoginStatus(function(response) {});
       };
        (function(d, s, id){
         var js, fjs = d.getElementsByTagName(s)[0];
         if (d.getElementById(id)) {return;}
         js = d.createElement(s); js.id = id;
         js.src = "//connect.facebook.net/en_US/sdk.js";
         fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    },
	data() {
	   return {
			fbSignInParams: {
                scope: 'email,user_likes',
                return_scopes: true
            },
			error: null,
            services: [],
            actions: [],
            reactions: [],
            serviceIn: 0,
            serviceInClass: '',
            serviceOut: 0,
            serviceOutClass: '',
            actionid: 0,
            actiondesc: '',
            reactionid: 0,
	        reactiondesc: '',
	    	actionData: [],
	    	reactionData: [],
            step: 1,
            steps: ['Choose action service', 'Choose action', 'Choose reaction service', 'Choose reaction']
	    }
    },
    async mounted() {
        if (this.$store.state.isUserLoggedIn) {
            const response = await WidgetService.getServices(this.$store.state.token);
            for (var service in response.data.services) {
                    if (this.step == 1) {
                        const areactions = await WidgetService.getActions(response.data.services[service].id, this.$store.state.token)
                        if (areactions.data.actions.length != 0) {
                            this.services.push(response.data.services[service])
                        }
                    }
            }
        } else {
            this.navigateTo({name: 'root'});
        }
    },
    methods: {
        navigateTo(route) {
            this.$router.push(route);
	    },
	    async refreshServices() {
            this.services = [];
	        if (this.$store.state.isUserLoggedIn) {
                const response = await WidgetService.getServices(this.$store.state.token);
                for (var service in response.data.services) {
                    if (this.step == 1) {
                        const areactions = await WidgetService.getActions(response.data.services[service].id, this.$store.state.token)
                        if (areactions.data.actions.length != 0) {
                            this.services.push(response.data.services[service])
                        }
                    }
                    if (this.step == 3) {
                        const areactions = await WidgetService.getReactions(response.data.services[service].id, this.$store.state.token)
                        if (areactions.data.reactions.length != 0) {
                            this.services.push(response.data.services[service])
                        }
                    }
                }
            }
	    },
        async selectService(serviceid, servicetitle, logged) {
            if (this.step == 1) {
                const response = await WidgetService.getActions(
                    serviceid, this.$store.state.token
                );
                this.serviceIn = serviceid;
                this.actions = response.data.actions;
                this.serviceInClass = servicetitle;
            } else if (this.step == 3) {
                const response = await WidgetService.getReactions(
                    serviceid, this.$store.state.token
                );
                this.serviceOut = serviceid;
                this.reactions = response.data.reactions;
                this.serviceOutClass = servicetitle;
            }
            this.step += 1;
        },
        selectAction(actionid, desc) {
            this.actionid = actionid;
            this.actiondesc = desc;
            this.step += 1;
            this.refreshServices();
        },
        selectReaction(reactionid, desc) {
            this.reactionid = reactionid;
            this.reactiondesc = desc;
            this.step += 1;
        },
        async confirmWidget() {
            var widgetcreator = {
                serviceIn: this.serviceIn,
                serviceOut: this.serviceOut,
    		    action: this.actionid,
    		    reaction: this.reactionid,
    		    actionData: this.actionData[this.actionid],
    		    reactionData: this.reactionData[this.reactionid],
    		    isConfigurable: this.actions[this.actionid].isConfigurable
            };
            const response = await WidgetService.createWidget(widgetcreator, this.$store.state.token);
            this.step = 1;
            this.$router.push({ name: "root" });
        },
        cancelWidget() {
            this.step = 1;
		},
		goBack() {
            this.step -= 1;
        },
	    async youtubeAuth() {
            gapi.auth.authorize({
                    client_id: "348333225838-43rcuddes9uftacun2drni5h3hpkbja9.apps.googleusercontent.com",
                    scope: ["https://www.googleapis.com/auth/youtube"]
                },
                this.youtubeAuthCallback
            );
        },
        async youtubeAuthCallback(auth) {
            try {
                const credentials = {
                    access_token: auth.access_token
                };
                await AuthenticationService.oauth(0, credentials, this.$store.state.token);
                this.refreshServices();
            } catch (error) {
                this.error = "Youtube login failed";
            }
        },
        async gmailAuth() {
            gapi.auth.authorize({
                    client_id: "348333225838-43rcuddes9uftacun2drni5h3hpkbja9.apps.googleusercontent.com",
                    scope: ["https://www.googleapis.com/auth/gmail.send"]
                },
                this.gmailAuthCallback
            );
        },
		async twitterAuth() {
            try {
                const credentials = {
                    access_token: this.$store.state.token
                };
                const { data } = await AuthenticationService.twitterRequestTokens(credentials, this.$store.state.token);
                window.open('https://api.twitter.com/oauth/authenticate?oauth_token=' + data.oauth_token);
                this.refreshServices();
            } catch (error) {
                this.error = "Twitter login failed"
            }
        },
        async gmailAuthCallback(auth) {
            try {
                const credentials = {
                    access_token: auth.access_token
                };
                await AuthenticationService.oauth(4, credentials, this.$store.state.token);
                this.refreshServices();
            } catch (error) {
                this.error = "Gmail login failed";
            }
        },
        async calendarAuth() {
            gapi.auth.authorize({
                    client_id: "348333225838-43rcuddes9uftacun2drni5h3hpkbja9.apps.googleusercontent.com",
                    scope: ["https://www.googleapis.com/auth/calendar.events"]
                },
                this.calendarAuthCallback
            );
        },
        async calendarAuthCallback(auth) {
            try {
                const credentials = {
                    access_token: auth.access_token
                };
                console.log(auth.access_token);
                await AuthenticationService.oauth(5, credentials, this.$store.state.token);
                this.refreshServices();
                console.log(this.services)
            } catch (error) {
                this.error = 'Calendar login failed';
            }
        },
	    async onFBSignInSuccess (response) {
            try {
                const credentials = {
                    access_token: FB.getAuthResponse()["accessToken"]
                };
                await AuthenticationService.oauth(1, credentials, this.$store.state.token);
                this.refreshServices();
            } catch (error) {
                this.error = "Facebook login failed"
            }
        }
    }
};
</script>

<style scoped>
.testclass {
    padding: 10px !important;
}
.card {
    border-radius: 0.5em;
}
.card-service {
    height: 275px; 
}
.card-title {
    padding-top: 16px;
    padding-left: 35px;
    padding-right: 35px;
    padding-bottom: 16px;
}
#container {
    position: relative;
}
.container {
    margin: 10px !important;
}

.biglogo {
    height: 100px;
    width: auto;
}
.Youtube {
    background-color: red !important;
}
.Google {
    background-color: rgb(45, 45, 45) !important;
}
.Clock {
    background-color: rgb(164, 198, 57) !important;
}
.Twitter {
    background-color: rgb(56, 161, 243) !important;
}
.Facebook {
    background-color: rgb(59, 89, 152) !important;
}
.Gmail {
    background-color: rgb(35, 68, 139) !important;
}
.Calendar {
    background-color: rgb(223, 120, 35) !important;
}

.fb-signin-button {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 3px;
    background-color: #4267b2;
    color: #000;
	cursor: pointer;
    width: 200px;
    height: 36px;
    margin: 6px 8px;
    background-color: #f5f5f5;
    text-transform: uppercase;
    vertical-align: middle;
    text-align: center;
    padding: 8px;
}
</style>
