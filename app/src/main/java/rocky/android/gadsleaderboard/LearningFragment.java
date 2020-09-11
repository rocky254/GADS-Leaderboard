package rocky.android.gadsleaderboard;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class LearningFragment extends Fragment {
    private ProgressBar mLoadingProgress;
    private RecyclerView mLearningRecycler;
    private TextView mTvError;

    public LearningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learning, container, false);
        mLoadingProgress = v.findViewById(R.id.pb_loading);
        mTvError = v.findViewById(R.id.tv_error);
        mLearningRecycler = v.findViewById(R.id.list_learning);
        mLearningRecycler.setHasFixedSize(true);
        mLearningRecycler.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));

        URL learnerUrl;

        try{
            learnerUrl = ApiUtil.buildURL("hours");
            new LearnersQueryTask().execute(learnerUrl);
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        // Inflate the layout for this fragment
        return v;
    }

    public class LearnersQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL learnersURL = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJSON(learnersURL);
            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                mLearningRecycler.setVisibility(View.INVISIBLE);
                mTvError.setVisibility(View.VISIBLE);
            }
            else {
                mLearningRecycler.setVisibility(View.VISIBLE);
                mTvError.setVisibility(View.INVISIBLE);

                ArrayList<Learner> learners = ApiUtil.getLearnersFromJson(result);
                LearnersAdapter adapter = new LearnersAdapter(learners);
                mLearningRecycler.setAdapter(adapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
