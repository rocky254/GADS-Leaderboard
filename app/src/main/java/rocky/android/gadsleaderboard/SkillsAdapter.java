package rocky.android.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {
    ArrayList<Skill> mSkills;

    public SkillsAdapter(ArrayList<Skill> skills) {
        this.mSkills = skills;
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_skills, parent, false);
        return new SkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        Skill skill = mSkills.get(position);
        holder.bind(skill);
    }

    @Override
    public int getItemCount() {
        return mSkills.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvSkill;

        public SkillViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.text_learning_name);
            tvSkill = itemView.findViewById(R.id.text_skill_details);
        }

        public void bind (Skill skill) {
            tvName.setText(skill.name);
            String details = skill.score + " skill IQ Score, " + skill.country;
            tvSkill.setText(details);
        }
    }
}
