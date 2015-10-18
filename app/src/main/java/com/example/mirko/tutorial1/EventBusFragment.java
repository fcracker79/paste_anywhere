package com.example.mirko.tutorial1;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;


public class EventBusFragment extends Fragment {
    private static final String ARG_ELEMENTS_LIST = "elementsList";

    private String[] elementsList;
    private int counter = 0;

    private ToastEventListener eventListener;
    private TextView counterView;

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

        if (getArguments() != null) {
            this.elementsList = getArguments().getStringArray(ARG_ELEMENTS_LIST);
            this.counter = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.fragment_event_bus, container, false);

        this.counterView = (TextView) result.findViewById(R.id.messageBusCounter);

        this.eventListener = new ToastEventListener(this, getActivity());
        Log.i("EVENT_BUS", String.format("Event listener %s created", this.eventListener));
        if (EventBus.getDefault().hasSubscriberForEvent(RealWorldEvent.class)) {
            throw new IllegalStateException();
        }

        final Button sendEventButton = (Button) result.findViewById(R.id.sendMessageButton);
        sendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new RealWorldEvent(elementsList[counter]));
                counter = (counter + 1) % elementsList.length;
                Log.i("EVENT BUS", String.format("Messages counter: %s", counter));
            }
        });

        return result;
    }

    @Override
    public void onDestroy() {
        Log.i("EVENT BUS", "On view destroyed event bus fragment");
        if (this.eventListener != null) {
            EventBus.getDefault().unregister(this.eventListener);
            this.eventListener = null;
        }
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("EVENT BUS", "On view created event bus fragment");
        if (this.eventListener == null) {
            throw new IllegalStateException();
        }
        EventBus.getDefault().register(this.eventListener);
        super.onViewCreated(view, savedInstanceState);
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

    private void updateCounter() {
        this.counterView.setText(String.valueOf(this.counter));
    }
    private static class ToastEventListener {
        private final Context context;
        private final EventBusFragment f;
        public ToastEventListener(EventBusFragment f, Context context) {
            this.context = context;
            this.f = f;
        }

        @SuppressWarnings("unused")
        public void onEventMainThread(RealWorldEvent event) {
            Toast.makeText(context, event.getWord(), Toast.LENGTH_SHORT).show();
            f.updateCounter();
        }
    }


}
