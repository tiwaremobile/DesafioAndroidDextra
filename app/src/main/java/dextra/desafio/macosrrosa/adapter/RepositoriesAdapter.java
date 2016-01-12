package dextra.desafio.macosrrosa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import dextra.desafio.macosrrosa.R;
import dextra.desafio.macosrrosa.bean.Repository;


/**
 * Created by marcos.fael@gmail.com on 10/01/2016.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.UserViewHolder> {
    protected static final String TAG = "REPOSITORIES_ADAPTER";
    private final List<Repository> repos;
    private RepoOnClickListener repoOnClickListener;
    private Context context;

    public RepositoriesAdapter(Context context, List<Repository> repos, RepoOnClickListener repoOnClickListener) {
        this.context = context;
        this.repos = repos;
        this.repoOnClickListener = repoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.repos != null ? this.repos.size() : 0;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_repo, viewGroup, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        Repository r = repos.get(position);
        holder.txtName.setText(r.getName());
        holder.txtFullName.setText(r.getFullName());
        holder.txtUrlPage.setText(r.getUrlPage());

        if (repoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repoOnClickListener.onClick(holder.itemView, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    repoOnClickListener.onLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    public interface RepoOnClickListener {
        public void onClick(View view, int idx);
        public void onLongClick(View itemView, int position);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtFullName;
        public TextView txtUrlPage;

        public UserViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtUrlPage = (TextView) view.findViewById(R.id.txtUrlPage);
            txtFullName = (TextView) view.findViewById(R.id.txtFullName);
        }
    }

}
