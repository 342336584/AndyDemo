package com.example.andy.andydemo.net.student;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.andydemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onething on 2018/4/9.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> mStudent;

    private LayoutInflater mInflater;

    public StudentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public StudentAdapter(Context context, List<Student> mStudent) {
        this(context);
        this.mStudent = mStudent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item,parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!checkDataValid())
            return;
        holder.name.setText(mStudent.get(position).name);
        holder.age.setText("" + mStudent.get(position).age);
        holder.address.setText(mStudent.get(position).address);
        holder.no.setText(mStudent.get(position).study.no);
        holder.teacher.setText(mStudent.get(position).study.teacher);
    }

    @Override
    public int getItemCount() {
        return checkDataValid() ? mStudent.size() : 0;
    }

    boolean checkDataValid(){
        if (mStudent == null || mStudent.isEmpty())
            return false;

        return true;
    }

    public void setData(List<Student> data) {
        mStudent = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        public TextView name;

        @BindView(R.id.age)
        public TextView age;

        @BindView(R.id.address)
        public TextView address;

        @BindView(R.id.no)
        public TextView no;

        @BindView(R.id.teacher)
        public TextView teacher;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
