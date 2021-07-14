package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;

public class ExamHistoryListAdapter extends RecyclerView.Adapter<ExamHistoryListAdapter.ViewHolder> {
    List<ExamHistory> examHistoryList;
    Context context;
    public ExamHistoryListAdapter(List<ExamHistory> examHistoryList,Context context){
        this.examHistoryList = examHistoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_examhistory, parent, false);
        ExamHistoryListAdapter.ViewHolder viewHolder = new ExamHistoryListAdapter.ViewHolder(ExamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamHistory examHistory = examHistoryList.get(position);
        holder.ExamName.setText(examHistory.getExam().getName());
        holder.ExamScore.setText(examHistory.getScore());
        holder.CorrectRecyclerView.setAdapter(new CorrectAnsAdapter(examHistory.getQuestionRight(),context,true));
        holder.IncorrectRecyclerView.setAdapter(new IncorrectAnsAdapter(examHistory.getQuestionWrong(),examHistory.getIncorrectChosen(),context));
        holder.NotChosenRecyclerView.setAdapter(new CorrectAnsAdapter(examHistory.getQuestionNotChosen(),context,false));
        holder.CorrectRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.IncorrectRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.NotChosenRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(examHistory.getQuestionRight().size()!=0) {
            holder.CorrectShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.CorrectRecyclerView.getHeight() != 0) {
                        ViewGroup.LayoutParams params = holder.CorrectRecyclerView.getLayoutParams();
                        params.height = 0;
                        holder.CorrectRecyclerView.setLayoutParams(params);
                        holder.CorrectShow.setText(context.getString(R.string.correct_show));
                    } else {
                        ViewGroup.LayoutParams params = holder.CorrectRecyclerView.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        holder.CorrectRecyclerView.setLayoutParams(params);
                        holder.CorrectShow.setText(context.getString(R.string.correct_hide));
                    }
                }
            });
        }
        else{
            holder.CorrectShow.setText(context.getString(R.string.correct_size_zero));
        }
        if(examHistory.getQuestionWrong().size()!=0) {
            holder.IncorrectShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.IncorrectRecyclerView.getHeight() != 0) {
                        ViewGroup.LayoutParams params = holder.IncorrectRecyclerView.getLayoutParams();
                        params.height = 0;
                        holder.IncorrectRecyclerView.setLayoutParams(params);
                        holder.IncorrectShow.setText(context.getString(R.string.incorrect_show));

                    } else {
                        ViewGroup.LayoutParams params = holder.IncorrectRecyclerView.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        holder.IncorrectRecyclerView.setLayoutParams(params);
                        holder.IncorrectShow.setText(context.getString(R.string.correct_hide));

                    }
                }
            });
        }
        else{
            holder.IncorrectShow.setText(context.getString(R.string.incorrect_size_zero));
        }
        if(examHistory.getQuestionNotChosen().size()!=0) {
            holder.NotChosenShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.NotChosenRecyclerView.getHeight() != 0) {
                        ViewGroup.LayoutParams params = holder.NotChosenRecyclerView.getLayoutParams();
                        params.height = 0;
                        holder.NotChosenRecyclerView.setLayoutParams(params);
                        holder.NotChosenShow.setText(context.getString(R.string.notchosen_show));

                    } else {
                        ViewGroup.LayoutParams params = holder.NotChosenRecyclerView.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        holder.NotChosenRecyclerView.setLayoutParams(params);
                        holder.NotChosenShow.setText(context.getString(R.string.notchosen_hide));

                    }
                }
            });
        }
        else{
            holder.IncorrectShow.setText(context.getString(R.string.notchosen_size_zero));
        }
        holder.ExamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int contentHeight = holder.ExamDescription.getHeight();
                if(contentHeight==0){
                    ViewGroup.LayoutParams params = holder.ExamDescription.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.ExamDescription.setLayoutParams(params);
                }
                else{
                    ViewGroup.LayoutParams params = holder.ExamDescription.getLayoutParams();
                    params.height = 0;
                    holder.ExamDescription.setLayoutParams(params);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return examHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ExamName,ExamScore,CorrectShow,IncorrectShow,NotChosenShow;
        LinearLayout ExamDescription;
        RecyclerView CorrectRecyclerView,IncorrectRecyclerView,NotChosenRecyclerView;
        public ViewHolder(@NonNull View view) {
            super(view);
            ExamDescription = view.findViewById(R.id.ExamContent);
            ExamName = view.findViewById(R.id.Name);
            ExamScore = view.findViewById(R.id.ExamScoreContent);
            CorrectRecyclerView = view.findViewById(R.id.CorrectAnswerList);
            IncorrectRecyclerView = view.findViewById(R.id.IncorrectAnswerList);
            NotChosenRecyclerView = view.findViewById(R.id.NotChosenList);
            CorrectShow = view.findViewById(R.id.CorrectAnswer);
            IncorrectShow = view.findViewById(R.id.IncorrectAnswer);
            NotChosenShow = view.findViewById(R.id.NotChosen);
        }
    }
}
