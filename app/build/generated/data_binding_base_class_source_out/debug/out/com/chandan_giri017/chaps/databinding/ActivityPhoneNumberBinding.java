// Generated by view binder compiler. Do not edit!
package com.chandan_giri017.chaps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.chandan_giri017.chaps.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPhoneNumberBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final Button getOTPButton;

  @NonNull
  public final EditText phonebox;

  private ActivityPhoneNumberBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull Button getOTPButton, @NonNull EditText phonebox) {
    this.rootView = rootView;
    this.getOTPButton = getOTPButton;
    this.phonebox = phonebox;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPhoneNumberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPhoneNumberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_phone_number, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPhoneNumberBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.getOTPButton;
      Button getOTPButton = ViewBindings.findChildViewById(rootView, id);
      if (getOTPButton == null) {
        break missingId;
      }

      id = R.id.phonebox;
      EditText phonebox = ViewBindings.findChildViewById(rootView, id);
      if (phonebox == null) {
        break missingId;
      }

      return new ActivityPhoneNumberBinding((LinearLayoutCompat) rootView, getOTPButton, phonebox);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}