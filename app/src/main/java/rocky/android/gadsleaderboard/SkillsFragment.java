package rocky.android.gadsleaderboard;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
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


public class SkillsFragment extends Fragment {
    private ProgressBar mLoadingProgress;
    private RecyclerView mSkillsRecycler;
    private TextView mTvError;

    public SkillsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_skills, container, false);
        mLoadingProgress = v.findViewById(R.id.pb_loading);
        mTvError = v.findViewById(R.id.tv_error);
        mSkillsRecycler = v.findViewById(R.id.list_skills);
        mSkillsRecycler.setHasFixedSize(true);
        mSkillsRecycler.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));

        URL skillsUrl;

        try{
            skillsUrl = ApiUtil.buildURL("skilliq");
            new SkillsQueryTask().execute(skillsUrl);
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        return v;
    }

    public class SkillsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL skillsURL = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJSON(skillsURL);
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
                mSkillsRecycler.setVisibility(View.INVISIBLE);
                mTvError.setVisibility(View.VISIBLE);
            }
            else {
                mSkillsRecycler.setVisibility(View.VISIBLE);
                mTvError.setVisibility(View.INVISIBLE);

                ArrayList<Skill> skills = ApiUtil.getSkillsFromJson(result);
                SkillsAdapter adapter = new SkillsAdapter(skills);
                mSkillsRecycler.setAdapter(adapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
