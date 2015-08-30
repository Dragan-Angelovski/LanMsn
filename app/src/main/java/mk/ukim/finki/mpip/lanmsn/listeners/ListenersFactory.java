package mk.ukim.finki.mpip.lanmsn.listeners;

import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Viktor on 8/30/2015.
 */
public class ListenersFactory {

    private DiscoverPeersListener discoverPeersListener;

    public ListenersFactory(){

    }

    public WifiP2pManager.ActionListener getDiscoverPeersListener(){

        if(discoverPeersListener==null){
            discoverPeersListener = new DiscoverPeersListener();
        }

        return discoverPeersListener;
    }

}
