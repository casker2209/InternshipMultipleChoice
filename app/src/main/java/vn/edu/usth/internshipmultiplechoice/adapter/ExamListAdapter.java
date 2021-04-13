package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamListAdapter extends RecyclerView.Adapter {
    List<ExamMini> examList;
    Context context;
    public ExamListAdapter(List examList,Context context){
        this.examList = examList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_examlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(ExamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExamMini examMini = examList.get(position);
        
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameView;
        private TextView descView;
        private Button examShow;
        private Button Go;
        public ViewHolder(View itemView)
        {
            super(itemView);
            nameView = itemView.findViewById(R.id.ExamName);
            descView = itemView.findViewById(R.id.Description);
            examShow = itemView.findViewById(R.id.ExamShow);
            Go = itemView.findViewById(R.id.goExam);
            Go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
