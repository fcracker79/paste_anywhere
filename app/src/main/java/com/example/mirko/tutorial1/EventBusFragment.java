package com.example.mirko.tutorial1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import de.greenrobot.event.EventBus;


public class EventBusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ELEMENTS_LIST = "elementsList";

    private String[] elementsList;
    private int counter = 0;

    private ToastEventListener eventListener;

    public static EventBusFragment newInstance(String ... elementsList) {
        EventBusFragment fragment = new EventBusFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_ELEMENTS_LIST, elementsList);
        fragment.setArguments(args);
        return fragment;
    }

    public EventBusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        this.eventListener = new ToastEventListener(getActivity());

        if (getArguments() != null) {
            this.elementsList = getArguments().getStringArray(ARG_ELEMENTS_LIST);
            this.counter = 0;
        }

        if (!EventBus.getDefault().hasSubscriberForEvent(RealWorldEvent.class)) {
            EventBus.getDefault().register(this.eventListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.fragment_event_bus, container, false);

        final Button sendEventButton = (Button) result.findViewById(R.id.sendMessageButton);
        sendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new RealWorldEvent(elementsList[counter]));
                counter = (counter + 1) % elementsList.length;
            }
        });

        return result;
    }

    private static class RealWorldEvent {
        private final String word;

        public RealWorldEvent(String word) {
            this.word = word;
        }

        public String getWord() {
            return word;
        }
    }

    private static class ToastEventListener {
        private final Context context;

        public ToastEventListener(Context context) {
            this.context = context;
        }

        public void onEventMainThread(RealWorldEvent event) {
            Toast.makeText(context, event.getWord(), Toast.LENGTH_SHORT).show();
        }
    }
}
