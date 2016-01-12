package dextra.desafio.macosrrosa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;
import java.util.List;
import dextra.desafio.macosrrosa.R;
import dextra.desafio.macosrrosa.bean.User;
import dextra.desafio.macosrrosa.util.Utils;

/**
 * Created by marcos.fael@gmail.com on 10/01/2016.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    protected static final String TAG = "USERS_ADAPTER";
    private final List<User> users;
    private UserOnClickListener userOnClickListener;
    private Context context;

    public UsersAdapter(Context context, List<User> users, UserOnClickListener userOnClickListener) {
        this.context = context;
        this.users = users;
        this.userOnClickListener = userOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.users != null ? this.users.size() : 0;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, viewGroup, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        User u = users.get(position);
        holder.txtName.setText(u.getLogin());
        holder.txtUrlPage.setText(u.getUrlPage());

        //dafult image
        Bitmap bmpNoImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.noimg);
        holder.imgUser.setImageBitmap(Utils.getRoundedShape(bmpNoImg, context));
        //Async download image:
        new DownloadImageTask(holder.imgUser).execute(u.getUrlAvatar());


        if (userOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userOnClickListener.onClick(holder.itemView, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    userOnClickListener.onLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    public interface UserOnClickListener {
        public void onClick(View view, int idx);
        public void onLongClick(View itemView, int position);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtUrlPage;
        public ImageView imgUser;

        public UserViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtUrlPage = (TextView) view.findViewById(R.id.txtUrlPage);
            imgUser = (ImageView) view.findViewById(R.id.imgUser);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(Utils.getRoundedShape(result, context));
            }
        }
    }
}
