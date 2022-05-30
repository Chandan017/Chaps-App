// Generated by view binder compiler. Do not edit!
package com.chandan_giri017.chaps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.chandan_giri017.chaps.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RowConversationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView lastMessage;

  @NonNull
  public final TextView messageTime;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView userName;

  @NonNull
  public final View view;

  private RowConversationBinding(@NonNull ConstraintLayout rootView, @NonNull TextView lastMessage,
      @NonNull TextView messageTime, @NonNull CircleImageView profileImage,
      @NonNull TextView userName, @NonNull View view) {
    this.rootView = rootView;
    this.lastMessage = lastMessage;
    this.messageTime = messageTime;
    this.profileImage = profileImage;
    this.userName = userName;
    this.view = view;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RowConversationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RowConversationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.row_conversation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RowConversationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.lastMessage;
      TextView lastMessage = ViewBindings.findChildViewById(rootView, id);
      if (lastMessage == null) {
        break missingId;
      }

      id = R.id.messageTime;
      TextView messageTime = ViewBindings.findChildViewById(rootView, id);
      if (messageTime == null) {
        break missingId;
      }

      id = R.id.profileImage;
      CircleImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.userName;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      id = R.id.view;
      View view = ViewBindings.findChildViewById(rootView, id);
      if (view == null) {
        break missingId;
      }

      return new RowConversationBinding((ConstraintLayout) rootView, lastMessage, messageTime,
          profileImage, userName, view);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}