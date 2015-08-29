package mk.ukim.finki.mpip.lanmsn.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.lanmsn.R;
import mk.ukim.finki.mpip.lanmsn.network.WiFiDirectBroadcastReceiver;

public class DevicesListActivity extends AppCompatActivity implements WifiP2pManager.ActionListener,WifiP2pManager.PeerListListener,
    WifiP2pManager.ConnectionInfoListener{

    private TextView mMyDeviceName;
    private final IntentFilter intentFilter = new IntentFilter();

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel mChannel;

    private BroadcastReceiver mReceiver;

    private List<WifiP2pDevice> peers = new ArrayList();
    private ListView peersList;
    private WiFiPeerListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        mMyDeviceName = (TextView) findViewById(R.id.txtMyDeviceName);

        Intent intent = getIntent();

        String username = intent.getExtras().getString("username");
        mMyDeviceName.setText(username);
        peersList = (ListView) findViewById(R.id.peersList);
        mListAdapter = new WiFiPeerListAdapter(this, R.layout.row_devices, peers);
        peersList.setAdapter(mListAdapter);


        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = wifiP2pManager.initialize(this, getMainLooper(), null);
        wifiP2pManager.discoverPeers(mChannel,this);



    }

    public WiFiPeerListAdapter getmListAdapter(){

        return mListAdapter;
    }


    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new WiFiDirectBroadcastReceiver(wifiP2pManager, mChannel, this);
        registerReceiver(mReceiver, intentFilter);


    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onSuccess() {
        // Code for when the discovery initiation is successful goes here.
        // No services have actually been discovered yet, so this method
        // can often be left blank.  Code for peer discovery goes in the
        // onReceive method, detailed below.
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(int reasonCode) {
        // Code for when the discovery initiation fails goes here.
        // Alert the user that something went wrong.
        Toast.makeText(this,"wrond",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {


        // Out with the old, in with the new.
        peers.clear();
        peers.addAll(peerList.getDeviceList());

        // If an AdapterView is backed by this data, notify it
        // of the change.  For instance, if you have a ListView of available
        // peers, trigger an update.
        mListAdapter.notifyDataSetChanged();
        if (peers.size() == 0) {
            //Log.d(WiFiDirectActivity.TAG, "No devices found");
            mListAdapter.notifyDataSetChanged();
            return;
        }

    }


    public void connect(int position) {
        // Picking the first device found on the network.
        WifiP2pDevice device = peers.get(position);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        wifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(DevicesListActivity.this, "Connect failed. Retry.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {


        // InetAddress from WifiP2pInfo struct.
        InetAddress groupOwnerAddress = info.groupOwnerAddress;

        // After the group negotiation, we can determine the group owner.
        if (info.groupFormed && info.isGroupOwner) {
            // Do whatever tasks are specific to the group owner.
            // One common case is creating a server thread and accepting
            // incoming connections.
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case,
            // you'll want to create a client thread that connects to the group
            // owner.
        }
    }

    private static String getDeviceStatus(int deviceStatus) {
        //Log.d(WiFiDirectActivity.TAG, "Peer status :" + deviceStatus);
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";

        }
    }

    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

        private List<WifiP2pDevice> items;

        /**
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public WiFiPeerListAdapter(Context context, int textViewResourceId,
                                   List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            items = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_devices, null);
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
                TextView top = (TextView) v.findViewById(R.id.device_name);
                TextView bottom = (TextView) v.findViewById(R.id.device_details);
                if (top != null) {
                    top.setText(device.deviceName);
                }
                if (bottom != null) {
                    bottom.setText(getDeviceStatus(device.status));
                }
            }

            return v;

        }
    }


}
