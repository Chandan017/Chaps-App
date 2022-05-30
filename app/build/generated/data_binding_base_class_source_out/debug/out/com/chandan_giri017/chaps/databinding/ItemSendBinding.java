// Generated by view binder compiler. Do not edit!
package com.chandan_giri017.chaps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.chandan_giri017.chaps.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemSendBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView reaction;

  @NonNull
  public final TextView sentMessage;

  private ItemSendBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView reaction,
      @NonNull TextView sentMessage) {
    this.rootView = rootView;
    this.reaction = reaction;
    this.sentMessage = sentMessage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemSendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemSendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_send, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemSendBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.reaction;
      ImageView reaction = ViewBindings.findChildViewById(rootView, id);
      if (reaction == null) {
        break missingId;
      }

      id = R.id.sentMessage;
      TextView sentMessage = ViewBindings.findChildViewById(rootView, id);
      if (sentMessage == null) {
        break missingId;
      }

      return new ItemSendBinding((ConstraintLayout) rootView, reaction, sentMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}