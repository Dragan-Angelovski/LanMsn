package mk.ukim.finki.mpip.lanmsn.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mk.ukim.finki.mpip.lanmsn.R;

public class ChatMessageAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> messages = null;


    public ChatMessageAdapter(Context context, int textViewResourceId,
                              List<String> items) {
        super(context, textViewResourceId, items);
        this.context=context;
        this.messages=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(android.R.layout.simple_list_item_1, null);
        }
        String message = messages.get(position);
        if (message != null && !message.isEmpty()) {
            TextView nameText = (TextView) v
                    .findViewById(android.R.id.text1);

            if (nameText != null) {
                nameText.setText(message);
                if (message.startsWith("Me: ")) {
                    nameText.setTextAppearance(context.getApplicationContext(),
                            R.style.normalText);
                } else {
                    nameText.setTextAppearance(context.getApplicationContext(),
                            R.style.boldText);
                }
            }
        }
        return v;
    }
}
