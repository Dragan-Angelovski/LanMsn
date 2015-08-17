package mk.ukim.finki.mpip.lanmsn.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Viktor on 8/18/2015.
 */
public class NetworkChangeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int networkStatus = NetworkUtil.getConnectivityStatus(context);

        if(networkStatus==NetworkUtil.TYPE_NOT_CONNECTED){

            Toast.makeText(context,"Network unavailable",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Network available",Toast.LENGTH_LONG).show();
        }


    }
}
