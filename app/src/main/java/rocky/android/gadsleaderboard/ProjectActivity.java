package rocky.android.gadsleaderboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rocky.android.gadsleaderboard.data.remote.APIService;
import rocky.android.gadsleaderboard.data.remote.APIUtils;

public class ProjectActivity extends AppCompatActivity {
    Toolbar mToolbar;
    private static final String TAG = "PROJECT";
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        mToolbar = findViewById(R.id.submission_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText mFirst = findViewById(R.id.first_name);
        final EditText mLast = findViewById(R.id.last_name);
        final EditText mEmail = findViewById(R.id.email_address);
        final EditText mLink = findViewById(R.id.project_link);
        Button mSubmit = findViewById(R.id.submit_project);

        mAPIService = APIUtils.getAPIService();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = mFirst.getText().toString().trim();
                String last = mLast.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String link = mLink.getText().toString().trim();

                if(!TextUtils.isEmpty(first) && !TextUtils.isEmpty(last) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(link)) {
                    confirmationAlert(first, last, email, link);
                }
            }
        });
    }

    private void confirmationAlert(final String first, final String last, final String email, final String link) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View confirm_dialog = inflater.inflate(R.layout.confirm_layout, null);
        ImageButton cancel_dialog = confirm_dialog.findViewById(R.id.cancel);

        builder.setCancelable(false);
        builder.setView(confirm_dialog);

        final AlertDialog dialog = builder.create();
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final Button confirm_submission = confirm_dialog.findViewById(R.id.confirm);
        confirm_submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendProject(first, last, email, link);
            }
        });

        dialog.show();
    }

    private void sendProject(String first, String last, String email, String link) {
        mAPIService.saveProject(first, last, email, link).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    successConfirmation();
                }
                else {
                    unsuccessfulAlert();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                unsuccessfulAlert();
                Log.e(TAG, "Unable to submit project.");
            }
        });
    }

    private void successConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(R.layout.success_layout);

        builder.create().show();
    }

    private void unsuccessfulAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(R.layout.alert_layout);

        builder.create().show();
    }
}
