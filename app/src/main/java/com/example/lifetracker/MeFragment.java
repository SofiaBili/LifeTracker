package com.example.lifetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {

    ApplicationViewModel applicationViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String username;
    int countTotalItems, countCheckedItems = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_me, container, false);
        applicationViewModel = new ViewModelProvider(this.requireActivity()).get(ApplicationViewModel.class);
        
        SharedPreferences sharedPref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultText = getResources().getString(R.string.default_username_text);
        String username = sharedPref.getString(getString(R.string.saved_username_text_key), defaultText);
        TextView textView = inflatedView.findViewById(R.id.userNameTextView);
        TextView finishedTasksTextView = inflatedView.findViewById(R.id.finishedTasksTextView);
        TextView pendingTasksTextView = inflatedView.findViewById(R.id.pendingTasksTextView);
        textView.setText("Hello, " + username);

        CircularImageView imageView = inflatedView.findViewById(R.id.imageView);
        if (sharedPref.contains("imagepathURI")) {
            String mFilePath = sharedPref.getString("imagepathURI",null);
            Log.d("iiiiiiiiijjjjjjj", mFilePath);
            Picasso.with(getContext()).load(Uri.parse(mFilePath)).placeholder(R.drawable.ic_baseline_autorenew_24).error(R.drawable.cat_glasses).fit().centerInside().into(imageView);
        }
        //count all to do's and how many are selected
        applicationViewModel.getToDoItemList().observe(getViewLifecycleOwner(), toDoItems -> {
            countCheckedItems =0;
            countTotalItems = toDoItems.size();
            for(int i =0;i<countTotalItems;i++) {
                if (toDoItems.get(i).isSelected()) {
                    countCheckedItems++;
                }
            }
            Log.d("iiiii", String.valueOf(countTotalItems));
            Log.d("iiiii", String.valueOf(countCheckedItems));
            finishedTasksTextView.setText(MessageFormat.format("{0}\nFinished Tasks", countCheckedItems));
            pendingTasksTextView.setText(MessageFormat.format("{0}\nPending Tasks", (countTotalItems - countCheckedItems)));
        });
        // Inflate the layout for this fragment
        return inflatedView;
    }
}
