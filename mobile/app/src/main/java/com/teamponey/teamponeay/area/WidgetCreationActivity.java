package com.teamponey.teamponeay.area;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamponey.teamponeay.area.Fragments.ConfigureFrag;
import com.teamponey.teamponeay.area.Fragments.Oauth.ClockFrag;
import com.teamponey.teamponeay.area.Fragments.Oauth.FacebookFrag;
import com.teamponey.teamponeay.area.Fragments.Oauth.GmailFrag;
import com.teamponey.teamponeay.area.Fragments.Oauth.TwitterFrag;
import com.teamponey.teamponeay.area.Fragments.ReactionFrag;
import com.teamponey.teamponeay.area.Fragments.ServiceFrag;
import com.teamponey.teamponeay.area.Fragments.ActionFrag;
import com.teamponey.teamponeay.area.Models.ActionReaction.CreationWidget;

import static com.teamponey.teamponeay.area.Constants.RC_SIGN_IN;

public class WidgetCreationActivity extends AppCompatActivity {


    private Fragment fragmentService;
    private Fragment fragmentAction;
    private Fragment fragmentReaction;
    private Fragment fragmentConfigure;

    private Fragment fragmentFacebook;
    private Fragment fragmentTwitter;
    private Fragment fragmentClock;
    private Fragment fragmentGmail;

    public static final int FRAGMENT_SERVICE = 0;
    public static final int FRAGMENT_ACTION = 1;
    public static final int FRAGMENT_REACTION = 2;
    public static final int FRAGMENT_CONFIGURE = 3;

    public static final int FRAGMENT_FACEBOOK = 100;
    public static final int FRAGMENT_TWITTER = 101;
    public static final int FRAGMENT_YOUTUBE = 102;
    public static final int FRAGMENT_CLOCK = 103;
    public static final int FRAGMENT_GMAIL = 104;
    public static final int FRAGMENT_CALENDAR = 105;

    private static WidgetCreationActivity instance;

    public static CreationWidget newWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_creation);

        newWidget = new CreationWidget();

        newWidget.setActionId(-1);
        newWidget.setReactionId(-1);

        newWidget.setServiceIn(-1);
        newWidget.setServiceOut(-1);

        instance = this;

        this.showFragment(FRAGMENT_SERVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
            fragmentGmail.onActivityResult(requestCode, resultCode, data);
        else
            fragmentTwitter.onActivityResult(requestCode, resultCode, data);
    }

    public void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_SERVICE:
                this.showServiceFragment();
                break;
            case FRAGMENT_ACTION:
                this.showActionFragment();
                break;
            case FRAGMENT_REACTION:
                this.showReactionFragment();
                break;
            case FRAGMENT_CONFIGURE:
                this.showConfigureFragment();
                break;
            case FRAGMENT_FACEBOOK:
                this.showFacebookFragment();
                break;
            case FRAGMENT_TWITTER:
                this.showTwitterFragment();
                break;
            case FRAGMENT_YOUTUBE:
                this.showGmailFragment();
                break;
            case FRAGMENT_CLOCK:
                this.showClockFragment();
                break;
            case FRAGMENT_GMAIL:
                this.showGmailFragment();
                break;
            case FRAGMENT_CALENDAR:
                this.showGmailFragment();
                break;
            default:
                break;
        }
    }

    private void showServiceFragment(){
        if (this.fragmentService == null) this.fragmentService = ServiceFrag.newInstance();
        this.startTransactionFragment(this.fragmentService);
    }

    private void showActionFragment(){
        if (this.fragmentAction == null) this.fragmentAction = ActionFrag.newInstance();
        this.startTransactionFragment(this.fragmentAction);
    }

    private void showReactionFragment(){
        if (this.fragmentReaction == null) this.fragmentReaction = ReactionFrag.newInstance();
        this.startTransactionFragment(this.fragmentReaction);
    }

    private void showConfigureFragment(){
        if (this.fragmentConfigure == null) this.fragmentConfigure = ConfigureFrag.newInstance();
        this.startTransactionFragment(this.fragmentConfigure);
    }

    private void showFacebookFragment(){
        if (this.fragmentFacebook == null) this.fragmentFacebook = FacebookFrag.newInstance();
        this.startTransactionFragment(this.fragmentFacebook);
    }

    private void showTwitterFragment(){
        if (this.fragmentTwitter == null) this.fragmentTwitter = TwitterFrag.newInstance();
        this.startTransactionFragment(this.fragmentTwitter);
    }

    private void showClockFragment(){
        if (this.fragmentClock == null) this.fragmentClock = ClockFrag.newInstance();
        this.startTransactionFragment(this.fragmentClock);
    }

    private void showGmailFragment(){
        if (this.fragmentGmail == null) this.fragmentGmail = GmailFrag.newInstance();
        this.startTransactionFragment(this.fragmentGmail);
    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_widget_frame_layout, fragment).commit();
        }
    }

    public static WidgetCreationActivity getInstance() {
        return instance;
    }
}