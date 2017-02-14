package com.task.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.task.R;
import com.task.activities.HomeActivity;
import com.task.adapters.ConversationViewAdapter;
import com.task.models.Message;
import com.task.models.MessageList;
import com.task.utils.MessageLink;
import com.task.utils.TimeUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.task.activities.HomeActivity.messageList;

public class ConversationsFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private ConversationViewAdapter conversationViewAdapter;

    public ConversationsFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.fragment_messageview_list, container, false);


            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            Button refreshButton = (Button) root.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.fetchAllMessages();
            }
        });
            conversationViewAdapter = new ConversationViewAdapter(mListener);
            recyclerView.setAdapter(conversationViewAdapter);
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void notifyDataChanged() {
        conversationViewAdapter.notifyDataSetChanged();
    }

    public interface OnListFragmentInteractionListener {
        public void fetchAllMessages();

        void notifyDataChanged();
    }
}
