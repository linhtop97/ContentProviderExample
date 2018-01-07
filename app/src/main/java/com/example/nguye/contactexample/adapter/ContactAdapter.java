package com.example.nguye.contactexample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguye.contactexample.IAppAction;
import com.example.nguye.contactexample.R;
import com.example.nguye.contactexample.Contact;

import java.util.List;

/**
 * Created by linh nb on 06/01/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private List<Contact> mContacts;
    private Context mContext;
    private IAppAction _mAction;
    public ContactAdapter(Context context,List<Contact> contacts, IAppAction _action) {
        mContacts = contacts;
        mContext = context;
        _mAction = _action;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View v = mLayoutInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bindData(mContacts.get(position));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.valueOf(v.getTag().toString());
                _mAction.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
            return mContacts != null ? mContacts.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;
        private TextView mTextPhone;
        private ImageView mImgAvatar;
        private ImageView mImgIsFavorite;

         ViewHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextPhone = (TextView) itemView.findViewById(R.id.text_phone);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.img_avartar);
            mImgIsFavorite = (ImageView) itemView.findViewById(R.id.img_favorite);
        }

         void bindData(Contact contact) {
            if (contact == null) return;
            mTextName.setText(contact.getmName());
            mTextPhone.setText(contact.getmPhone());
            if(contact.ismIsFavorit()){
                Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_favorit);
                mImgIsFavorite.setImageBitmap(bm);
            }
            circularImage(mImgAvatar);
        }

        void circularImage(ImageView img){
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.avatar);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            img.setImageDrawable(roundedBitmapDrawable);
        }

    }
}
