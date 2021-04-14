package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.ExamActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    List<ExamMini> examList;
    Context context;

    public ExamListAdapter(List examList, Context context) {
        this.examList = examList;
        this.context = context;
    }


    @NonNull
    @Override
    public ExamListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_examlist, parent, false);

        ExamListAdapter.ViewHolder viewHolder = new ExamListAdapter.ViewHolder(ExamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamListAdapter.ViewHolder holder, int position) {
        ExamMini examMini = examList.get(position);
        holder.descView.setText(examList.get(position).getDescription());
        holder.nameView.setText(examList.get(position).getName());
        holder.Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ExamActivity.class);
                intent.putExtra("id",examMini.getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView descView;
        private LinearLayout DescAndGo;
        private Button Go;
        public boolean checkSize(){
            int height = descView.getHeight();
            if(height!=0){
                return true;
            }
            else{
                return false;
            }
        }
        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.ExamName);
            descView = itemView.findViewById(R.id.Description);
            DescAndGo = itemView.findViewById(R.id.DescAndGo);
            Go = itemView.findViewById(R.id.goExam);
            nameView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(checkSize()){
                        DescAndGo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0));
                    }
                    else{
                        DescAndGo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                }
            });
        }
    }
}

