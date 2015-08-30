package mk.ukim.finki.mpip.lanmsn.listeners;

import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Viktor on 8/30/2015.
 */
public class DiscoverPeersListener implements WifiP2pManager.ActionListener {
    @Override
    public void onSuccess() {

        //code for the peer discovery goes in the onrecieve method
    }

    @Override
    public void onFailure(int reason) {

        //Todo alert the user that something went wrong
    }
}
