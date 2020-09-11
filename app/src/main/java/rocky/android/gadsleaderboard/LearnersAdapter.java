package rocky.android.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LearnersAdapter extends RecyclerView.Adapter<LearnersAdapter.LearnerViewHolder> {
    ArrayList<Learner> mLearners;
    public LearnersAdapter(ArrayList<Learner> learners) {
        this.mLearners = learners;
    }

    @NonNull
    @Override
    public LearnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_learning, parent, false);
        return new LearnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnerViewHolder holder, int position) {
        Learner learner = mLearners.get(position);
        holder.bind(learner);
    }

    @Override
    public int getItemCount() {
        return mLearners.size();
    }

    public class LearnerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDetails;

        public LearnerViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.text_learning_name);
            tvDetails = itemView.findViewById(R.id.text_learning_details);
        }

        public void bind (Learner learner) {
            tvName.setText(learner.name);
            String details = learner.hours + " learning hours, " + learner.country;
            tvDetails.setText(details);
        }
    }
}
